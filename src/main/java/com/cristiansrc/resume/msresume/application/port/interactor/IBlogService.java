package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import org.springframework.http.ResponseEntity;

public interface IBlogService {
    ResponseEntity<BlogPageResponse> blogGet(Integer page, Integer size, String sort);
    ResponseEntity<Void> blogIdDelete(Long id);
    ResponseEntity<BlogResponse> blogIdGet(Long id);
    ResponseEntity<Void> blogIdPut(Long id, BlogRequest blogRequest);
    ResponseEntity<ImageUrlPost201Response> blogPost(BlogRequest blogRequest);
}
