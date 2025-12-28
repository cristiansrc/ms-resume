package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IBasicDataService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBasicDataRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBasicDataMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BasicDataEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BasicDataService implements IBasicDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicDataService.class);
    private final IBasicDataRepository repository;
    private final IBasicDataMapper mapper;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public BasicDataResponse basicDataIdGet(final Long identifier) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(messageResolver.getMessage("basic.data.fetch.start", identifier));
        }

        final BasicDataResponse response = repository.findById(identifier)
                .map(mapper::toBasicDataResponse)
                .orElseThrow(() -> messageResolver.notFound("basic.data.not.found", identifier));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(messageResolver.getMessage("basic.data.fetch.success", identifier));
        }
        return response;
    }

    @Transactional
    @Override
    public void basicDataIdPut(final Long identifier, final BasicDataRequest basicDataRequest) {
        Objects.requireNonNull(basicDataRequest, messageResolver.getMessage("basic.data.request.null"));
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(messageResolver.getMessage("basic.data.update.start", identifier));
        }

        final BasicDataEntity basicData = repository.findById(identifier)
                .orElseThrow(() -> messageResolver.notFound("basic.data.not.found", identifier));

        mapper.updateBasicDataEntityFromBasicDataRequest(basicDataRequest, basicData);
        repository.save(basicData);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(messageResolver.getMessage("basic.data.update.success", identifier));
        }
    }

}
