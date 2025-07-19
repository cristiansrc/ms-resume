package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.FuturedProjectApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IFuturedProjectService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FuturedProjectController implements FuturedProjectApi {

    private final IFuturedProjectService futuredProjectService;

    @Override
    public ResponseEntity<List<FuturedProjectResponse>> futuredProjectGet() {
        var response = futuredProjectService.futuredProjectGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> futuredProjectIdDelete(Long id) {
        futuredProjectService.futuredProjectIdDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<FuturedProjectResponse> futuredProjectIdGet(Long id) {
        var response = futuredProjectService.futuredProjectIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> futuredProjectIdPut(Long id, FuturedProjectRequest futuredProjectRequest) {
        futuredProjectService.futuredProjectIdPut(id, futuredProjectRequest);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> futuredProjectPost(FuturedProjectRequest futuredProjectRequest) {
        var response = futuredProjectService.futuredProjectPost(futuredProjectRequest);
        return ResponseEntity.ok(response);
    }
}
