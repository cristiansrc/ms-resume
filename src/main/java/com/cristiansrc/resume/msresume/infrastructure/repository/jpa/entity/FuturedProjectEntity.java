package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "futured_project")
public class FuturedProjectEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(name = "description_short", nullable = false)
    @NotNull
    private String descriptionShort;

    @Column(nullable = false)
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "experience_id", nullable = false)
    private ExperienceEntity experience;

    @ManyToOne
    @JoinColumn(name = "image_list_url_id", nullable = false)
    private ImageUrlEntity imageListUrl;

    @ManyToOne
    @JoinColumn(name = "image_url_id", nullable = false)
    private ImageUrlEntity imageUrl;
}
