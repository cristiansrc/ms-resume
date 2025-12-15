package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SkillEntityTest {

    @Test
    void testEntity() {
        SkillEntity entity = new SkillEntity();
        entity.setId(1L);
        entity.setName("Skill");

        assertEquals(1L, entity.getId());
        assertEquals("Skill", entity.getName());
        assertNotNull(entity.toString());
    }
}
