package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.SkillSonApi;
import com.cristiansrc.resume.msresume.application.port.interactor.ISkillSonService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonResponse;
import com.cristiansrc.resume.msresume.infrastructure.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillSonController implements SkillSonApi {

    private final ISkillSonService skillSonService;

    @Override
    public ResponseEntity<List<SkillSonResponse>> skillSonGet() {
        var response = skillSonService.skillSonGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> skillSonIdDelete(Long id) {
        skillSonService.skillSonIdDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<SkillSonResponse> skillSonIdGet(Long id) {
        var response = skillSonService.skillSonIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> skillSonIdPut(Long id, SkillSonRequest skillSonRequest) {
        skillSonService.skillSonIdPut(id, skillSonRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> skillSonPost(SkillSonRequest skillSonRequest) {
        var response = skillSonService.skillSonPost(skillSonRequest);
        var url = UrlUtils.getCreatedResourceUri(response.getId());
        return ResponseEntity.created(url).body(response);
    }
}
