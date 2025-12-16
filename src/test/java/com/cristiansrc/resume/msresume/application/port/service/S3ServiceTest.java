package com.cristiansrc.resume.msresume.application.port.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class S3ServiceTest {

    @Mock
    private S3Client s3Client;

    @InjectMocks
    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(s3Service, "bucketName", "test-bucket");
        ReflectionTestUtils.setField(s3Service, "awsUrlFormat", "https://%s.s3.amazonaws.com/%s");
    }

    @Test
    void uploadFile() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenReturn(null);
        assertDoesNotThrow(() -> s3Service.uploadFile(file));
    }

    @Test
    void uploadFile_returnsKeyContainsOriginalFilename() {
        MockMultipartFile file = new MockMultipartFile("file", "myfile.png", "image/png", "data".getBytes());
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenReturn(null);
        String key = s3Service.uploadFile(file);
        assertNotNull(key);
        assertTrue(key.contains("myfile.png"));
    }

    @Test
    void uploadFile_ioException_throwsRuntimeException() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("a.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getBytes()).thenThrow(new IOException("io"));

        assertThrows(RuntimeException.class, () -> s3Service.uploadFile(file));
    }

    @Test
    void uploadFile_nullOriginalFilename_returnsKeyWithNull() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn(null);
        when(file.getContentType()).thenReturn("text/plain");
        try {
            when(file.getBytes()).thenReturn("x".getBytes());
        } catch (IOException e) {
            // won't happen for this stub
        }
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenReturn(null);
        String key = s3Service.uploadFile(file);
        assertNotNull(key);
        assertTrue(key.contains("-null") || key.endsWith("-null"));
    }

    @Test
    void downloadFile() throws IOException {
        ResponseInputStream<GetObjectResponse> response = mock(ResponseInputStream.class);
        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(response);
        when(response.readAllBytes()).thenReturn("test".getBytes());
        byte[] result = s3Service.downloadFile("test.txt");
        assertNotNull(result);
    }

    @Test
    void downloadFile_readIOException_throwsRuntimeException() throws IOException {
        ResponseInputStream<GetObjectResponse> response = mock(ResponseInputStream.class);
        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(response);
        when(response.readAllBytes()).thenThrow(new IOException("io"));
        assertThrows(RuntimeException.class, () -> s3Service.downloadFile("test.txt"));
    }

    @Test
    void downloadFile_s3Exception_throwsRuntimeException() {
        when(s3Client.getObject(any(GetObjectRequest.class))).thenThrow(S3Exception.builder().message("err").build());
        assertThrows(RuntimeException.class, () -> s3Service.downloadFile("test.txt"));
    }

    @Test
    void deleteFile() {
        when(s3Client.deleteObject(any(DeleteObjectRequest.class))).thenReturn(null);
        assertDoesNotThrow(() -> s3Service.deleteFile("test.txt"));
    }

    @Test
    void deleteFile_s3Exception_throwsRuntimeException() {
        when(s3Client.deleteObject(any(DeleteObjectRequest.class))).thenThrow(S3Exception.builder().message("err").build());
        assertThrows(RuntimeException.class, () -> s3Service.deleteFile("test.txt"));
    }

    @Test
    void getFileUrl() throws Exception {
        S3Utilities s3Utilities = mock(S3Utilities.class);
        when(s3Client.utilities()).thenReturn(s3Utilities);
        when(s3Utilities.getUrl(any(GetUrlRequest.class))).thenReturn(new URL("http://test.com"));
        assertDoesNotThrow(() -> s3Service.getFileUrl("test.txt"));
    }

    @Test
    void getFileUrl_s3Exception_throwsRuntimeException() {
        S3Utilities s3Utilities = mock(S3Utilities.class);
        when(s3Client.utilities()).thenReturn(s3Utilities);
        when(s3Utilities.getUrl(any(GetUrlRequest.class))).thenThrow(S3Exception.builder().message("err").build());
        assertThrows(RuntimeException.class, () -> s3Service.getFileUrl("test.txt"));
    }

    @Test
    void getAwsUrlFile_returnsFormattedUrl() {
        String url = s3Service.getAwsUrlFile("path/to/file.txt");
        assertEquals("https://test-bucket.s3.amazonaws.com/path/to/file.txt", url);
    }
}
