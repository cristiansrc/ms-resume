package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.VideoUrlApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IVideoUrlService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VideoUrlController implements VideoUrlApi {

    private final IVideoUrlService videoUrlService;

    @Override
    public ResponseEntity<List<VideoUrlResponse>> videoUrlGet() {
        var response = videoUrlService.videoUrlGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> videoUrlIdDelete(Long id) {
        videoUrlService.videoUrlIdDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<VideoUrlResponse> videoUrlIdGet(Long id) {
        var response = videoUrlService.videoUrlIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> videoUrlPost(VideoUrlRequest videoUrlRequest) {
        var response = videoUrlService.videoUrlPost(videoUrlRequest);
        var url = UrlUtils.getCreatedResourceUri(response.getId());
        return ResponseEntity.created(url).body(response);
    }
}
