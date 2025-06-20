package com.vincent.inc.raphael.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.viescloud.eco.viesspringutils.auto.model.object_storage.ObjectStorageData;
import com.viescloud.eco.viesspringutils.auto.service.object_storage.ObjectStorageService;
import com.viescloud.eco.viesspringutils.exception.HttpResponseThrowers;
import com.viescloud.eco.viesspringutils.model.EncodingType;
import com.viescloud.eco.viesspringutils.util.DataTransformationUtils;
import com.viescloud.eco.viesspringutils.util.DateTime;
import com.viescloud.eco.viesspringutils.util.ScheduledQueueTask;
import com.vincent.inc.raphael.client.TtsClient;
import com.vincent.inc.raphael.model.TTS;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TTSService {
    private static final int QUEUE_MAX_SIZE = 5;
    private static final long OBJECT_STORAGE_USER_ID = -2L;
    private static final String PREFIX = "/tts";

    private final TtsClient ttsClient;
    private final ObjectStorageService<ObjectStorageData, ?> objectStorageService;

    @Getter
    private Set<String> voices = new HashSet<>();
    @Getter
    private Set<String> models = new HashSet<>();
    private String defaultModel = null;
    private String defaultMultilingualModel = null;
    private List<ScheduledQueueTask<TTS>> queues = new ArrayList<>();
    private int count = 0;

    @PostConstruct
    public void init() {
        this.voices.addAll(ttsClient.getVoices());
        var models = ttsClient.getModels();
        this.models.addAll(models.available_models());
        defaultModel = models.default_model();
        defaultMultilingualModel = models.default_multilingual_model();

        for (int i = 0; i < QUEUE_MAX_SIZE; i++) {
            var queue = new ScheduledQueueTask<TTS>();
            queue.setDefaultDuration(Duration.ofSeconds(2));
            queues.add(queue);
        }
    }

    public ObjectStorageData generateWav(TTS tts) {
        tts = this.formatTTS(tts);
        var key = generateKey(tts);

        var metadata = this.objectStorageService.getFileMetadata(key);

        if(metadata != null) {
            return metadata;
        }
        else {
            this.preloadWav(tts);
            this.waitForPreloading(key, Duration.ofMinutes(10));
            metadata = this.objectStorageService.getFileMetadata(key);
        }

        if(metadata != null) {
            return metadata;
        }
        else {
            var wav = this.ttsClient.generateWav(tts);

            if(wav.isPresent()) {
                this.objectStorageService.postIfNotExist(key, wav.get(), OBJECT_STORAGE_USER_ID);
            }
            else {
                HttpResponseThrowers.throwServerError("Failed to generate wav");
            }

            metadata = this.objectStorageService.getFileMetadata(key);
            return metadata;
        }
    }

    public void preloadWav(TTS tts) {
        tts = this.formatTTS(tts);
        var key = generateKey(tts);
        var queue = queues.get(count);
        count = count % QUEUE_MAX_SIZE;
        count++;

        queue.add(key, Duration.ofSeconds(2), tts, (t, k) -> {
            if(!this.objectStorageService.isFileExists(k)) {
                var wav = this.ttsClient.generateWav(t);

                if(wav.isPresent()) {
                    this.objectStorageService.postIfNotExist(k, wav.get(), OBJECT_STORAGE_USER_ID);
                }
            }
        });
    }

    private void waitForPreloading(String key, Duration maxWaitTime) {
        var endTime = DateTime.now().plusDateTimes(DateTime.of(maxWaitTime));
        while(this.isPreloading(key) && DateTime.now().isBefore(endTime)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isPreloading(String key) {
        return this.queues.parallelStream().anyMatch(q -> q.contains(key));
    }

    public TTS formatTTS(TTS tts) {
        if(ObjectUtils.isEmpty(tts.getText())) {
            HttpResponseThrowers.throwBadRequest("Text is empty");
        }

        if(ObjectUtils.isEmpty(tts.getModel())) {
            if(ObjectUtils.isEmpty(tts.getVoice())) {
                tts.setModel(defaultModel);
            }
            else {
                tts.setModel(defaultMultilingualModel);
            }
        }

        return tts;
    }

    public static String generateKey(TTS tts) {
        var key = DataTransformationUtils.encode(tts.toString(), EncodingType.SHA256);
        return PREFIX + "/" + key + ".wav";
    }

}
