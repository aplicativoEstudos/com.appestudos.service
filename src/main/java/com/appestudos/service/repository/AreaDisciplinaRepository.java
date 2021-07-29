package com.appestudos.service.repository;

import com.appestudos.service.domain.AreaDisciplina;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AreaDisciplina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AreaDisciplinaRepository extends JpaRepository<AreaDisciplina, Long>, JpaSpecificationExecutor<AreaDisciplina> {
}
