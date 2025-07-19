package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillSonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISkillSonMapper {

    @Mapping(target = "name", source = "name")
    SkillSonEntity toEntity(SkillSonRequest skillSonRequest);
    void updateEntity(SkillSonRequest skillSonRequest, @MappingTarget SkillSonEntity skillSonEntity);

    @Mapping(target = "id", source = "id")
    SkillSonResponse toRequest(SkillSonEntity skillSonEntity);
    List<SkillSonResponse> toRequestList(List<SkillSonEntity> skillSonEntities);

}
