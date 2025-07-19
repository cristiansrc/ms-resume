package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISkillMapper {

    @Mapping(target = "name", source = "name")
    SkillEntity toEntity(SkillRequest skillRequest);
    void updateEntity(SkillRequest skillRequest, @MappingTarget SkillEntity skillEntity);

    @Mapping(target = "id", source = "id")
    SkillResponse toResponse(SkillEntity skillEntity);
    List<SkillResponse> toResponseList(List<SkillEntity> skillEntities);

}
