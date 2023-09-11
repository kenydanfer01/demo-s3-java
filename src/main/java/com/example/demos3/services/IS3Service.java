package com.example.demos3.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3Service {
    String uploadFile(MultipartFile file) throws IOException;
    String downloadFile(String fileName) throws IOException;
}
