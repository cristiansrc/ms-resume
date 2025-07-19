package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.LabelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ILabelMapper {

    @Mapping(target = "name", source = "name")
    LabelEntity labelToLabelEntity(LabelRequest labelRequest);

    @Mapping(target = "id", source = "id")
    LabelResponse labelToLabelResponse(LabelEntity labelEntity);
    List<LabelResponse> labelToLabelResponse(List<LabelEntity> labelEntities);


}
