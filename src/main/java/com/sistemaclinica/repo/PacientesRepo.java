package com.sistemaclinica.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaclinica.entity.Paciente;

@Repository
public interface PacientesRepo extends JpaRepository<Paciente, Integer> {

	Paciente findByCpf(String cpf);

    Optional<Paciente> findByEmail(String string);

}
