package com.sistemaclinica.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemaclinica.entity.Usersatendente;

@Repository
public interface UserAtendenteRepo extends JpaRepository<Usersatendente, Integer> {
    
	
	/*
    boolean existsByEmailAndClinicaAccessKey(String email, String clinicaId);
   
    
    List<Usersatendente> findByClinica_AccessKey(String accessKey);

 // Busca por EMAIL + clinicaKey (mais seguro)
    Optional<Usersatendente> findByEmailAndClinica_AccessKey(String email, String clinicaKey); */
	
	
	 //Verifica email APENAS entre usuários NÃO excluídos (deleted IS NULL)
    boolean existsByEmailAndClinicaAccessKeyAndDeletedIsNull(String email, String clinicaId);
    
    //Lista APENAS usuários NÃO excluídos (deleted IS NULL)
    List<Usersatendente> findByClinica_AccessKeyAndDeletedIsNull(String accessKey);
    
    //Busca por email APENAS entre usuários NÃO excluídos
    Optional<Usersatendente> findByEmailAndClinica_AccessKeyAndDeletedIsNull(String email, String clinicaKey);
    
    //(Opcional) Busca também incluindo excluídos (para auditoria/restauração)
    Optional<Usersatendente> findByEmailAndClinica_AccessKey(String email, String clinicaKey);
    
    // to login
    Optional<Usersatendente> findByEmailAndDeletedIsNullAndStatus(String email,Integer status);
	
}
