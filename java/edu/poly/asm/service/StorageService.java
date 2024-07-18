package edu.poly.asm.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {

    String getStoredFilename(MultipartFile file, String id);

    void storeFile(MultipartFile file, String storedFilename);

    Resource loadFileAsResource(String filename);

    Path load(String filename);

    void deleteFile(String filename) throws IOException;

    void init();
}
