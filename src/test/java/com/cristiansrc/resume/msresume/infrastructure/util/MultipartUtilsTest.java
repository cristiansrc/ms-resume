package com.cristiansrc.resume.msresume.infrastructure.util;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MultipartUtilsTest {

    @Test
    void base64ToMultipart() {
        String base64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";
        MultipartFile multipartFile = MultipartUtils.base64ToMultipart(base64, "test.png");
        assertNotNull(multipartFile);
    }

    @Test
    void base64ToMultipart_noHeader() {
        String base64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";
        MultipartFile multipartFile = MultipartUtils.base64ToMultipart(base64, "test.png");
        assertNotNull(multipartFile);
    }

    @Test
    void base64ToMultipart_null() {
        assertThrows(IllegalArgumentException.class, () -> MultipartUtils.base64ToMultipart(null, "test.png"));
    }

    @Test
    void base64ToMultipart_empty() {
        assertThrows(IllegalArgumentException.class, () -> MultipartUtils.base64ToMultipart("", "test.png"));
    }
}
