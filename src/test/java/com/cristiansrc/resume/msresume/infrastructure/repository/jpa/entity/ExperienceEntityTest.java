package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExperienceEntityTest {

    @Test
    void testInstantiation() {
        ExperienceEntity entity = new ExperienceEntity();
        assertNotNull(entity);
    }
}
