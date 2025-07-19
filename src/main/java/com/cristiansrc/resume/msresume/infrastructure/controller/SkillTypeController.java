package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.SkillTypeApi;
import com.cristiansrc.resume.msresume.application.port.interactor.ISkillTypeService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillTypeController implements SkillTypeApi {

    private final ISkillTypeService skillTypeService;

    @Override
    public ResponseEntity<List<SkillTypeResponse>> skillTypeGet() {
        var response = skillTypeService.skillTypeGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> skillTypeIdDelete(Long id) {
        skillTypeService.skillTypeIdDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<SkillTypeResponse> skillTypeIdGet(Long id) {
        var response = skillTypeService.skillTypeIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> skillTypeIdPut(Long id, SkillTypeRequest skillTypeRequest) {
        skillTypeService.skillTypeIdPut(id, skillTypeRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> skillTypePost(SkillTypeRequest skillTypeRequest) {
        ImageUrlPost201Response response = skillTypeService.skillTypePost(skillTypeRequest);
        var uri = UrlUtils.getCreatedResourceUri(response.getId());
        return ResponseEntity.created(uri).body(response);
    }
}
