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
@Table(name = "home")
public class HomeEntity extends AuditBasicEntity {

    @Column(nullable = false)
    @NotNull
    private String greeting;

    @Column(name = "greeting_eng", nullable = false)
    @NotNull
    private String greetingEng;

    @Column(name = "button_work_label", nullable = false)
    @NotNull
    private String buttonWorkLabel;

    @Column(name = "button_work_label_eng", nullable = false)
    @NotNull
    private String buttonWorkLabelEng;

    @Column(name = "button_contact_label", nullable = false)
    @NotNull
    private String buttonContactLabel;

    @Column(name = "button_contact_label_eng", nullable = false)
    @NotNull
    private String buttonContactLabelEng;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = true)
    private ImageUrlEntity imageUrl;

    @OneToMany(mappedBy = "home", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HomeLabelRelationalEntity> homeLabelRelations;
}
