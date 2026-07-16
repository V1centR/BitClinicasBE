package com.sistemaclinica.dto;

import com.sistemaclinica.entity.Usersatendente;

import lombok.Data;

@Data
public class UsersatendenteResponseDTO extends UsuarioBaseDTO {
	
	public UsersatendenteResponseDTO(Usersatendente user) {
        this.setId(user.getId());
        this.setNome(user.getNome());
        this.setEmail(user.getEmail());
        this.setTelefone(user.getTelefone());
        this.setFirstlogin(user.getFirstlogin());
        this.setAvatar(user.getAvatar());
        this.setStatus(user.getStatus());
        this.setClinicaNome(user.getClinica() != null ? user.getClinica().getNomeclinica() : null);
        this.setClinicaKey(user.getClinica() != null ? user.getClinica().getAccessKey() : null);
        this.setFuncaoNome(user.getFuncaoclinica() != null ? user.getFuncaoclinica().getNomefuncao() : null);
        this.setFuncaoID(user.getFuncaoclinica() != null ? user.getFuncaoclinica().getId() : 0);
        this.setObservacoes(user.getObservacoes());
        this.setTipo("ATENDENTE");
    }

}
