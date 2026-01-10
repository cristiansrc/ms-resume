package com.cristiansrc.resume.msresume.application.port.output.repository.jpa;

import com.cristiansrc.resume.msresume.infrastructure.repository.jpa.entity.BasicDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBasicDataRepository extends JpaRepository<BasicDataEntity, Long> {
    Optional<BasicDataEntity> findFirstByOrderByCreatedDesc();
}
