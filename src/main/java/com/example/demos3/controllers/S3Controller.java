package com.example.demos3.controllers;

import com.example.demos3.services.IS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class S3Controller {

    @Autowired
    private IS3Service s3Service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return s3Service.uploadFile(file);
    }

    @GetMapping("/download/{fileName}")
    public String downloadFile(@PathVariable("fileName") String fileName) throws IOException {
        return s3Service.downloadFile(fileName);
    }

}
