package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.LabelApi;
import com.cristiansrc.resume.msresume.application.port.interactor.ILabelService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelResponse;
import com.cristiansrc.resume.msresume.infrastructure.util.UrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LabelController implements LabelApi {

    private final ILabelService labelService;

    @Override
    public ResponseEntity<List<LabelResponse>> labelGet() {
        var response = labelService.labelGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> labelIdDelete(Long id) {
        labelService.labelIdDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<LabelResponse> labelIdGet(Long id) {
        var response = labelService.labelIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> labelPost(LabelRequest labelRequest) {
        var response = labelService.labelPost(labelRequest);
        var uri = UrlUtils.getCreatedResourceUri(response.getId());
        return ResponseEntity.created(uri).body(response);
    }
}
