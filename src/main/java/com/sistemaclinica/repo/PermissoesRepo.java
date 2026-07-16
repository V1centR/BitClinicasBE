package com.sistemaclinica.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaclinica.entity.Funcaoclinica;

@Repository
public interface PermissoesRepo extends JpaRepository<Funcaoclinica, Integer>{

}
