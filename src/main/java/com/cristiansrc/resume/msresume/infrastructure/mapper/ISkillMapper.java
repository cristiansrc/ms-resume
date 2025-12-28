package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.SkillSonRelationalEntity;
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
    @Mapping(target = "skillSons", source = "skillSons")
    SkillResponse toResponse(SkillEntity skillEntity);
    List<SkillResponse> toResponseList(List<SkillEntity> skillEntities);

    default List<SkillSonResponse> mapSkillSonRelationalToSkillSonResponse(List<SkillSonRelationalEntity> relations) {
        if (relations == null) {
            return List.of();
        }
        return relations.stream()
                .map(relation -> {
                    var skillSon = relation.getSkillSon();
                    var response = new SkillSonResponse();
                    response.setId(skillSon.getId());
                    response.setName(skillSon.getName());
                    response.setNameEng(skillSon.getNameEng());
                    return response;
                })
                .toList();
    }
}
