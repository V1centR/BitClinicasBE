package com.sistemaclinica.dto;

import com.sistemaclinica.entity.Medico;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class MedicoResponseDTO extends UsuarioBaseDTO {
	
	private String conselho;
    
    public MedicoResponseDTO(Medico user) {
        this.setId(user.getId());
        this.setNome(user.getNome());
        this.setEmail(user.getEmail());
        this.setTelefone(user.getTelefone());
        this.setFirstlogin(user.getFirstlogin());
        this.setAvatar(user.getAvatar());
        this.setStatus(user.getStatus());
        this.setClinicaNome(user.getClinicaBean() != null ? user.getClinicaBean().getNomeclinica() : null);
        this.setFuncaoNome(user.getFuncaoclinica() != null ? user.getFuncaoclinica().getNomefuncao() : null);
        this.setFuncaoID(user.getFuncaoclinica() != null ? user.getFuncaoclinica().getId() : 0);
        this.setObservacoes(user.getObservacoes());
        this.setConselho(user.getConselho());
        this.setTipo("MEDICO");
    }

}
