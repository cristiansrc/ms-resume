package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BasicDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IBasicDataMapper {

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "othersName", source = "othersName")
    @Mapping(target = "firstSurname", source = "firstSurName")
    @Mapping(target = "othersSurname", source = "othersSurName")
    @Mapping(target = "dateBirth", source = "dateBirth")
    @Mapping(target = "located", source = "located")
    @Mapping(target = "startWorkingDate", source = "startWorkingDate")
    @Mapping(target = "greeting", source = "greeting")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "instagram", source = "instagram")
    @Mapping(target = "linkedin", source = "linkedin")
    @Mapping(target = "x", source = "x")
    @Mapping(target = "github", source = "github")
    @Mapping(target = "description", source = "description")
    void updateBasicDataEntityFromBasicDataRequest(
            BasicDataRequest basicDataRequest, @MappingTarget BasicDataEntity basicDataEntity);

    @Mapping(target = "id", source = "id")
    BasicDataResponse toBasicDataResponse(BasicDataEntity basicDataEntity);
}
