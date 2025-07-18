package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IExperienceMapper {

    @Mapping(target = "yearStart", source = "yearStart")
    @Mapping(target = "yearEnd", source = "yearEnd")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "description", source = "description")
    ExperienceEntity toExperienceEntity(ExperienceRequest experienceRequest);
    void updateExperienceEntityFromRequest(
            ExperienceRequest experienceRequest, @MappingTarget ExperienceEntity experienceEntity);

    @Mapping(target = "id", source = "id")
    ExperienceResponse toExperienceResponse(ExperienceEntity experienceEntity);
    List<ExperienceResponse> toExperienceResponseList(List<ExperienceEntity> experienceEntities);

}
