package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.*;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.*;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.*;
import com.cristiansrc.resume.msresume.infrastructure.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublicService implements IPublicService {

    private final IHomeRepository homeRepository;
    private final IBasicDataRepository basicDataRepository;
    private final ISkillTypeService skillTypeService;
    private final IExperienceService experienceService;
    private final IEducationService educationService;
    private final IHomeMapper homeMapper;
    private final IBasicDataMapper basicDataMapper;
    private final IS3Service s3Service;

    @Transactional(readOnly = true)
    @Override
    public InfoPageResponse getInfoPage() {
        log.info("Fetching public info page data");

        var homeEntity = homeRepository.findAll().stream().findFirst().orElse(null);
        var homeResponse = homeEntity != null ? homeMapper.toResponse(homeEntity, s3Service) : null;

        var basicDataEntity = basicDataRepository.findAll().stream().findFirst().orElse(null);
        var basicDataResponse = basicDataEntity != null ? basicDataMapper.toBasicDataResponse(basicDataEntity) : null;

        var skillTypes = skillTypeService.skillTypeGet();
        var experiences = experienceService.experienceGet();
        var educations = educationService.educationGet();

        log.info("Fetched public info page data successfully");

        var infoPageResponse = new InfoPageResponse();

        infoPageResponse.setHome(homeResponse);
        infoPageResponse.setBasicData(basicDataResponse);
        infoPageResponse.setSkillTypes(skillTypes);
        infoPageResponse.setExperiences(experiences);
        infoPageResponse.setEducations(educations);

        return infoPageResponse;
    }
}
