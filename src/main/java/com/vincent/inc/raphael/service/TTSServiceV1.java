package com.vincent.inc.raphael.service;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.vincent.inc.raphael.dao.TTSDao;
import com.vincent.inc.raphael.feign.AiReaderFeignClient;
import com.vincent.inc.raphael.model.AiReaderRequest;
import com.vincent.inc.raphael.model.TTS;
import com.vincent.inc.raphael.model.TimeModel;
import com.vincent.inc.raphael.util.TTSServices;
import com.vincent.inc.viesspringutils.service.ViesService;
import com.vincent.inc.viesspringutils.util.DatabaseCall;
import com.vincent.inc.viesspringutils.util.DateTime;

@Service
public class TTSServiceV1 extends ViesService<TTS, Integer, TTSDao> {

    @Autowired
    private AiReaderFeignClient aiReaderFeignClient;

    public TTSServiceV1(DatabaseCall<TTS, Integer> databaseCall, TTSDao repositoryDao) {
        super(databaseCall, repositoryDao);
    }

    @Override
    protected TTS newEmptyObject() {
        return new TTS();
    }
    
    @Override
    public TTS post(TTS object) {
        sanitizingText(object);
        var foundedTTS = this.getByText(object.getText());
        if(!ObjectUtils.isEmpty(foundedTTS))
            return this.getById(foundedTTS.getId());

        object.setWav(this.generateTTS(object.getText()));
        object.setCreatedTime(TimeModel.fromDateTime(DateTime.now()));
        return super.post(object);
    }

    @Override
    public TTS put(Integer id, TTS object) {
        sanitizingText(object);
        var foundedTTS = this.getByText(object.getText());
        if(!ObjectUtils.isEmpty(foundedTTS))
            return this.getById(foundedTTS.getId());

        object.setWav(this.generateTTS(object.getText()));
        object.setCreatedTime(TimeModel.fromDateTime(DateTime.now()));
        return super.put(id, object);
    }

    @Override
    public TTS patch(Integer id, TTS object) {
        sanitizingText(object);
        var foundedTTS = this.getByText(object.getText());
        if(!ObjectUtils.isEmpty(foundedTTS))
            return this.getById(foundedTTS.getId());

        object.setWav(this.generateTTS(object.getText()));
        object.setCreatedTime(TimeModel.fromDateTime(DateTime.now()));
        return super.patch(id, object);
    }

    public TTS getByText(String text) {
        var list = this.repositoryDao.findAllByText(text);
        for(var tts: list) {
            if(tts.getText().equalsIgnoreCase(text))
                return tts;
        }
        return null;
    }

    public TTS sanitizingText(TTS tts) {
        tts.setText(sanitizingText(tts.getText()));
        return tts;
    }

    public String sanitizingText(String text) {
        return TTSServices.preProcessingText(text);
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
