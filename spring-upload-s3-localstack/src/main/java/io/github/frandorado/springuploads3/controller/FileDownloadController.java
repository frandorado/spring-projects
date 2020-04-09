package io.github.frandorado.springuploads3.controller;

import io.github.frandorado.springuploads3.service.model.DownloadedResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.frandorado.springuploads3.service.StorageService;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class FileDownloadController {
    
    private final StorageService storageService;
    
    public FileDownloadController(StorageService storageService) {
        this.storageService = storageService;
    }
    
    @GetMapping("/download")
    public ResponseEntity<Resource> download(String id) {
        DownloadedResource downloadedResource = storageService.download(id);
        
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadedResource.getFileName())
                .contentLength(downloadedResource.getContentLength()).contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(downloadedResource.getInputStream()));
    }
}
