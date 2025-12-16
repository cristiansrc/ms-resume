package com.cristiansrc.resume.msresume.infrastructure.util;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void base64ToMultipart_withDataUrl() throws IOException {
        String original = "hello world";
        String base64 = java.util.Base64.getEncoder().encodeToString(original.getBytes());
        String dataUrl = "data:text/plain;base64," + base64;

        MultipartFile multipart = MultipartUtils.base64ToMultipart(dataUrl, "file.txt");

        assertNotNull(multipart);
        assertEquals("file.txt", multipart.getOriginalFilename());
        assertEquals("text/plain", multipart.getContentType());
        assertFalse(multipart.isEmpty());
        assertArrayEquals(original.getBytes(), multipart.getBytes());
        assertEquals(original.length(), multipart.getSize());

        // getInputStream
        assertNotNull(multipart.getInputStream());

        // transferTo -> escribe archivo temporal
        File tmp = Files.createTempFile("mu-test-", ".tmp").toFile();
        try {
            multipart.transferTo(tmp);
            byte[] read = Files.readAllBytes(tmp.toPath());
            assertArrayEquals(original.getBytes(), read);
        } finally {
            tmp.delete();
        }
    }

    @Test
    void base64ToMultipart_withoutPrefix() throws IOException {
        String original = "abc123";
        String base64 = java.util.Base64.getEncoder().encodeToString(original.getBytes());

        MultipartFile multipart = MultipartUtils.base64ToMultipart(base64, "a.bin");

        assertNotNull(multipart);
        assertEquals("application/octet-stream", multipart.getContentType());
        assertArrayEquals(original.getBytes(), multipart.getBytes());
    }

    @Test
    void base64ToMultipart_empty_throws() {
        assertThrows(IllegalArgumentException.class, () -> MultipartUtils.base64ToMultipart("", "f"));
        assertThrows(IllegalArgumentException.class, () -> MultipartUtils.base64ToMultipart(null, "f"));
    }
}
