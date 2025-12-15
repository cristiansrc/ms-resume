package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "blog")
public class BlogEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String title;

    @Column(name = "clean_url_title", nullable = false)
    @NotNull
    private String cleanUrlTitle;

    @Column(name = "description_short", nullable = false)
    @NotNull
    private String descriptionShort;

    @Column(nullable = false)
    @NotNull
    private String description;

    @Column(name = "description_short_eng", nullable = false)
    @NotNull
    private String descriptionShortEng;

    @Column(name = "description_eng", nullable = false)
    @NotNull
    private String descriptionEng;
}
