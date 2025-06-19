package com.vincent.inc.raphael.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Intent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String question;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String context;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String emotion;
    
    @ElementCollection
    private List<Response> response;
}
