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

    @Column(name = "button_work_label", nullable = false)
    @NotNull
    private String buttonWorkLabel;

    @Column(name = "button_contact_label", nullable = false)
    @NotNull
    private String buttonContactLabel;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    private ImageUrlEntity imageUrl;

    @ManyToMany
    @JoinTable(
            name="home_label_relational",
            joinColumns=@JoinColumn(name="home_id"),
            inverseJoinColumns=@JoinColumn(name="label_id")
    )
    private List<LabelEntity> labels;

}
