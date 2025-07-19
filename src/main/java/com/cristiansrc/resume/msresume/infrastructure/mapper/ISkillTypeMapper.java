package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISkillTypeMapper {

    @Mapping(target = "name", source = "name")
    SkillTypeEntity toEntity(SkillTypeRequest skillTypeRequest);
    void updateEntity(SkillTypeRequest skillTypeRequest, @MappingTarget SkillTypeEntity skillTypeEntity);

    @Mapping(target = "id", source = "id")
    SkillTypeResponse toResponse(SkillTypeEntity skillTypeEntity);
    List<SkillTypeResponse> toResponsetList(List<SkillTypeEntity> skillTypeEntities);


}
