package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillTypeEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillTypeRelationalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = ISkillMapper.class)
public interface ISkillTypeMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "skillTypeRelations", ignore = true)
    SkillTypeEntity toEntity(SkillTypeRequest skillTypeRequest);
    
    @Mapping(target = "skillTypeRelations", ignore = true)
    void updateEntity(SkillTypeRequest skillTypeRequest, @MappingTarget SkillTypeEntity skillTypeEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "skills", source = "skillTypeRelations")
    SkillTypeResponse toResponse(SkillTypeEntity skillTypeEntity);
    
    List<SkillTypeResponse> toResponsetList(List<SkillTypeEntity> skillTypeEntities);

    @Mapping(target = ".", source = "skill")
    SkillResponse toSkillResponse(SkillTypeRelationalEntity relation);
}
