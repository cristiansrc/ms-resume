package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IVideoUrlService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoUrlService implements IVideoUrlService {

    @Transactional(readOnly = true)
    @Override
    public List<VideoUrlResponse> videoUrlGet() {
        return List.of();
    }

    @Transactional
    @Override
    public void videoUrlIdDelete(Long id) {

    }

    @Transactional(readOnly = true)
    @Override
    public VideoUrlResponse videoUrlIdGet(Long id) {
        return null;
    }

    @Transactional
    @Override
    public ImageUrlPost201Response videoUrlPost(VideoUrlRequest videoUrlRequest) {
        return null;
    }
}
