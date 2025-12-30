package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.EducationApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IEducationService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EducationController implements EducationApi {

    private final IEducationService educationService;

    @Override
    public ResponseEntity<List<EducationResponse>> educationGet() {
        var response = educationService.educationGet();
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<EducationResponse> educationIdGet(Long id) {
        var response = educationService.educationIdGet(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<Void> educationIdPut(Long id, EducationRequest educationRequest) {
        educationService.educationIdPut(id, educationRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> educationPost(EducationRequest educationRequest) {
        var response = educationService.educationPost(educationRequest);
        var uri = UrlUtils.getCreatedResourceUri(response.getId());
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    public ResponseEntity<Void> educationIdDelete(Long id) {
        educationService.educationIdDelete(id);
        return ResponseEntity.noContent().build();
    }
}
