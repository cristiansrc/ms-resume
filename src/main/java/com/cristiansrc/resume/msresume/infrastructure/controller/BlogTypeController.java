package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.BlogTypeApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IBlogTypeService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogTypeController implements BlogTypeApi {

    private final IBlogTypeService blogTypeService;

    @Override
    public ResponseEntity<List<BlogTypeResponse>> blogTypeGet() {
        var response = blogTypeService.blogTypeGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> blogTypeIdDelete(Long id) {
        blogTypeService.blogTypeIdDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<BlogTypeResponse> blogTypeIdGet(Long id) {
        var response = blogTypeService.blogTypeIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> blogTypeIdPut(Long id, BlogTypeRequest blogTypeRequest) {
        blogTypeService.blogTypeIdPut(id, blogTypeRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> blogTypePost(BlogTypeRequest blogTypeRequest) {
        var response = blogTypeService.blogTypePost(blogTypeRequest);
        var url = UrlUtils.getCreatedResourceUri(response.getId());
        return ResponseEntity.created(url).body(response);
    }
}
