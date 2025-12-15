package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SkillSonEntityTest {

    @Test
    void testEntity() {
        SkillSonEntity entity = new SkillSonEntity();
        entity.setId(1L);
        entity.setName("SkillSon");

        assertEquals(1L, entity.getId());
        assertEquals("SkillSon", entity.getName());
        assertNotNull(entity.toString());
    }
}
