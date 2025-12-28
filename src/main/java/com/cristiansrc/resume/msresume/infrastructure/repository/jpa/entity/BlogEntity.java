package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "title_eng", nullable = false)
    @NotNull
    private String titleEng;

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

    @ManyToOne
    @JoinColumn(name = "blog_type_id")
    private BlogTypeEntity blogType;

    @ManyToOne
    @JoinColumn(name = "image_url_id")
    private ImageUrlEntity imageUrl;

    @ManyToOne
    @JoinColumn(name = "video_url_id", nullable = true)
    private VideoUrlEntity videoUrl;
}
