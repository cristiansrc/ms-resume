package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BasicDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBasicDataResponseMapper {


    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "othersName", source = "othersName")
    @Mapping(target = "firstSurname", source = "firstSurname")
    @Mapping(target = "othersSurname", source = "othersSurname")
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
    BasicDataResponse toBasicDataResponse(BasicDataEntity basicDataEntity);
}
