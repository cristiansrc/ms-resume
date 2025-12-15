package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SkillSonRelationalEntityTest {

    @Test
    void testEntity() {
        SkillSonRelationalEntity entity = new SkillSonRelationalEntity();
        entity.setId(1L);

        assertEquals(1L, entity.getId());
        assertNotNull(entity.toString());
    }
}
