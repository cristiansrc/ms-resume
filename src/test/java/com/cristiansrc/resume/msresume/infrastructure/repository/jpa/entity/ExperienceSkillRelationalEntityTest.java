package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExperienceSkillRelationalEntityTest {

    @Test
    void testInstantiation() {
        ExperienceSkillEntity entity = new ExperienceSkillEntity();
        assertNotNull(entity);
    }
}
