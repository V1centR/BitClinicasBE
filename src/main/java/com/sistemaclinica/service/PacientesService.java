package com.sistemaclinica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaclinica.entity.Paciente;
import com.sistemaclinica.repo.PacientesRepo;
import com.sistemaclinica.request.PacienteCreateUpdateRequest;

@Service
@Transactional
public class PacientesService {
	
	@Autowired
	private PacientesRepo repo;
	
	public Paciente salvarPacientes(PacienteCreateUpdateRequest request) {
		
		Paciente newPaciente = new Paciente();
		
		newPaciente.setNomecompleto(request.getNomecompleto());
		newPaciente.setCpf(request.getCpf().replace(".", "").replace("-", ""));
		newPaciente.setCep(request.getCep());
		newPaciente.setTelefone(request.getTelefone());
		newPaciente.setEndereco(request.getEndereco());
		newPaciente.setCidade(request.getCidade());		
		newPaciente.setDatanasc(request.getDataNasc());
		newPaciente.setDoencaspreexistentes(request.getDoencaspreexistentes());
		newPaciente.setEmail(request.getEmail());
		newPaciente.setEstado(request.getEstado());
		newPaciente.setMedicacoesdesc(request.getMedicacoesdesc());
		newPaciente.setRg(request.getRg());
		newPaciente.setSexo(request.getSexo());
		newPaciente.setContatoemergenciaNome(request.getContatoemergenciaNome());
		newPaciente.setContatoemergenciaParentesco(request.getContatoemergenciaParentesco());
		newPaciente.setContatoemergenciaTelefone(request.getContatoemergenciaTelefone());
		newPaciente.setTiposanguineo(request.getTiposanguineo());
		newPaciente.setObservacoes(request.getObservacoes());
		
		return repo.save(newPaciente);
	}
	
	public Paciente searchPaciente(String cpf) {
		
		return repo.findByCpf(cpf);
		
	}

}
