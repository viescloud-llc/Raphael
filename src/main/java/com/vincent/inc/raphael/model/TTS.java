package com.vincent.inc.raphael.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TTS {
    private String text;
    private String cloneVoice;
    private String modelName;
}
