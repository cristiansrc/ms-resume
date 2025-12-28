package com.cristiansrc.resume.msresume.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;


import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationEntryPointTest {

    @Test
    void commence_setsUnauthorizedAndJsonBody() throws Exception {
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/some/path");
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthenticationException ex = new AuthenticationException("bad credentials") {};

        entryPoint.commence(request, response, ex);

        assertEquals(401, response.getStatus());
        assertEquals("application/json", response.getContentType());
        String content = response.getContentAsString();
        assertTrue(content.contains("Unauthorized"));
        assertTrue(content.contains("bad credentials"));
        assertTrue(content.contains("/some/path"));
    }
}

