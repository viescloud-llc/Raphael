package com.vincent.inc.raphael.client;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.viescloud.eco.viesspringutils.util.WebCall;
import com.vincent.inc.raphael.config.ApplicationProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TtsClient {
    private final ApplicationProperties applicationProperties;
    private final RestTemplate restTemplate;

    public record TTSConfig(String cloneVoice, String modelName) {}

    public Optional<byte[]> generateWav(String text, TTSConfig ttsConfig) {
        var params = new HashMap<String, String>();
        if(text != null) {
            params.put("text", text);
        }
        if (ttsConfig != null) {
            if(ttsConfig.cloneVoice() != null) {
                params.put("clone_voice", ttsConfig.cloneVoice());
            }
            if(ttsConfig.modelName() != null) {
                params.put("model_name", ttsConfig.modelName());
            }
        }

        return WebCall.of(restTemplate, byte[].class)
                      .request(HttpMethod.GET, String.format("%s/generate/wav", applicationProperties.getCoquiTTS_Url()))
                      .queryParams(params)
                      .exchange()
                      .getOptionalResponseBody();
    }

    public Optional<byte[]> generateWav(String text) {
        return generateWav(text, null);
    }

    public record TTSVoiceData(List<String> voices, int count) {}
    public record TTSVoiceResponse(boolean success, String message, TTSVoiceData data) {}

    public List<String> getVoices() {
        return WebCall.of(restTemplate, TTSVoiceResponse.class)
                      .request(HttpMethod.GET, String.format("%s/voices", applicationProperties.getCoquiTTS_Url()))
                      .exchange()
                      .getOptionalResponseBody()
                      .orElse(null)
                      .data()
                      .voices();
    }

    public record TTSModelData(List<String> available_models, List<String> loaded_models, String default_model, int total_available, int total_loaded) {}
    public record TTSModelResponse(boolean success, String message, TTSModelData data) {}

    public TTSModelData getModels() {
        return WebCall.of(restTemplate, TTSModelResponse.class)
                      .request(HttpMethod.GET, String.format("%s/models", applicationProperties.getCoquiTTS_Url()))
                      .exchange()
                      .getOptionalResponseBody()
                      .orElse(null)
                      .data();
    }
}
