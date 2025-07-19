package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.HomeApi;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController implements HomeApi {
    @Override
    public ResponseEntity<HomeResponse> homeIdGet(Long id) {
        return HomeApi.super.homeIdGet(id);
    }

    @Override
    public ResponseEntity<Void> homeIdPut(Long id, HomeRequest homeRequest) {
        return HomeApi.super.homeIdPut(id, homeRequest);
    }
}
