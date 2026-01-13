package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IEducationService;
import com.cristiansrc.resume.msresume.application.port.interactor.IExperienceService;
import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.application.port.interactor.ISkillService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBasicDataRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IHomeRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.*;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBasicDataMapper;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IHomeMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BasicDataEntity;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.HomeEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InfoPageServiceTest {

    @Mock
    private IHomeRepository homeRepository;

    @Mock
    private IBasicDataRepository basicDataRepository;

    @Mock
    private ISkillService skillService;

    @Mock
    private IExperienceService experienceService;

    @Mock
    private IEducationService educationService;

    @Mock
    private IHomeMapper homeMapper;

    @Mock
    private IBasicDataMapper basicDataMapper;

    @Mock
    private IS3Service s3Service;

    @InjectMocks
    private InfoPageService infoPageService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getInfoPage_success() {
        // Arrange
        HomeEntity homeEntity = new HomeEntity();
        HomeResponse homeResponse = new HomeResponse();
        BasicDataEntity basicDataEntity = new BasicDataEntity();
        BasicDataResponse basicDataResponse = new BasicDataResponse();
        List<SkillResponse> skills = Collections.singletonList(new SkillResponse());
        List<ExperienceResponse> experiences = Collections.singletonList(new ExperienceResponse());
        List<EducationResponse> educations = Collections.singletonList(new EducationResponse());

        when(homeRepository.findFirstByOrderByCreatedDesc()).thenReturn(Optional.of(homeEntity));
        when(homeMapper.toResponse(homeEntity, s3Service)).thenReturn(homeResponse);
        when(basicDataRepository.findFirstByOrderByCreatedDesc()).thenReturn(Optional.of(basicDataEntity));
        when(basicDataMapper.toBasicDataResponse(basicDataEntity)).thenReturn(basicDataResponse);
        when(skillService.skillGet()).thenReturn(skills);
        when(experienceService.experienceGet()).thenReturn(experiences);
        when(educationService.educationGet()).thenReturn(educations);

        // Act
        InfoPageResponse response = infoPageService.getInfoPage();

        // Assert
        assertNotNull(response);
        assertEquals(homeResponse, response.getHome());
        assertEquals(basicDataResponse, response.getBasicData());
        assertEquals(skills, response.getSkills());
        assertEquals(experiences, response.getExperiences());
        assertEquals(educations, response.getEducations());

        verify(homeRepository).findFirstByOrderByCreatedDesc();
        verify(basicDataRepository).findFirstByOrderByCreatedDesc();
        verify(skillService).skillGet();
        verify(experienceService).experienceGet();
        verify(educationService).educationGet();
    }
}
