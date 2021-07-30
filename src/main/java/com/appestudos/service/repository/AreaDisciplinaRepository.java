package com.appestudos.service.repository;

import com.appestudos.service.domain.AreaDisciplina;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AreaDisciplina entity.
 */

@Repository
public interface AreaDisciplinaRepository extends JpaRepository<AreaDisciplina, Long>, JpaSpecificationExecutor<AreaDisciplina> {
	
	@Query(value = "select areadisc.* from area_disciplina areadisc where areadisc.area_id = ?1 and ( areadisc.geral = ?2 or areadisc.id_user = ?3 )",nativeQuery=true)
	List<AreaDisciplina> getAreaIdGeralIdUser(Long areaId, Boolean geral,String idUser);
}
