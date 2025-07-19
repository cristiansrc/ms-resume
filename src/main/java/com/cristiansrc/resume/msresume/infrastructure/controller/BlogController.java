package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.BlogApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IBlogService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlogController implements BlogApi {

    private final IBlogService blogService;

    @Override
    public ResponseEntity<BlogPageResponse> blogGet(Integer page, Integer size, String sort) {
        var response = blogService.blogGet(page, size, sort);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> blogIdDelete(Long id) {
        blogService.blogIdDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<BlogResponse> blogIdGet(Long id) {
        var response = blogService.blogIdGet(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> blogIdPut(Long id, BlogRequest blogRequest) {
        blogService.blogIdPut(id, blogRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> blogPost(BlogRequest blogRequest) {
        var response = blogService.blogPost(blogRequest);
        var uri = UrlUtils.getCreatedResourceUri(response.getId());
        return ResponseEntity.created(uri).body(response);
    }
}
