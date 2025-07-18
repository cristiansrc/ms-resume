package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.exception.ResourceNotFoundException;
import com.cristiansrc.resume.msresume.application.port.interactor.IBasicDataService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBasicDataRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBasicDataMapper;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class BasicDataService implements IBasicDataService {

    private final IBasicDataRepository basicDataRepository;
    private final IBasicDataMapper basicDataMapper;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<BasicDataResponse> basicDataIdGet(Long id) {
        log.debug(messageResolver.getMessage("basic.data.fetch.start", id));

        var response = basicDataRepository.findById(id)
                .map(basicDataMapper::toBasicDataResponse)
                .orElseThrow(() -> messageResolver.notFound("basic.data.not.found", id));

        log.debug(messageResolver.getMessage("basic.data.fetch.success", id));
        return ResponseEntity.ok(response);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> basicDataIdPut(Long id, BasicDataRequest basicDataRequest) {
        Objects.requireNonNull(basicDataRequest, messageResolver.getMessage("basic.data.request.null"));
        log.info(messageResolver.getMessage("basic.data.update.start", id));

        var basicData = basicDataRepository.findById(id)
                .orElseThrow(() -> messageResolver.notFound("basic.data.not.found", id));

        basicDataMapper.updateBasicDataEntityFromBasicDataRequest(basicDataRequest, basicData);
        basicDataRepository.save(basicData);
        log.info(messageResolver.getMessage("basic.data.update.success", id));
        return ResponseEntity.noContent().build();
    }

}