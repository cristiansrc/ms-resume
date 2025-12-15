package com.cristiansrc.resume.msresume;

import com.cristiansrc.resume.msresume.application.port.interactor.ILoginService;
import com.cristiansrc.resume.msresume.application.port.interactor.IHomeService;
import com.cristiansrc.resume.msresume.infrastructure.security.JwtUtil;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestBeansConfig {

    @Bean
    public ILoginService loginService() {
        return Mockito.mock(ILoginService.class);
    }

    @Bean
    public IHomeService homeService() {
        return Mockito.mock(IHomeService.class);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return Mockito.mock(JwtUtil.class);
    }
}
