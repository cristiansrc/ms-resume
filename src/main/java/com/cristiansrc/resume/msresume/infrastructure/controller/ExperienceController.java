package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.ExperienceApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IExperienceService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExperienceController implements ExperienceApi {

    private final IExperienceService experienceService;

    @Override
    public ResponseEntity<List<ExperienceResponse>> experienceGet() {
        return experienceService.experienceGet();
    }

    @Override
    public ResponseEntity<ExperienceResponse> experienceIdGet(Long id) {
        return experienceService.experienceIdGet(id);
    }

    @Override
    public ResponseEntity<Void> experienceIdPut(Long id, ExperienceRequest experienceRequest) {
        return experienceService.experienceIdPut(id, experienceRequest);
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> experiencePost(ExperienceRequest experienceRequest) {
        return experienceService.experiencePost(experienceRequest);
    }

    @Override
    public ResponseEntity<Void> experienceIdDelete(Long id) {
        return experienceService.experienceIdDelete(id);
    }
}
