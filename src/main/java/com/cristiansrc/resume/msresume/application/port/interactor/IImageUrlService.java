package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlResponse;

import java.util.List;

public interface IImageUrlService {
    List<ImageUrlResponse> imageUrlGet();
    void imageUrlIdDelete(Long identifier);
    ImageUrlResponse imageUrlIdGet(Long identifier);
    ImageUrlPost201Response imageUrlPost(ImageUrlRequest imageUrlRequest);
}
