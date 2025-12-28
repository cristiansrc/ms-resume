package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HomeLabelRelationalEntityTest {

    @Test
    void testInstantiation() {
        HomeLabelRelationalEntity entity = new HomeLabelRelationalEntity();
        assertNotNull(entity);
    }
}
