package io.github.frandorado.springuploads3.service;

import org.springframework.web.multipart.MultipartFile;

import io.github.frandorado.springuploads3.service.model.DownloadedResource;

public interface StorageService {
    
    String upload(MultipartFile multipartFile);
    
    DownloadedResource download(String id);
}
