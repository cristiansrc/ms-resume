package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IExperienceService {
    ResponseEntity<List<ExperienceResponse>> experienceGet();
    ResponseEntity<ExperienceResponse> experienceIdGet(Long id);
    ResponseEntity<Void> experienceIdPut(Long id, ExperienceRequest experienceRequest);
    ResponseEntity<ImageUrlPost201Response> experiencePost(ExperienceRequest experienceRequest);
    ResponseEntity<Void> experienceIdDelete(Long id);
}
