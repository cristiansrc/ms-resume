package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements IS3Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);
    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${config.aws.url}")
    private String awsUrlFormat;

    public String getAwsUrlFile(final String key) {
        try {
            // Intentar obtener la URL desde S3Utilities (región/endpoint correcto, certificado válido)
            final GetUrlRequest request = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            final URL url = s3Client.utilities().getUrl(request);
            return url.toString();
        } catch (final S3Exception e) {
            // Fallback a la plantilla configurable (path-style o virtual-hosted según config)
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Could not get S3 utilities URL, falling back to configured format: {}", e.getMessage());
            }
            return String.format(awsUrlFormat, bucketName, key);
        }
    }

    public String uploadFile(final MultipartFile file) {
        try {
            final String key = generateUniqueFileName(file.getOriginalFilename());
            final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return key;
        } catch (final IOException e) {
            LOGGER.error("Error uploading file to S3", e);
            throw new RuntimeException("Error uploading file", e);
        }
    }

    public byte[] downloadFile(final String key) {
        try {
            final GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            final ResponseInputStream<GetObjectResponse> response = s3Client.getObject(getObjectRequest);
            return response.readAllBytes();
        } catch (final IOException | S3Exception e) {
            LOGGER.error("Error downloading file from S3", e);
            throw new RuntimeException("Error downloading file", e);
        }
    }

    public void deleteFile(final String key) {
        try {
            final DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (final S3Exception e) {
            LOGGER.error("Error deleting file from S3", e);
            throw new RuntimeException("Error deleting file", e);
        }
    }

    public URL getFileUrl(final String key) {
        try {
            final GetUrlRequest request = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            return s3Client.utilities().getUrl(request);
        } catch (final S3Exception e) {
            LOGGER.error("Error getting file URL from S3", e);
            throw new RuntimeException("Error getting file URL", e);
        }
    }

    private String generateUniqueFileName(final String originalFilename) {
        return UUID.randomUUID() + "-" + originalFilename;
    }
}
