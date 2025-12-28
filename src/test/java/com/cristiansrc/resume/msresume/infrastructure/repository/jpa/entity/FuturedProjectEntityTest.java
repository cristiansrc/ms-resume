package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FuturedProjectEntityTest {

    @Test
    void testInstantiation() {
        FuturedProjectEntity entity = new FuturedProjectEntity();
        assertNotNull(entity);
    }
}
