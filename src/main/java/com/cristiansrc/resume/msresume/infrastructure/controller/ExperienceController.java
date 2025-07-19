package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.ExperienceApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IExperienceService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.util.UrlUtils;
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
        var response = experienceService.experienceGet();
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<ExperienceResponse> experienceIdGet(Long id) {
        var response = experienceService.experienceIdGet(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> experienceIdPut(Long id, ExperienceRequest experienceRequest) {
        experienceService.experienceIdPut(id, experienceRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> experiencePost(ExperienceRequest experienceRequest) {
        var response = experienceService.experiencePost(experienceRequest);
        var uri = UrlUtils.getCreatedResourceUri(response.getId());
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    public ResponseEntity<Void> experienceIdDelete(Long id) {
        experienceService.experienceIdDelete(id);
        return ResponseEntity.noContent().build();
    }
}
