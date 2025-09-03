package com.PawSathi.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
     String storeFile(MultipartFile file, String uploadDir);
    boolean deleteFile(String filePath);

}
