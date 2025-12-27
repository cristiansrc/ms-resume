package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class EntitiesTest {

    @Test
    void auditBasicEntity_onCreate_and_onUpdate() {
        AuditBasicEntity entity = new AuditBasicEntity() {};
        // aseguramos valores nulos antes de persist
        assertNull(entity.getCreated());
        assertNull(entity.getUpdated());
        assertNull(entity.getDeleted());

        entity.onCreate();
        assertNotNull(entity.getCreated());
        assertNotNull(entity.getUpdated());
        assertFalse(entity.getDeleted());

        Timestamp before = entity.getUpdated();
        
        // Usamos Awaitility para esperar a que el tiempo avance lo suficiente
        // para que el timestamp cambie, evitando Thread.sleep
        await().atLeast(10, TimeUnit.MILLISECONDS).until(() -> {
            entity.onUpdate();
            return entity.getUpdated().after(before);
        });
    }

    @Test
    void homeEntity_builder_and_setters() {
        ImageUrlEntity img = ImageUrlEntity.builder().name("n").nameEng("ne").nameFileAws("f").build();
        LabelEntity label = LabelEntity.builder().name("l").nameEng("le").build();

        HomeEntity home = HomeEntity.builder()
                .greeting("g")
                .buttonContactLabel("c")
                .buttonWorkLabel("w")
                .imageUrl(img)
                .build();

        assertEquals("g", home.getGreeting());
        assertEquals("c", home.getButtonContactLabel());
        assertEquals("w", home.getButtonWorkLabel());
        assertEquals(img, home.getImageUrl());

        home.setGreeting("g2");
        assertEquals("g2", home.getGreeting());

        HomeLabelRelationalEntity relation = new HomeLabelRelationalEntity();
        relation.setHome(home);
        relation.setLabel(label);
        home.setHomeLabelRelations(new ArrayList<>());
        home.getHomeLabelRelations().add(relation);
        
        assertNotNull(home.getHomeLabelRelations());
        assertEquals(1, home.getHomeLabelRelations().size());
        assertEquals(label, home.getHomeLabelRelations().getFirst().getLabel());
    }

    @Test
    void imageLabel_entities_builder() {
        ImageUrlEntity i = ImageUrlEntity.builder().name("n").nameEng("ne").nameFileAws("f").build();
        assertEquals("n", i.getName());

        LabelEntity l = LabelEntity.builder().name("ln").nameEng("le").build();
        assertEquals("ln", l.getName());
    }
}
