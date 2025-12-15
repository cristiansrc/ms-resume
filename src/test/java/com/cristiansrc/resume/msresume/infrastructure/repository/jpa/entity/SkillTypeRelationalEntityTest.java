package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SkillTypeRelationalEntityTest {

    @Test
    void testEntity() {
        SkillTypeRelationalEntity entity = new SkillTypeRelationalEntity();
        entity.setId(1L);

        assertEquals(1L, entity.getId());
        assertNotNull(entity.toString());
    }
}
