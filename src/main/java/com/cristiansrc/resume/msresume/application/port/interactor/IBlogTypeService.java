package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;

import java.util.List;

public interface IBlogTypeService {
    List<BlogTypeResponse> blogTypeGet();
    void blogTypeIdDelete(Long identifier);
    BlogTypeResponse blogTypeIdGet(Long identifier);
    void blogTypeIdPut(Long identifier, BlogTypeRequest blogTypeRequest);
    ImageUrlPost201Response blogTypePost(BlogTypeRequest blogTypeRequest);
}
