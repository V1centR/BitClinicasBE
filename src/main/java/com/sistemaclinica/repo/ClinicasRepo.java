package com.sistemaclinica.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaclinica.entity.Clinica;

@Repository
public interface ClinicasRepo extends JpaRepository<Clinica, Long> {
	
	
	Optional<Clinica> findByAccessKey(String accessKey);

	
}
