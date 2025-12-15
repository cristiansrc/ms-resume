package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.HomeApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IHomeService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController implements HomeApi {

    private final IHomeService homeService;

    @Override
    public ResponseEntity<HomeResponse> homeIdGet(Long id) {
        var response = homeService.homeIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> homeIdPut(Long id, HomeRequest homeRequest) {
        homeService.homeIdPut(id, homeRequest);
        return ResponseEntity.noContent().build();
    }
}
