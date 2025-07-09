package com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "basic_data")
public class BasicDataEntity extends AuditBasicEntity {

    @Column(name = "first_name", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "others_name", nullable = false)
    @NotNull
    private String othersName;

    @Column(name = "first_surname", nullable = false)
    @NotNull
    private String firstSurname;

    @Column(name = "others_name", nullable = false)
    @NotNull
    private String othersSurname;

    @Column(name = "date_birth", nullable = false)
    @NotNull
    private LocalDateTime dateBirth;

    @Column(nullable = false)
    @NotNull
    private String located;

    @Column(name = "start_working_date", nullable = false)
    @NotNull
    private LocalDateTime startWorkingDate;

    @Column(nullable = false)
    @NotNull
    private String greeting;

    @Column(nullable = false)
    @NotNull
    private String email;

    @Column(nullable = false)
    @NotNull
    private String instagram;

    @Column(nullable = false)
    @NotNull
    private String linkedin;

    @Column(nullable = false)
    @NotNull
    private String x;

    @Column(nullable = false)
    @NotNull
    private String github;

    @Column(nullable = false)
    @NotNull
    private String description;

}
