package io.github.frandorado.springuploads3.controller;

import io.github.frandorado.springuploads3.service.StorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Log4j2
public class FileUploadController {
    
    private final StorageService storageService;
    
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
    
    @PostMapping(value = "/upload", produces = "application/json")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String key = storageService.upload(file);
        return new ResponseEntity<>(key, HttpStatus.OK);
    }
}
