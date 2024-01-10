package com.vincent.inc.raphael.model;

import java.io.Serializable;

import javax.sql.rowset.serial.SerialBlob;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TTS implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(columnDefinition = "BLOB")
    private String text;
    
    @Column(columnDefinition = "LONGBLOB")
    @JsonIgnore
    private SerialBlob wav;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private TimeModel createdTime;
}
