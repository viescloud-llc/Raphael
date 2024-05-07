package com.vincent.inc.raphael.controller;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vincent.inc.raphael.model.TTS;
import com.vincent.inc.raphael.service.TTSServiceV1;
import com.vincent.inc.raphael.util.TTSServices;
import com.vincent.inc.viesspringutils.controller.ViesController;
import com.vincent.inc.viesspringutils.exception.HttpResponseThrowers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/tts")
public class TTSControllerV1 extends ViesController<TTS, Integer, TTSServiceV1> {

    public TTSControllerV1(TTSServiceV1 service) {
        super(service);
    }
    
    @GetMapping("wav/{id}")
    public ResponseEntity<byte[]> getBlobById(@PathVariable int id) throws SQLException {
        var tts = this.service.getById(id);
        var wav = tts.getWav();
        byte[] blobAsBytes = TTSServices.getBlobAsBytes(wav);
        return ResponseEntity.ok().header("Content-Type", "audio/wav").body(blobAsBytes);
    }

    @GetMapping("wav")
    public ResponseEntity<byte[]> getBlobByText(@RequestParam String text) throws SQLException {
        text = this.service.sanitizingText(text);
        var tts = this.service.getByText(text);

        if(ObjectUtils.isEmpty(tts))
            HttpResponseThrowers.throwBadRequest("TTS does not exist");

        var wav = tts.getWav();
        int blobLength = (int) wav.length();  
        byte[] blobAsBytes = wav.getBytes(1, blobLength);
        return ResponseEntity.ok().header("Content-Type", "audio/wav").body(blobAsBytes);
    }

    
}
