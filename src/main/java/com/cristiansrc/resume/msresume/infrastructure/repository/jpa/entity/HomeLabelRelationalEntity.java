package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "home_label_relational",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"home_id", "label_id"})})
public class HomeLabelRelationalEntity extends AuditBasicEntity {

    @ManyToOne
    @JoinColumn(name = "home_id", nullable = false)
    private HomeEntity home;

    @ManyToOne
    @JoinColumn(name = "label_id", nullable = false)
    private LabelEntity label;
}
