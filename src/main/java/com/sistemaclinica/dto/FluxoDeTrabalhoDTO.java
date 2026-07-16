package com.sistemaclinica.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.sistemaclinica.entity.Fluxotrabalho;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FluxoDeTrabalhoDTO {
    
    private String user;
    private String acao;
    private String flag;
    private String medico;
    private LocalDateTime dataregistro;
    private String nomemedico;
    private String nomepaciente;
    
    // Construtor que mapeia da entidade para o DTO
    public FluxoDeTrabalhoDTO(Fluxotrabalho data) {
        this.user = data.getUsersatendente() != null ? data.getUsersatendente().getNome() : null;
        this.acao = data.getAcao();
        this.flag = data.getFlag();
        this.medico = data.getMedicoBean() != null ? data.getMedicoBean().getNome() : null;
        this.dataregistro = data.getDataRegistro();
        this.nomemedico = data.getNomemedico();
        this.nomepaciente = data.getNomepaciente();
        
        // Formatar LocalDateTime para String
        /*
        if (data.getDataRegistro() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            this.dataregistro = data.getDataRegistro().format(formatter);
        } */
    }
}
