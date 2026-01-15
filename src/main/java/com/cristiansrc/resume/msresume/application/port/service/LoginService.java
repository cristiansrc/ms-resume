package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.application.exception.InvalidCredentialsException;
import com.cristiansrc.resume.msresume.application.port.interactor.IAltchaService;
import com.cristiansrc.resume.msresume.application.port.interactor.ILoginService;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LoginPost200Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LoginPostRequest;
import com.cristiansrc.resume.msresume.infrastructure.security.JwtUtil;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService implements ILoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Value("${auth.user}")
    private String user;

    @Value("${auth.pass}")
    private String pass;

    private final MessageResolver messageResolver;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final IAltchaService altchaService;

    @Override
    public LoginPost200Response loginPost(final LoginPostRequest loginPostRequest) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Login attempt for user: {}", loginPostRequest.getUser());
        }
        
        altchaService.validateChallenge(loginPostRequest.getAltcha());

        if (loginPostRequest.getUser().equals(user) && passwordEncoder.matches(loginPostRequest.getPassword(), pass)) {
            final String token = jwtUtil.generateToken(loginPostRequest.getUser());
            final LoginPost200Response response = new LoginPost200Response();
            response.setToken(token);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Login successful for user: {}", loginPostRequest.getUser());
            }
            return response;
        }
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Invalid login attempt for user: {}", loginPostRequest.getUser());
        }
        throw new InvalidCredentialsException(messageResolver.getMessage("login.user.pass.not.found"));
    }
}
