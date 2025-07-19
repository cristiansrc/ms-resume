package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.ImageUrlApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IImageUrlService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlResponse;
import com.cristiansrc.resume.msresume.infrastructure.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImageUrlController implements ImageUrlApi {

    private final IImageUrlService imageUrlService;

    @Override
    public ResponseEntity<List<ImageUrlResponse>> imageUrlGet() {
        var response = imageUrlService.imageUrlGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> imageUrlIdDelete(Long id) {
        imageUrlService.imageUrlIdDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ImageUrlResponse> imageUrlIdGet(Long id) {
        var response = imageUrlService.imageUrlIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> imageUrlPost(ImageUrlRequest imageUrlRequest) {
        var response = imageUrlService.imageUrlPost(imageUrlRequest);
        var uri = UrlUtils.getCreatedResourceUri(response.getId());
        return ResponseEntity.created(uri).body(response);
    }
}
