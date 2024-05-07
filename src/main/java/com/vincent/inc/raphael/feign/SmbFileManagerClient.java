package com.vincent.inc.raphael.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.vincent.inc.raphael.model.SmbFileManager.FileMetaData;

@FeignClient("SMB-FILE-MANAGER-SERVICE")
public interface SmbFileManagerClient {

    @GetMapping("file")
    public byte[] getFileByFileName(
            @RequestHeader String user_id,
            @RequestParam String fileName);

    @GetMapping("metadata")
    public FileMetaData getMetadata(
            @RequestHeader String user_id,
            @RequestParam String fileName);

    @PostMapping(value = "file", consumes = "multipart/form-data")
    public FileMetaData uploadFile(
            @RequestHeader String user_id,
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "publicity") Boolean publicity);

    @PatchMapping("metadata")
    public FileMetaData patchMetaData(
            @RequestHeader String user_id,
            @RequestParam String fileName,
            @RequestBody FileMetaData fileMetaData);

    @DeleteMapping("file")
    public void deleteFile(
            @RequestHeader String user_id,
            @RequestParam String fileName);
}
