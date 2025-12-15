package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.port.interactor.ILoginService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LoginPost200Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LoginPostRequest;
import com.cristiansrc.resume.msresume.infrastructure.security.JwtUtil;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService implements ILoginService {

    @Value("${auth.user}")
    private String user;

    @Value("${auth.pass}")
    private String pass;

    private final MessageResolver messageResolver;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginPost200Response loginPost(LoginPostRequest loginPostRequest) {

        if (loginPostRequest.getUser().equals(user) && passwordEncoder.matches(loginPostRequest.getPassword(), pass)) {
            String token = jwtUtil.generateToken(loginPostRequest.getUser());
            LoginPost200Response response = new LoginPost200Response();
            response.setToken(token);
            return response;
        }

        throw messageResolver.notFound("login.user.pass.not.found");
    }
}
