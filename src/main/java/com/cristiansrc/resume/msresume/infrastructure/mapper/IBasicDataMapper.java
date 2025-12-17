package com.cristiansrc.resume.msresume.infrastructure.mapper;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataRequest;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BasicDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IBasicDataMapper {

    void updateBasicDataEntityFromBasicDataRequest(
            BasicDataRequest basicDataRequest, @MappingTarget BasicDataEntity basicDataEntity);

    BasicDataResponse toBasicDataResponse(BasicDataEntity basicDataEntity);
}
