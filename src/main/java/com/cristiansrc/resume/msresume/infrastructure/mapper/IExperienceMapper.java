package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.ExperienceSkillEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IExperienceMapper {

    @Mapping(target = "experienceSkills", ignore = true)
    @Mapping(target = "descriptionItems", source = "descriptionItemsPdf")
    @Mapping(target = "descriptionItemsEng", source = "descriptionItemsPdfEng")
    @Mapping(target = "companyEng", source = "company")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "locationEng", source = "locationEng")
    ExperienceEntity toExperienceEntity(ExperienceRequest experienceRequest);
    
    @Mapping(target = "experienceSkills", ignore = true)
    @Mapping(target = "descriptionItems", source = "descriptionItemsPdf")
    @Mapping(target = "descriptionItemsEng", source = "descriptionItemsPdfEng")
    @Mapping(target = "companyEng", source = "company")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "locationEng", source = "locationEng")
    void updateExperienceEntityFromRequest(
            ExperienceRequest experienceRequest, @MappingTarget ExperienceEntity experienceEntity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "skillSons", source = "experienceSkills")
    @Mapping(target = "descriptionItemsPdf", source = "descriptionItems")
    @Mapping(target = "descriptionItemsPdfEng", source = "descriptionItemsEng")
    ExperienceResponse toExperienceResponse(ExperienceEntity experienceEntity);
    
    List<ExperienceResponse> toExperienceResponseList(List<ExperienceEntity> experienceEntities);

    default List<SkillSonResponse> mapExperienceSkillsToSkillSonResponses(List<ExperienceSkillEntity> experienceSkills) {
        if (experienceSkills == null) {
            return new ArrayList<>();
        }
        return experienceSkills.stream()
                .map(experienceSkill -> {
                    var skillSon = experienceSkill.getSkillSon();
                    var response = new SkillSonResponse();
                    response.setId(skillSon.getId());
                    response.setName(skillSon.getName());
                    response.setNameEng(skillSon.getNameEng());
                    return response;
                })
                .toList();
    }
}
