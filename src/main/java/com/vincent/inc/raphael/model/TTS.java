package com.vincent.inc.raphael.model;

import javax.sql.rowset.serial.SerialBlob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viescloud.eco.viesspringutils.model.TrackedTimeStamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TTS extends TrackedTimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "BLOB")
    private String text;
    
    @Column(columnDefinition = "LONGBLOB")
    @JsonIgnore
    private SerialBlob wav;
}
