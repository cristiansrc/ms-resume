package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.BlogApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IBlogService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BlogController implements BlogApi {

    private final IBlogService blogService;

    @Override
    public ResponseEntity<BlogPageResponse> blogGet(Integer page, Integer size, String sort) {
        return blogService.blogGet(page, size, sort);
    }

    @Override
    public ResponseEntity<Void> blogIdDelete(Long id) {
        return blogService.blogIdDelete(id);
    }

    @Override
    public ResponseEntity<BlogResponse> blogIdGet(Long id) {
        return blogService.blogIdGet(id);
    }

    @Override
    public ResponseEntity<Void> blogIdPut(Long id, BlogRequest blogRequest) {
        return blogService.blogIdPut(id, blogRequest);
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> blogPost(BlogRequest blogRequest) {
        return blogService.blogPost(blogRequest);
    }
}
