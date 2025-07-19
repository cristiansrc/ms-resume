package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;

public interface IBlogService {
    BlogPageResponse blogGet(Integer page, Integer size, String sort);
    void blogIdDelete(Long id);
    BlogResponse blogIdGet(Long id);
    void blogIdPut(Long id, BlogRequest blogRequest);
    ImageUrlPost201Response blogPost(BlogRequest blogRequest);
}
