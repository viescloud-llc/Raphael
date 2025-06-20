package com.vincent.inc.raphael.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Response implements Serializable {
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String responseText;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String context;
    
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String emotion;
}
