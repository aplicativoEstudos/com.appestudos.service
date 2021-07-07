package com.appestudos.service.repository;

import com.appestudos.service.domain.RegistroDeEstudo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RegistroDeEstudo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistroDeEstudoRepository extends JpaRepository<RegistroDeEstudo, Long>, JpaSpecificationExecutor<RegistroDeEstudo> {
}
