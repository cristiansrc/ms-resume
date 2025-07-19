package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.SkillApi;
import com.cristiansrc.resume.msresume.application.port.interactor.ISkillService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;
import com.cristiansrc.resume.msresume.infrastructure.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController implements SkillApi {

    private final ISkillService skillService;

    @Override
    public ResponseEntity<List<SkillResponse>> skillGet() {
        var response = skillService.skillGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> skillIdDelete(Long id) {
        skillService.skillIdDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<SkillResponse> skillIdGet(Long id) {
        var response = skillService.skillIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> skillIdPut(Long id, SkillRequest skillRequest) {
        skillService.skillIdPut(id, skillRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> skillPost(SkillRequest skillRequest) {
        var response = skillService.skillPost(skillRequest);
        var url = UrlUtils.getCreatedResourceUri(response.getId());
        return ResponseEntity.created(url).body(response);
    }
}
