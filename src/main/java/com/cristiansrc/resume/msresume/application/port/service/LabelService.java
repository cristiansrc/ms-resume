package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.ILabelService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.ILabelRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ImageUrlPost201Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.ILabelMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.LabelEntity;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LabelService implements ILabelService {

    private final ILabelRepository labelRepository;
    private final ILabelMapper labelMapper;
    private final MessageResolver messageResolver;

    @Transactional(readOnly = true)
    @Override
    public List<LabelResponse> labelGet() {
        log.info("Fetching all labels");
        var listEntities = labelRepository.findAll();
        var listResponse = labelMapper.labelToLabelResponse(listEntities);
        log.info("Fetched {} labels", listResponse.size());
        return listResponse;
    }

    @Transactional
    @Override
    public void labelIdDelete(Long id) {
        log.info("Deleting label with id: {}", id);
        var entity = entityById(id);
        labelRepository.delete(entity);
        log.info("Label with id: {} deleted successfully", id);
    }

    @Transactional(readOnly = true)
    @Override
    public LabelResponse labelIdGet(Long id) {
        log.info("Fetching label with id: {}", id);
        var entity = entityById(id);
        var response = labelMapper.labelToLabelResponse(entity);
        log.info("Fetched label with id: {}", id);
        return response;
    }

    @Transactional
    @Override
    public ImageUrlPost201Response labelPost(LabelRequest labelRequest) {
        log.info("Creating new label with name: {}", labelRequest.getName());
        var labelEntity = labelMapper.labelToLabelEntity(labelRequest);
        labelEntity = labelRepository.save(labelEntity);
        var response = new ImageUrlPost201Response();
        response.setId(labelEntity.getId());
        log.info("Created label with id: {}", labelEntity.getId());
        return response;
    }

    private LabelEntity entityById(Long id) {
        return labelRepository.findById(id)
                .orElseThrow(() -> messageResolver.notFound("label.not.found", id));
    }
}
