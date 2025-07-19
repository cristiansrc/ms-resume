package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.VideoUrlResponse;

import java.util.List;

public interface IVideoUrlService {
    List<VideoUrlResponse> videoUrlGet();
    void videoUrlIdDelete(Long id);
    VideoUrlResponse videoUrlIdGet(Long id);
    ImageUrlPost201Response videoUrlPost(VideoUrlRequest videoUrlRequest);
}
