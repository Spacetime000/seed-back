package com.SeedOasis.voca;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VocaRepository extends JpaRepository<VocaEntity, Long> {

    List<VocaEntity> findByVisibilityOrderByCreatedDateDesc(Boolean visibility);

    Optional<VocaEntity> findByIdAndVisibility(Long id, Boolean visibility);

    Optional<VocaEntity> findByIdAndCreateBy(Long id, String createBy);

    List<VocaEntity> findByCreateByOrderByIdDesc(String createBy);



}
