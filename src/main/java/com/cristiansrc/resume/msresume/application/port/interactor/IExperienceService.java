package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IExperienceService {
    List<ExperienceResponse> experienceGet();
    ExperienceResponse experienceIdGet(Long id);
    void experienceIdPut(Long id, ExperienceRequest experienceRequest);
    ImageUrlPost201Response experiencePost(ExperienceRequest experienceRequest);
    void experienceIdDelete(Long id);
}
