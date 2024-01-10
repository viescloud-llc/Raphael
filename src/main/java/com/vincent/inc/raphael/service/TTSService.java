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
import com.vincent.inc.viesspringutils.service.ViesService;
import com.vincent.inc.viesspringutils.util.DatabaseUtils;
import com.vincent.inc.viesspringutils.util.DateTime;

@Service
public class TTSService extends ViesService<TTS, Integer, TTSDao> {

    @Autowired
    private AiReaderFeignClient aiReaderFeignClient;

    public TTSService(DatabaseUtils<TTS, Integer> databaseUtils, TTSDao repositoryDao) {
        super(databaseUtils, repositoryDao);
    }

    @Override
    protected TTS newEmptyObject() {
        return new TTS();
    }
    
    @Override
    public TTS create(TTS object) {
        sanitizingText(object);
        var foundedTTS = this.getByText(object.getText());
        if(!ObjectUtils.isEmpty(foundedTTS))
            return foundedTTS;

        object.setWav(this.generateTTS(object.getText()));
        object.setCreatedTime(TimeModel.fromDateTime(DateTime.now()));
        return super.create(object);
    }

    @Override
    public TTS modify(Integer id, TTS object) {
        sanitizingText(object);
        var foundedTTS = this.getByText(object.getText());
        if(!ObjectUtils.isEmpty(foundedTTS))
            return foundedTTS;

        object.setWav(this.generateTTS(object.getText()));
        object.setCreatedTime(TimeModel.fromDateTime(DateTime.now()));
        return super.modify(id, object);
    }

    @Override
    public TTS patch(Integer id, TTS object) {
        sanitizingText(object);
        var foundedTTS = this.getByText(object.getText());
        if(!ObjectUtils.isEmpty(foundedTTS))
            return foundedTTS;

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
        return text.toLowerCase().trim().replaceAll(" +", " ");
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
