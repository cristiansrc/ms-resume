package com.cristiansrc.resume.msresume.infrastructure.controller;

import com.cristiansrc.resume.msresume.application.port.input.controller.LoginApi;
import com.cristiansrc.resume.msresume.application.port.interactor.ILoginService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LoginPost200Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LoginPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController implements LoginApi {

    private final ILoginService loginService;

    @Override
    public ResponseEntity<LoginPost200Response> loginPost(LoginPostRequest loginPostRequest) {
        var response = loginService.loginPost(loginPostRequest);
        return ResponseEntity.ok(response);
    }
}
