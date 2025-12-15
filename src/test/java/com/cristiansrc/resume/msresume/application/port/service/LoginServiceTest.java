package com.cristiansrc.resume.msresume.application.port.service;

import com.cristiansrc.resume.msresume.infrastructure.controller.model.LoginPost200Response;
import com.cristiansrc.resume.msresume.infrastructure.controller.model.LoginPostRequest;
import com.cristiansrc.resume.msresume.infrastructure.security.JwtUtil;
import com.cristiansrc.resume.msresume.infrastructure.util.MessageResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoginServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private MessageResolver messageResolver;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        org.springframework.test.util.ReflectionTestUtils.setField(loginService, "user", "testuser");
        org.springframework.test.util.ReflectionTestUtils.setField(loginService, "pass", "password");
    }

    @Test
    void loginPost_success() {
        LoginPostRequest request = new LoginPostRequest();
        request.setUser("testuser");
        request.setPassword("password");

        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtUtil.generateToken(any())).thenReturn("test-token");

        LoginPost200Response response = loginService.loginPost(request);

        assertNotNull(response);
        assertEquals("test-token", response.getToken());
    }

    @Test
    void loginPost_failure() {
        LoginPostRequest request = new LoginPostRequest();
        request.setUser("testuser");
        request.setPassword("wrong-password");

        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        when(messageResolver.notFound(any())).thenThrow(new RuntimeException("login.user.pass.not.found"));

        assertThrows(RuntimeException.class, () -> loginService.loginPost(request));
    }
}
