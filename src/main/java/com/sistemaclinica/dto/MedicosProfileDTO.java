package com.sistemaclinica.dto;

import java.util.List;

import com.sistemaclinica.repo.AgendamentoRepo.HorarioDisponivelProjection;

import lombok.Data;

@Data
public class MedicosProfileDTO {
    private Integer id;
    private String nome;
    private String email;
    private String avatar;
    private String telefone;
    private String especialidade;
    private String conselho;
    private List<HorarioDisponivelProjection> agenda;
}
