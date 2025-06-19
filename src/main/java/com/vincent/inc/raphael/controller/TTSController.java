package com.vincent.inc.raphael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vincent.inc.raphael.service.TTSService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/tts")
@Slf4j
public class TTSController {

    @Autowired
    private TTSService ttsService;

    // @PostMapping("wav")
    // public ResponseEntity<byte[]> generateWav(@RequestBody Text text) throws SQLException {
    //     var wav = this.ttsService.generateWav(text.getText());
    //     return ResponseEntity.ok().header("Content-Type", "audio/wav").body(wav);
    // }

    // @PutMapping("preload")
    // public ResponseEntity<?> preloadWav(@RequestBody Text text) throws SQLException {
    //     var input = text.getText();
    //     this.threadPool.execute(() -> {
    //         try {
    //             this.ttsService.preload(input);
    //         } catch (SerialException e) {
    //             log.error(e.getMessage(), e);
    //         }
    //     });
    //     return ResponseEntity.ok().build();
    // }
}
