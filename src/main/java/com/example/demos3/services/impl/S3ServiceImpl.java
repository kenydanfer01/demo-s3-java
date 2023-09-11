package com.example.demos3.services.impl;

import com.example.demos3.services.IS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3ServiceImpl implements IS3Service {

    @Value("${upload.s3.localPath}")
    private String localPath;

    @Value("${aws.NAME-BUCKET-S3}")
    private String bucketName;

    private final S3Client s3Client;

    @Autowired
    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        try {
            String fileName = file.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("bucket-axioma-java-1109")
                    .key(fileName)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            return "Success";

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

    }

    public String downloadFile(String fileName) throws IOException {
        if (!doesObjectExist(fileName)) {
            return "File not found";
        }

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        ResponseInputStream<GetObjectResponse> result = s3Client.getObject(request);

        try (FileOutputStream fos = new FileOutputStream(localPath + fileName)) {
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = result.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return "File downloaded";
    }

    private boolean doesObjectExist(String objectKey) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();
            s3Client.headObject(headObjectRequest);
            return true;

        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                return false;
            }
        }
        return false;
    }
}
