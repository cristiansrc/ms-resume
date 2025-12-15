package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class VideoUrlServiceTest {

    @InjectMocks
    private VideoUrlService videoUrlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void videoUrlGet() {
        List<VideoUrlResponse> response = videoUrlService.videoUrlGet();
        assertNotNull(response);
    }

    @Test
    void videoUrlIdDelete() {
        videoUrlService.videoUrlIdDelete(1L);
    }

    @Test
    void videoUrlIdGet() {
        videoUrlService.videoUrlIdGet(1L);
    }

    @Test
    void videoUrlPost() {
        videoUrlService.videoUrlPost(null);
    }
}
