package com.cristiansrc.resume.msresume.application.port.interactor;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public interface IS3Service {
    String getAwsUrlFile(String key);
    String uploadFile(MultipartFile file);
    byte[] downloadFile(String key);
    void deleteFile(String key);
    URL getFileUrl(String key);
}
