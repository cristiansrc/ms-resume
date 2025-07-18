package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.FuturedProjectApi;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class FuturedProjectController implements FuturedProjectApi {
    @Override
    public ResponseEntity<List<FuturedProjectResponse>> futuredProjectGet() {
        return FuturedProjectApi.super.futuredProjectGet();
    }

    @Override
    public ResponseEntity<Void> futuredProjectIdDelete(Long id) {
        return FuturedProjectApi.super.futuredProjectIdDelete(id);
    }

    @Override
    public ResponseEntity<FuturedProjectResponse> futuredProjectIdGet(Long id) {
        return FuturedProjectApi.super.futuredProjectIdGet(id);
    }

    @Override
    public ResponseEntity<Void> futuredProjectIdPut(Long id, FuturedProjectRequest futuredProjectRequest) {
        return FuturedProjectApi.super.futuredProjectIdPut(id, futuredProjectRequest);
    }

    @Override
    public ResponseEntity<ImageUrlPost201Response> futuredProjectPost(FuturedProjectRequest futuredProjectRequest) {
        return FuturedProjectApi.super.futuredProjectPost(futuredProjectRequest);
    }
}
