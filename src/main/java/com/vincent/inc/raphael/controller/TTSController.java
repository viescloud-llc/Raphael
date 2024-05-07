package com.vincent.inc.raphael.controller;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vincent.inc.raphael.model.Text;
import com.vincent.inc.raphael.service.TTSService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v2/tts")
@Slf4j
public class TTSController {

    @Autowired
    private TTSService ttsService;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    @PostMapping("wav")
    public ResponseEntity<byte[]> generateWav(@RequestBody Text text) throws SQLException {
        var wav = this.ttsService.generateWav(text.getText());
        return ResponseEntity.ok().header("Content-Type", "audio/wav").body(wav);
    }

    @PutMapping("preload")
    public ResponseEntity<?> preloadWav(@RequestBody Text text) throws SQLException {
        var input = text.getText();
        this.threadPool.execute(() -> {
            try {
                this.ttsService.preload(input);
            } catch (SerialException e) {
                log.error(e.getMessage(), e);
            }
        });
        return ResponseEntity.ok().build();
    }
}
