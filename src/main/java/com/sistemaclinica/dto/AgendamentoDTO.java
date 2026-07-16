package com.sistemaclinica.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgendamentoDTO {
    
    private Long id;
    private String nomePaciente;
    private String sexo;
    private String telefone;
    private String medicoNome;
    private String convenioNome;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAgendamento;
    
    @JsonFormat(pattern = "HH:mm")
    private String horaFormatada;
    
    private String status;
    private String tipoConsulta;
    private String observacoes;
    
    // Construtor
    public AgendamentoDTO(Long id, String nomePaciente, String sexo, String telefone, 
                         String medicoNome, String convenioNome, LocalDateTime dataAgendamento, 
                         String status, String tipoConsulta, String observacoes) {
        this.id = id;
        this.nomePaciente = nomePaciente;
        this.sexo = sexo;
        this.telefone = telefone;
        this.medicoNome = medicoNome;
        this.convenioNome = convenioNome;
        this.dataAgendamento = dataAgendamento;
        this.horaFormatada = dataAgendamento.toLocalTime().toString();
        this.status = status;
        this.tipoConsulta = tipoConsulta;
        this.observacoes = observacoes;
    }
}
