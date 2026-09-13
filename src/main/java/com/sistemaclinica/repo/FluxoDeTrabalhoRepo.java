package com.sistemaclinica.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaclinica.entity.Fluxotrabalho;

@Repository
public interface FluxoDeTrabalhoRepo extends JpaRepository<Fluxotrabalho, Integer> {
	
	List<Fluxotrabalho> findTop17ByClinica_AccessKeyAndDataRegistroBetweenOrderByDataRegistro(String accessKey, LocalDateTime dataInicio, LocalDateTime dataFim);

	List<Fluxotrabalho> findByClinica_AccessKey(String accessKey);
}
