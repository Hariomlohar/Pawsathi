package com.PawSathi.service.imple;

import com.PawSathi.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    public String storeFile(MultipartFile file, String uploadDir) {
        try {
            // Create directory if not exists
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Generate unique file name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            // Save the file
            Files.write(path, file.getBytes());

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

     @Override
    public boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            System.out.println("Failed to delete file: " + e.getMessage());
        }
        return false;
    }

}
