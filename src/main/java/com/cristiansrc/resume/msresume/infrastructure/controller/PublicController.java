package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.PublicApi;
import com.cristiansrc.resume.msresume.application.port.interactor.*;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicController implements PublicApi {

    private final IBlogService blogService;
    private final IBlogTypeService blogTypeService;
    private final IInfoPageService infoPageService;
    private final ICvService cvService;
    private final IContactService contactService;
    private final IAltchaService altchaService;

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
    public ResponseEntity<List<BlogTypeResponse>> publicBlogTypeGet() {
        var response = blogTypeService.blogTypeGet();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BlogTypeResponse> publicBlogTypeIdGet(Long id) {
        var response = blogTypeService.blogTypeIdGet(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AltchaChallengeResponse> publicChallengeGet() {
        var response = altchaService.createChallenge();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<InfoPageResponse> publicInfoPageGet() {
        var response = infoPageService.getInfoPage();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Resource> publicCurriculumLanguageGet(String language) {
        var response = cvService.generateCurriculum(language);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> publicContactPost(ContactRequest contactRequest) {
        contactService.sendContactMessage(contactRequest);
        return ResponseEntity.ok().build();
    }
}
