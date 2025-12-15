package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExperienceEntityTest {

    @Test
    void testEntity() {
        ExperienceEntity entity = new ExperienceEntity();
        entity.setId(1L);
        entity.setCompany("Company");

        assertEquals(1L, entity.getId());
        assertEquals("Company", entity.getCompany());
        assertNotNull(entity.toString());
    }
}
