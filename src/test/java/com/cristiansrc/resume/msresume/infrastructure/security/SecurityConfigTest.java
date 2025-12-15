package com.cristiansrc.resume.msresume.infrastructure.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {SecurityConfig.class, SecurityConfigTest.TestConfig.class})
class SecurityConfigTest {

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncoder() {
        assertNotNull(passwordEncoder);
    }

    @Configuration
    static class TestConfig {
        @Bean(name = "mvcHandlerMappingIntrospector")
        public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
            return new HandlerMappingIntrospector();
        }
    }
}
