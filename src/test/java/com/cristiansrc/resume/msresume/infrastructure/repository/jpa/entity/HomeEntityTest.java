package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HomeEntityTest {

    @Test
    void testEntity() {
        HomeEntity entity = new HomeEntity();
        entity.setId(1L);
        entity.setGreeting("Hello");

        assertEquals(1L, entity.getId());
        assertEquals("Hello", entity.getGreeting());
        assertNotNull(entity.toString());
    }
}
