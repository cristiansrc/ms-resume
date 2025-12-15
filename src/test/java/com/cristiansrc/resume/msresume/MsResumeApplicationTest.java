package com.cristiansrc.resume.msresume;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MsResumeApplicationTest {

    @Test
    void contextLoads() {
        // El contexto de la aplicación se carga automáticamente con @SpringBootTest
    }
}
