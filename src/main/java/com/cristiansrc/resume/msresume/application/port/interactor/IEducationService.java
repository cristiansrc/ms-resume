package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;

import java.util.List;

public interface IEducationService {
    List<EducationResponse> educationGet();
    EducationResponse educationIdGet(Long identifier);
    void educationIdPut(Long identifier, EducationRequest educationRequest);
    ImageUrlPost201Response educationPost(EducationRequest educationRequest);
    void educationIdDelete(Long identifier);
}
