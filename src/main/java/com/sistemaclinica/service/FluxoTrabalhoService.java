package com.sistemaclinica.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemaclinica.dto.FluxoDeTrabalhoDTO;
import com.sistemaclinica.entity.Clinica;
import com.sistemaclinica.entity.Fluxotrabalho;
import com.sistemaclinica.entity.Medico;
import com.sistemaclinica.entity.Paciente;
import com.sistemaclinica.entity.Usersatendente;
import com.sistemaclinica.repo.FluxoDeTrabalhoRepo;

@Service
public class FluxoTrabalhoService {
	
	String messageLog = "";
	
	private LocalDateTime registerDateNow;
    
    @Autowired
    FluxoDeTrabalhoRepo repo;
    
    public List<FluxoDeTrabalhoDTO> getFluxoDeTrabalho(String accessKey, LocalDate data) {
        
        LocalDateTime startOfDay = data.atStartOfDay();
        LocalDateTime endOfDay = data.atTime(23, 59, 59, 999999999);
        
        List<Fluxotrabalho> resultados = repo.findByClinica_AccessKeyAndDataRegistroBetween(accessKey, startOfDay, endOfDay);
        
        if (resultados.isEmpty()) {
            return new ArrayList<>(); // Retorna lista vazia em vez de erro
        }
        
        return resultados.stream()
			.map(FluxoDeTrabalhoDTO::new)
            .collect(Collectors.toList());
    }
    
    public void logFluxoDeTrabalho(Clinica clinica, Usersatendente atendente, String flag,Paciente pacienteFound, LocalDateTime registerDate, Medico medico) {
    	
    	//"pendente", "confirmado", "cancelado", "atendido"
    	
    	
    	registerDateNow =  LocalDateTime.now();
    	
    	Fluxotrabalho logFluxo = new Fluxotrabalho();
    	
    	logFluxo.setAcao("-");
    	logFluxo.setUsersatendente(atendente);
    	logFluxo.setClinica(clinica);
    	logFluxo.setFlag(flag);
    	logFluxo.setDataRegistro(registerDateNow);
    	logFluxo.setNomemedico(medico.getNome());
    	logFluxo.setNomepaciente(pacienteFound.getNomecompleto());
    	
    	
    	repo.save(logFluxo);
    	
    	
    }


}
