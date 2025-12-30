package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.EducationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEducationMapper {

    EducationEntity toEducationEntity(EducationRequest educationRequest);

    void updateEducationEntityFromRequest(
            EducationRequest educationRequest, @MappingTarget EducationEntity educationEntity);

    EducationResponse toEducationResponse(EducationEntity educationEntity);

    List<EducationResponse> toEducationResponseList(List<EducationEntity> educationEntities);
}
