package com.sistemaclinica.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaclinica.dto.AgendaPorMedicoDTO;
import com.sistemaclinica.dto.AgendamentoDTO;
import com.sistemaclinica.dto.AgendamentoPorDataDTO;
import com.sistemaclinica.entity.Agendamento;
import com.sistemaclinica.repo.AgendamentoRepo;
import com.sistemaclinica.repo.AgendamentoRepo.HorarioDisponivelProjection;

@Service
@Transactional
public class AgendamentoService {
	
	 @Autowired
	 private AgendamentoRepo agendamentoRepo;
	 
	 
	 public List<HorarioDisponivelProjection> findHorariosDisponiveisPorMedicoEData(Long medicoId,LocalDate data){
		 return agendamentoRepo.findHorariosDisponiveisPorMedicoEData(medicoId, data);
	 }
	 
	 public List<AgendaPorMedicoDTO> getHorariosPorMedico(Long medicoId, LocalDate data) {
	        // Busca no repositório usando a query
	        List<HorarioDisponivelProjection> resultados = agendamentoRepo.findHorariosDisponiveisPorMedicoEData(medicoId, data);
	        
	        // Converte para DTO do frontend
	        return resultados.stream()
	            .map(proj -> {
	            	AgendaPorMedicoDTO dto = new AgendaPorMedicoDTO();
	                dto.setLabel(proj.getLabel());
	                dto.setValue(proj.getValue());
	                // IMPORTANTE: no frontend, empty=true significa INDISPONÍVEL
	                // Se getOcupado() retorna true, significa que está OCUPADO, então empty=true
	                dto.setOcupado(proj.getOcupado());
	                return dto;
	            })
	            .collect(Collectors.toList());
	    }
	    
	    
	    //Buscar agendamentos de hoje
	    public List<Agendamento> buscarAgendamentosDeHoje() {
	        // Usando o default method
	        return agendamentoRepo.findAgendamentosDeHoje();
	    }
	    
	    
	    //Buscar por médico hoje
	    public List<Agendamento> buscarAgendamentosDeHojePorMedico(Long medicoId) {
	        return agendamentoRepo.findAgendamentosDeHojePorMedico(medicoId);
	    }
	    
	    //Buscar por data especifica
	    public List<AgendamentoPorDataDTO> buscarPorData(LocalDate data) {
	    	
	    	List<Agendamento> agendaData = agendamentoRepo.findByData(data);
	    	
	    	return agendaData.stream().map(AgendamentoPorDataDTO::new).collect(Collectors.toList());

	    }
	    
	    // ✅ Buscar por status hoje
	    public List<Agendamento> buscarAgendamentosDeHojePorStatus(String status) {
	        return agendamentoRepo.findAgendamentosDeHojePorStatus(status);
	    }
	    
	    // ✅ Versão nativa (garantido funcionar)
	    public List<Agendamento> buscarAgendamentosDeHojeNativo() {
	        return agendamentoRepo.findAgendamentosDeHojeNative();
	    }
	    
	    
	    //Buscar por intervalo específico
	    public List<AgendamentoPorDataDTO> buscarPorMedicoEPeriodo(
	            Long medicoId, 
	            LocalDate inicio, 
	            LocalDate fim) {
	        LocalDateTime inicioDateTime = inicio.atStartOfDay();
	        LocalDateTime fimDateTime = fim.plusDays(1).atStartOfDay();
	        List<Agendamento> agendamentos = agendamentoRepo.findByMedicoIdAndDataBetween(medicoId, inicioDateTime, fimDateTime);
	        return agendamentos.stream().map(AgendamentoPorDataDTO::new).collect(Collectors.toList());
	    }

		/*
	    
	    // ✅ Método alternativo usando BETWEEN do Spring Data
	    public List<Agendamento> buscarAgendamentosDeHojePorMedicoV2(Long medicoId) {
	        LocalDate hoje = LocalDate.now();
	        LocalDateTime inicio = hoje.atStartOfDay();
	        LocalDateTime fim = hoje.atTime(LocalTime.MAX);
	        
	        return agendamentoRepo.findByMedicoIdAndDataBetween(medicoId, inicio, fim);
	    } */

}
