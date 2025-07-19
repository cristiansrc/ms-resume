package com.cristiansrc.resume.msresume.application.port.interactor;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import org.springframework.http.ResponseEntity;

public interface IBasicDataService {
    BasicDataResponse basicDataIdGet(Long id);
    void basicDataIdPut(Long id, BasicDataRequest basicDataRequest);
}