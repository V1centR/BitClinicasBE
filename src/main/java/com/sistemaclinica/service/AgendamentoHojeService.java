package com.sistemaclinica.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaclinica.entity.Agendamento;
import com.sistemaclinica.repo.AgendamentoRepo;

@Service
@Transactional
public class AgendamentoHojeService {
    
	@Autowired
    private AgendamentoRepo agendamentoRepo;
    
    // ✅ Buscar agendamentos de hoje
    public List<Agendamento> buscarAgendamentosDeHoje() {
        // Usando o default method
        return agendamentoRepo.findAgendamentosDeHoje();
    }
    
    /*
    // ✅ Buscar por médico hoje
    public List<Agendamento> buscarAgendamentosDeHojePorMedico(Long medicoId) {
        return agendamentoRepo.findAgendamentosDeHojePorMedico(medicoId);
    }*/
    
    // ✅ Buscar por data específica
    public List<Agendamento> buscarPorData(LocalDate data) {
        return agendamentoRepo.findByData(data);
    }
    
    // ✅ Buscar por status hoje
    public List<Agendamento> buscarAgendamentosDeHojePorStatus(String status) {
        return agendamentoRepo.findAgendamentosDeHojePorStatus(status);
    }
    
    // ✅ Versão nativa (garantido funcionar)
    public List<Agendamento> buscarAgendamentosDeHojeNativo() {
        return agendamentoRepo.findAgendamentosDeHojeNative();
    }
    
    /*
    // ✅ Buscar por intervalo específico
    public List<Agendamento> buscarPorMedicoEPeriodo(
            Long medicoId, 
            LocalDateTime inicio, 
            LocalDateTime fim) {
        return agendamentoRepo.findByMedicoIdAndDataBetween(medicoId, inicio, fim);
    }
    
    // ✅ Método alternativo usando BETWEEN do Spring Data
    public List<Agendamento> buscarAgendamentosDeHojePorMedicoV2(Long medicoId) {
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicio = hoje.atStartOfDay();
        LocalDateTime fim = hoje.atTime(LocalTime.MAX);
        
        return agendamentoRepo.findByMedicoIdAndDataBetween(medicoId, inicio, fim);
    } */

}
