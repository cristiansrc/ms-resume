package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.IEducationService;
import com.cristiansrc.resume.msresume.application.port.interactor.IExperienceService;
import com.cristiansrc.resume.msresume.application.port.interactor.IS3Service;
import com.cristiansrc.resume.msresume.application.port.interactor.ISkillTypeService;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBasicDataRepository;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IHomeRepository;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.InfoPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PublicServiceTest {

    @Mock
    private IHomeRepository homeRepository;

    @Mock
    private IBasicDataRepository basicDataRepository;

    @Mock
    private ISkillTypeService skillTypeService;

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
    private PublicService publicService;

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
        List<SkillTypeResponse> skillTypes = Collections.singletonList(new SkillTypeResponse());
        List<ExperienceResponse> experiences = Collections.singletonList(new ExperienceResponse());
        List<EducationResponse> educations = Collections.singletonList(new EducationResponse());

        when(homeRepository.findAll()).thenReturn(Collections.singletonList(homeEntity));
        when(homeMapper.toResponse(homeEntity, s3Service)).thenReturn(homeResponse);
        when(basicDataRepository.findAll()).thenReturn(Collections.singletonList(basicDataEntity));
        when(basicDataMapper.toBasicDataResponse(basicDataEntity)).thenReturn(basicDataResponse);
        when(skillTypeService.skillTypeGet()).thenReturn(skillTypes);
        when(experienceService.experienceGet()).thenReturn(experiences);
        when(educationService.educationGet()).thenReturn(educations);

        // Act
        InfoPageResponse response = publicService.getInfoPage();

        // Assert
        assertNotNull(response);
        assertEquals(homeResponse, response.getHome());
        assertEquals(basicDataResponse, response.getBasicData());
        assertEquals(skillTypes, response.getSkillTypes());
        assertEquals(experiences, response.getExperiences());
        assertEquals(educations, response.getEducations());

        verify(homeRepository).findAll();
        verify(basicDataRepository).findAll();
        verify(skillTypeService).skillTypeGet();
        verify(experienceService).experienceGet();
        verify(educationService).educationGet();
    }

    @Test
    void getInfoPage_emptyData() {
        // Arrange
        when(homeRepository.findAll()).thenReturn(Collections.emptyList());
        when(basicDataRepository.findAll()).thenReturn(Collections.emptyList());
        when(skillTypeService.skillTypeGet()).thenReturn(Collections.emptyList());
        when(experienceService.experienceGet()).thenReturn(Collections.emptyList());
        when(educationService.educationGet()).thenReturn(Collections.emptyList());

        // Act
        InfoPageResponse response = publicService.getInfoPage();

        // Assert
        assertNotNull(response);
        assertNull(response.getHome());
        assertNull(response.getBasicData());
        assertEquals(Collections.emptyList(), response.getSkillTypes());
        assertEquals(Collections.emptyList(), response.getExperiences());
        assertEquals(Collections.emptyList(), response.getEducations());
    }
}
