package com.sistemaclinica.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistemaclinica.entity.Medico;
import com.sistemaclinica.entity.Usersatendente;

@Repository
public interface MedicoRepo extends JpaRepository<Medico, Integer> {
	 
	List<Medico> findByClinicaBean_AccessKey(String accessKey);

	boolean existsByEmailAndClinicaBean_AccessKeyAndDeletedIsNull(String email, String accessKey);
	
	List<Medico> findByClinicaBean_AccessKeyAndDeletedIsNull(String accessKey);

    Medico findByEmailAndClinicaBean_AccessKey(String emailMedico, String clinicaKey);
	
	Optional<Medico> findByEmailAndDeletedIsNullAndStatus(String email, Integer status);
	
}
