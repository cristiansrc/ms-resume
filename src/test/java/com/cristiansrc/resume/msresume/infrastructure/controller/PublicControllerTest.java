package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.interactor.IBasicDataService;
import com.cristiansrc.resume.msresume.application.port.interactor.IBlogService;
import com.cristiansrc.resume.msresume.application.port.interactor.IExperienceService;
import com.cristiansrc.resume.msresume.application.port.interactor.IFuturedProjectService;
import com.cristiansrc.resume.msresume.application.port.interactor.IHomeService;
import com.cristiansrc.resume.msresume.application.port.interactor.ILabelService;
import com.cristiansrc.resume.msresume.application.port.interactor.ISkillService;
import com.cristiansrc.resume.msresume.application.port.interactor.ISkillSonService;
import com.cristiansrc.resume.msresume.application.port.interactor.ISkillTypeService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BasicDataResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogPageResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.BlogResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.ExperienceResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.FuturedProjectResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.HomeResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LabelResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillSonResponse;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.SkillTypeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PublicControllerTest {

    @Mock
    private IBasicDataService basicDataService;
    @Mock
    private IBlogService blogService;
    @Mock
    private IExperienceService experienceService;
    @Mock
    private IFuturedProjectService futuredProjectService;
    @Mock
    private IHomeService homeService;
    @Mock
    private ILabelService labelService;
    @Mock
    private ISkillService skillService;
    @Mock
    private ISkillSonService skillSonService;
    @Mock
    private ISkillTypeService skillTypeService;

    @InjectMocks
    private PublicController publicController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void publicBasicDataIdGet() {
        when(basicDataService.basicDataIdGet(1L)).thenReturn(new BasicDataResponse());
        ResponseEntity<BasicDataResponse> response = publicController.publicBasicDataIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void publicBlogGet() {
        when(blogService.blogGet(any(), any(), any())).thenReturn(new BlogPageResponse());
        ResponseEntity<BlogPageResponse> response = publicController.publicBlogGet(0, 10, "DESC");
        assertNotNull(response);
    }

    @Test
    void publicBlogIdGet() {
        when(blogService.blogIdGet(1L)).thenReturn(new BlogResponse());
        ResponseEntity<BlogResponse> response = publicController.publicBlogIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void publicExperienceGet() {
        when(experienceService.experienceGet()).thenReturn(Collections.singletonList(new ExperienceResponse()));
        ResponseEntity<List<ExperienceResponse>> response = publicController.publicExperienceGet();
        assertNotNull(response);
    }

    @Test
    void publicExperienceIdGet() {
        when(experienceService.experienceIdGet(1L)).thenReturn(new ExperienceResponse());
        ResponseEntity<ExperienceResponse> response = publicController.publicExperienceIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void publicFuturedProjectGet() {
        when(futuredProjectService.futuredProjectGet()).thenReturn(Collections.singletonList(new FuturedProjectResponse()));
        ResponseEntity<List<FuturedProjectResponse>> response = publicController.publicFuturedProjectGet();
        assertNotNull(response);
    }

    @Test
    void publicFuturedProjectIdGet() {
        when(futuredProjectService.futuredProjectIdGet(1L)).thenReturn(new FuturedProjectResponse());
        ResponseEntity<FuturedProjectResponse> response = publicController.publicFuturedProjectIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void publicHomeIdGet() {
        when(homeService.homeIdGet(1L)).thenReturn(new HomeResponse());
        ResponseEntity<HomeResponse> response = publicController.publicHomeIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void publicLabelGet() {
        when(labelService.labelGet()).thenReturn(Collections.singletonList(new LabelResponse()));
        ResponseEntity<List<LabelResponse>> response = publicController.publicLabelGet();
        assertNotNull(response);
    }

    @Test
    void publicLabelIdGet() {
        when(labelService.labelIdGet(1L)).thenReturn(new LabelResponse());
        ResponseEntity<LabelResponse> response = publicController.publicLabelIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void publicSkillGet() {
        when(skillService.skillGet()).thenReturn(Collections.singletonList(new SkillResponse()));
        ResponseEntity<List<SkillResponse>> response = publicController.publicSkillGet();
        assertNotNull(response);
    }

    @Test
    void publicSkillIdGet() {
        when(skillService.skillIdGet(1L)).thenReturn(new SkillResponse());
        ResponseEntity<SkillResponse> response = publicController.publicSkillIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void publicSkillSonGet() {
        when(skillSonService.skillSonGet()).thenReturn(Collections.singletonList(new SkillSonResponse()));
        ResponseEntity<List<SkillSonResponse>> response = publicController.publicSkillSonGet();
        assertNotNull(response);
    }

    @Test
    void publicSkillSonIdGet() {
        when(skillSonService.skillSonIdGet(1L)).thenReturn(new SkillSonResponse());
        ResponseEntity<SkillSonResponse> response = publicController.publicSkillSonIdGet(1L);
        assertNotNull(response);
    }

    @Test
    void publicSkillTypeGet() {
        when(skillTypeService.skillTypeGet()).thenReturn(Collections.singletonList(new SkillTypeResponse()));
        ResponseEntity<List<SkillTypeResponse>> response = publicController.publicSkillTypeGet();
        assertNotNull(response);
    }

    @Test
    void publicSkillTypeIdGet() {
        when(skillTypeService.skillTypeIdGet(1L)).thenReturn(new SkillTypeResponse());
        ResponseEntity<SkillTypeResponse> response = publicController.publicSkillTypeIdGet(1L);
        assertNotNull(response);
    }
}
