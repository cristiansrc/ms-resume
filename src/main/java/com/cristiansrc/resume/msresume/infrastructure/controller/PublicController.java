package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.PublicApi;
import com.cristiansrc.resume.msresume.application.port.interactor.*;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicController implements PublicApi {

    private final IBasicDataService basicDataService;
    private final IBlogService blogService;
    private final IExperienceService experienceService;
    private final IFuturedProjectService futuredProjectService;
    private final IHomeService homeService;
    private final ILabelService labelService;
    private final ISkillService skillService;
    private final ISkillSonService skillSonService;
    private final ISkillTypeService skillTypeService;

    @Override
    public ResponseEntity<BasicDataResponse> publicBasicDataIdGet(Long id) {
        var response = basicDataService.basicDataIdGet(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<BlogPageResponse> publicBlogGet(Integer page, Integer size, String sort) {
        var response = blogService.blogGet(page, size, sort);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<BlogResponse> publicBlogIdGet(Long id) {
        var response = blogService.blogIdGet(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<ExperienceResponse>> publicExperienceGet() {
        var response = experienceService.experienceGet();
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<ExperienceResponse> publicExperienceIdGet(Long id) {
        var response = experienceService.experienceIdGet(id);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<FuturedProjectResponse>> publicFuturedProjectGet() {
        var response = futuredProjectService.futuredProjectGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<FuturedProjectResponse> publicFuturedProjectIdGet(Long id) {
        var response = futuredProjectService.futuredProjectIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<HomeResponse> publicHomeIdGet(Long id) {
        var response = homeService.homeIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<LabelResponse>> publicLabelGet() {
        var response = labelService.labelGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LabelResponse> publicLabelIdGet(Long id) {
        var response = labelService.labelIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SkillResponse>> publicSkillGet() {
        var response = skillService.skillGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SkillResponse> publicSkillIdGet(Long id) {
        var response = skillService.skillIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SkillSonResponse>> publicSkillSonGet() {
        var response = skillSonService.skillSonGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SkillSonResponse> publicSkillSonIdGet(Long id) {
        var response = skillSonService.skillSonIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SkillTypeResponse>> publicSkillTypeGet() {
        var response = skillTypeService.skillTypeGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SkillTypeResponse> publicSkillTypeIdGet(Long id) {
        var response = skillTypeService.skillTypeIdGet(id);
        return ResponseEntity.ok(response);
    }
}
