package com.vincent.inc.raphael.service;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.viescloud.eco.viesspringutils.auto.service.object_storage.ObjectStorageService;
import com.vincent.inc.raphael.client.TtsClient;
import com.vincent.inc.raphael.model.AiReaderRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TTSService {

    @Autowired
    private TtsClient ttsClient;

    @Autowired
    private ObjectStorageService<?, ?> objectStorageService;

    // public byte[] generateWav(String text) {
    //     text = TTSServices.preProcessingText(text);
    //     byte[] wav = null;
    //     try {
    //         wav = objectStorageService.getFileByName(SERVICE_USER_ID, text + ".wav");
    //     }
    //     catch(FeignException ex) {
    //         log.info("no file with name {} is found", text);
    //         if(ex.status() == 404) {
    //             try {
    //                 this.preload(text);
    //             } catch (SerialException e) {
    //                 log.error(e.getMessage(), e);
    //             }
    //             return generateWav(text);
    //         }
    //     }
        
    //     return wav;
    // }

    // public void preload(String text) throws SerialException {
    //     text = TTSServices.preProcessingText(text);
    //     FileMetaData fileMetaData = null;
    //     try {
    //         fileMetaData = objectStorageService.getMetadata(SERVICE_USER_ID, text + ".wav");
    //     }
    //     catch(FeignException ex) {
    //         log.info("no file with name {} is found", text);
    //     }
        
    //     if(ObjectUtils.isEmpty(fileMetaData)) {
    //         var tts = generateTTS(text);
    //         var multipart = CustomMultipartFile.builder().customName("file").customOriginalFileName(text + ".wav").customContentType("audio/wav").input(TTSServices.getBlobAsBytes(tts)).build();
    //         this.objectStorageService.uploadFile(SERVICE_USER_ID, multipart, false);
    //     }
    // }

    public SerialBlob generateTTS(String text) {
        var response = this.ttsClient.generateWav(text);
        if(response.isPresent()) {
            var wav = response.get();

            try {
                SerialBlob blob = new SerialBlob(wav);
                return blob;
            } catch (SerialException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
