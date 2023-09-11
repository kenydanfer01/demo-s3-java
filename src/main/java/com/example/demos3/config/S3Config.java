package com.example.demos3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${aws.PUBLIC_ACCESS_KEY}")
    private String PUBLIC_ACCESS_KEY;

    @Value("${aws.SECRET-ACCESS-KEY}")
    private String SECRET_ACCESS_KEY;

    @Bean
    public S3Client s3Client() {
        Region region = Region.US_EAST_1;

        AwsCredentials awsCredentials = AwsBasicCredentials.create(PUBLIC_ACCESS_KEY, SECRET_ACCESS_KEY);

        S3Client s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();

        return s3Client;
    }
}
