package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.BasicDataApi;
import com.cristiansrc.resume.msresume.application.port.interactor.IBasicDataService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasicDataController implements BasicDataApi {

    private final IBasicDataService basicDataService;

    @Override
    public ResponseEntity<BasicDataResponse> basicDataIdGet(Long id) {
        return basicDataService.basicDataIdGet(id);
    }

    @Override
    public ResponseEntity<Void> basicDataIdPut(Long id, BasicDataRequest basicDataRequest) {
        return basicDataService.basicDataIdPut(id, basicDataRequest);
    }
}
