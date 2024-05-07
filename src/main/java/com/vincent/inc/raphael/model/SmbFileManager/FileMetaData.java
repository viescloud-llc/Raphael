package com.vincent.inc.raphael.model.SmbFileManager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vincent.inc.viesspringutils.model.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileMetaData extends UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column
    private String originalFilename;
    
    @Column
    private String contentType;
    
    @Column
    private Long size;
    
    @Column(unique = true)
    private String path;

    @Column
    private Boolean publicity;

    @JsonIgnore
    @Transient
    private byte[] data;
}
