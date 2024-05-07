package com.vincent.inc.raphael.service;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.vincent.inc.raphael.feign.AiReaderFeignClient;
import com.vincent.inc.raphael.feign.SmbFileManagerClient;
import com.vincent.inc.raphael.model.AiReaderRequest;
import com.vincent.inc.raphael.model.SmbFileManager.FileMetaData;
import com.vincent.inc.raphael.util.TTSServices;
import com.vincent.inc.viesspringutils.model.CustomMultipartFile;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TTSService {

    private static final String SERVICE_USER_ID = "-1";

    @Autowired
    private AiReaderFeignClient aiReaderFeignClient;

    @Autowired
    private SmbFileManagerClient smbFileManagerClient;

    public byte[] generateWav(String text) {
        text = TTSServices.preProcessingText(text);
        byte[] wav = null;
        try {
            wav = smbFileManagerClient.getFileByFileName(SERVICE_USER_ID, text + ".wav");
        }
        catch(FeignException ex) {
            log.info("no file with name {} is found", text);
            if(ex.status() == 404) {
                try {
                    this.preload(text);
                } catch (SerialException e) {
                    log.error(e.getMessage(), e);
                }
                return generateWav(text);
            }
        }
        
        return wav;
    }

    public void preload(String text) throws SerialException {
        text = TTSServices.preProcessingText(text);
        FileMetaData fileMetaData = null;
        try {
            fileMetaData = smbFileManagerClient.getMetadata(SERVICE_USER_ID, text + ".wav");
        }
        catch(FeignException ex) {
            log.info("no file with name {} is found", text);
        }
        
        if(ObjectUtils.isEmpty(fileMetaData)) {
            var tts = generateTTS(text);
            var multipart = CustomMultipartFile.builder().customName("file").customOriginalFileName(text + ".wav").customContentType("audio/wav").input(TTSServices.getBlobAsBytes(tts)).build();
            this.smbFileManagerClient.uploadFile(SERVICE_USER_ID, multipart, false);
        }
    }

    public SerialBlob generateTTS(String text) {
        var wav = this.aiReaderFeignClient.generateWav(new AiReaderRequest(text));

        try {
            SerialBlob blob = new SerialBlob(wav);
            return blob;
        } catch (SerialException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    
}
