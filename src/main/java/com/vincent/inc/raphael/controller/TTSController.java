package com.vincent.inc.raphael.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viescloud.eco.viesspringutils.auto.model.object_storage.DirectAccessLinkType;
import com.viescloud.eco.viesspringutils.auto.model.object_storage.ObjectStorageData;
import com.viescloud.eco.viesspringutils.auto.service.object_storage.ObjectStorageService;
import com.vincent.inc.raphael.model.TTS;
import com.vincent.inc.raphael.service.TTSService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/tts")
@Slf4j
public class TTSController {

    @Autowired
    private TTSService ttsService;

    @Autowired
    private ObjectStorageService<ObjectStorageData, ?> objectStorageService;

    @PostMapping("wav")
    public ResponseEntity<byte[]> generateWav(@RequestBody TTS tts) {
        var metadata = this.ttsService.generateWav(tts);
        if(metadata.getData() == null) {
            metadata = this.objectStorageService.loadFileData(metadata);
        }
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "audio/wav").body(metadata.getData());
    }

    @PostMapping("wav/metadata")
    public ResponseEntity<ObjectStorageData> generateWavMetadata(@RequestBody TTS tts) {
        var metadata = this.ttsService.generateWav(tts);
        metadata = this.objectStorageService.addTemporaryAccessLink(metadata, DirectAccessLinkType.GET, Duration.ofMinutes(30));
        return ResponseEntity.ok(metadata);
    }

    @PutMapping("preload")
    public ResponseEntity<?> preloadWav(@RequestBody TTS tts) {
        this.ttsService.preloadWav(tts);        
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
