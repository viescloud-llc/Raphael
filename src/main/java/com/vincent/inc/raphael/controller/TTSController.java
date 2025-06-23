package com.vincent.inc.raphael.controller;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viescloud.eco.viesspringutils.auto.model.object_storage.DirectAccessLinkType;
import com.viescloud.eco.viesspringutils.auto.model.object_storage.ObjectStorageData;
import com.viescloud.eco.viesspringutils.auto.service.object_storage.ObjectStorageService;
import com.viescloud.eco.viesspringutils.exception.HttpResponseThrowers;
import com.vincent.inc.raphael.model.TTS;
import com.vincent.inc.raphael.service.TTSService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/tts")
@Slf4j
public class TTSController {

    @Autowired
    private TTSService ttsService;

    @Autowired
    private ObjectStorageService<ObjectStorageData, ?> objectStorageService;

    @GetMapping("models")
    public Set<String> getModels() {
        return this.ttsService.getModels();
    }
    
    @GetMapping("voices")
    public Set<String> getVoices() {
        return this.ttsService.getVoices();
    }

    private record DefaultSetting(String defaultModel, String defaultMultilingualModel) {}

    @GetMapping("default")
    public DefaultSetting getDefaultSetting() {
        return new DefaultSetting(this.ttsService.getDefaultModel(), this.ttsService.getDefaultMultilingualModel());
    }

    @PutMapping("wav")
    public ResponseEntity<?> generateWav(@RequestBody TTS tts) {
        if(ObjectUtils.isEmpty(tts.getText())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponseThrowers.getExceptionResponse(HttpStatus.BAD_REQUEST, "Text is empty"));
        }
        
        tts.setText(Normalizer.normalize(tts.getText(), Form.NFKC));

        var metadata = this.ttsService.generateWav(tts);
        if(metadata.getData() == null) {
            metadata = this.objectStorageService.loadFileData(metadata);
        }
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "audio/wav").body(metadata.getData());
    }

    @PutMapping("wav/metadata")
    public ResponseEntity<?> generateWavMetadata(@RequestBody TTS tts) {
        if(ObjectUtils.isEmpty(tts.getText())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponseThrowers.getExceptionResponse(HttpStatus.BAD_REQUEST, "Text is empty"));
        }

        tts.setText(Normalizer.normalize(tts.getText(), Form.NFKC));

        var metadata = this.ttsService.generateWav(tts);
        metadata = this.objectStorageService.addTemporaryAccessLink(metadata, DirectAccessLinkType.GET, Duration.ofMinutes(30));
        return ResponseEntity.ok(metadata);
    }

    @PutMapping("wav/preload")
    public ResponseEntity<?> preloadWav(@RequestBody TTS tts) {
        if(ObjectUtils.isEmpty(tts.getText())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpResponseThrowers.getExceptionResponse(HttpStatus.BAD_REQUEST, "Text is empty"));
        }

        tts.setText(Normalizer.normalize(tts.getText(), Form.NFKC));

        this.ttsService.preloadWav(tts);        
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
