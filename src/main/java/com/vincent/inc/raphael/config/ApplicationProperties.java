package com.vincent.inc.raphael.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class ApplicationProperties {
    @Value("${CoquiTTS.url}")
    private String CoquiTTS_Url;

    @Value("${openai.token}") 
    String openAPI_token;
}
