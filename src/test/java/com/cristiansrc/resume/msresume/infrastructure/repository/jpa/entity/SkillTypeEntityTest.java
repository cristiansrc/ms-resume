package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SkillTypeEntityTest {

    @Test
    void testEntity() {
        SkillTypeEntity entity = new SkillTypeEntity();
        entity.setId(1L);
        entity.setName("SkillType");

        assertEquals(1L, entity.getId());
        assertEquals("SkillType", entity.getName());
        assertNotNull(entity.toString());
    }
}
