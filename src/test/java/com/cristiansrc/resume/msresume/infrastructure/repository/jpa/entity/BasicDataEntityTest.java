package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BasicDataEntityTest {

    @Test
    void testInstantiation() {
        BasicDataEntity entity = new BasicDataEntity();
        assertNotNull(entity);
    }
}
