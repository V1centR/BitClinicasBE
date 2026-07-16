package com.sistemaclinica.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaclinica.entity.Tipoconsulta;

@Repository
public interface TipoConsultaRepo extends JpaRepository<Tipoconsulta, Integer> {

}
