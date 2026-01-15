package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.*;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBasicDataRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IHomeRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.InfoPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBasicDataMapper;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IHomeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class InfoPageService implements IInfoPageService {

    private final IHomeRepository homeRepository;
    private final IBasicDataRepository basicDataRepository;
    private final ISkillService skillService;
    private final IExperienceService experienceService;
    private final IEducationService educationService;
    private final IHomeMapper homeMapper;
    private final IBasicDataMapper basicDataMapper;
    private final IS3Service s3Service;
    private final IAltchaService altchaService;

    @Transactional(readOnly = true)
    @Override
    public InfoPageResponse getInfoPage() {
        log.info("Fetching public info page data");

        var homeResponse = getHomeResponse();
        var basicDataResponse = getBasicDataResponse();
        var skills = skillService.skillGet();
        var experiences = experienceService.experienceGet();
        var educations = educationService.educationGet();
        var altchaChallengeResponse = altchaService.createChallenge();

        log.info("Fetched public info page data successfully");

        var infoPageResponse = new InfoPageResponse();
        infoPageResponse.setHome(homeResponse);
        infoPageResponse.setBasicData(basicDataResponse);
        infoPageResponse.setSkills(skills);
        infoPageResponse.setExperiences(experiences);
        infoPageResponse.setEducations(educations);
        infoPageResponse.setAltchaChallenge(altchaChallengeResponse);

        return infoPageResponse;
    }

    private HomeResponse getHomeResponse() {
        return homeRepository.findFirstByOrderByCreatedDesc()
                .map(entity -> homeMapper.toResponse(entity, s3Service))
                .orElse(null);
    }

    private BasicDataResponse getBasicDataResponse() {
        return basicDataRepository.findFirstByOrderByCreatedDesc()
                .map(basicDataMapper::toBasicDataResponse)
                .orElse(null);
    }
}
