package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.exception.RenderCvServiceException;
import com.cristiansrc.resume.msresume.application.port.interactor.IEducationService;
import com.cristiansrc.resume.msresume.application.port.interactor.IExperienceService;
import com.cristiansrc.resume.msresume.application.port.interactor.ISkillService;
import com.cristiansrc.resume.msresume.application.port.output.client.IRenderCvClient;
import com.cristiansrc.resume.msresume.application.port.output.repository.jpa.IBasicDataRepository;
import com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in.CustomerCvIn;
import com.cristiansrc.resume.msresume.infrastructure.client.rendercv.out.CustomerCvOut;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.EducationResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;
import com.cristiansrc.resume.msresume.infrastructure.mapper.IBasicDataMapper;
import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BasicDataEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CvServiceTest {

    @Mock
    private IBasicDataRepository basicDataRepository;

    @Mock
    private ISkillService skillService;

    @Mock
    private IExperienceService experienceService;

    @Mock
    private IEducationService educationService;

    @Mock
    private IBasicDataMapper basicDataMapper;

    @Mock
    private IRenderCvClient renderCvClient;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CvService cvService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(cvService, "webSite", "https://example.com");
        ReflectionTestUtils.setField(cvService, "theme", "classic");
        ReflectionTestUtils.setField(cvService, "linkedinUrl", "https://linkedin.com/in/");
        ReflectionTestUtils.setField(cvService, "githubUrl", "https://github.com/");
        ReflectionTestUtils.setField(cvService, "xUrl", "https://x.com/");
        ReflectionTestUtils.setField(cvService, "instagramUrl", "https://instagram.com/");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void generateCurriculum_success() {
        // Arrange
        String language = "english";
        BasicDataEntity basicDataEntity = new BasicDataEntity();
        BasicDataResponse basicDataResponse = new BasicDataResponse();
        basicDataResponse.setFirstName("John");
        basicDataResponse.setFirstSurName("Doe");
        basicDataResponse.setEmail("john@example.com");
        basicDataResponse.setDescription("Desc");
        basicDataResponse.setDescriptionEng("Desc Eng");
        
        List<SkillResponse> skills = Collections.emptyList();
        List<ExperienceResponse> experiences = Collections.emptyList();
        List<EducationResponse> educations = Collections.emptyList();

        CustomerCvOut customerCvOut = new CustomerCvOut();
        customerCvOut.setPdfBase(Base64.getEncoder().encodeToString("pdf content".getBytes()));

        when(basicDataRepository.findFirstByOrderByCreatedDesc()).thenReturn(Optional.of(basicDataEntity));
        when(basicDataMapper.toBasicDataResponse(basicDataEntity)).thenReturn(basicDataResponse);
        when(skillService.skillGet()).thenReturn(skills);
        when(experienceService.experienceGet()).thenReturn(experiences);
        when(educationService.educationGet()).thenReturn(educations);
        when(renderCvClient.renderCv(any(CustomerCvIn.class))).thenReturn(customerCvOut);

        // Act
        Resource resource = cvService.generateCurriculum(language);

        // Assert
        assertNotNull(resource);
        verify(renderCvClient).renderCv(any(CustomerCvIn.class));
    }

    @Test
    void generateCurriculum_basicDataNotFound() {
        // Arrange
        when(basicDataRepository.findFirstByOrderByCreatedDesc()).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RenderCvServiceException.class, () -> cvService.generateCurriculum("english"));
    }
}
