package com.vincent.inc.raphael.client;

import java.util.Map;
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

    public Optional<byte[]> generateWav(String text) {
        return WebCall.of(restTemplate, byte[].class)
                      .request(HttpMethod.GET, String.format("%s/generate/wav", applicationProperties.getCoquiTTS_Url()))
                      .body(Map.of("text", text))
                      .exchange()
                      .getOptionalResponseBody();
    }
}
