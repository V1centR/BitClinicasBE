package com.sistemaclinica.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaclinica.dto.AgendaPorMedicoDTO;
import com.sistemaclinica.dto.MedicoResponseDTO;
import com.sistemaclinica.dto.MedicoSelectsDTO;
import com.sistemaclinica.dto.MedicosProfileDTO;
import com.sistemaclinica.entity.Clinica;
import com.sistemaclinica.entity.Funcaoclinica;
import com.sistemaclinica.entity.Medico;
import com.sistemaclinica.exception.ErrorRegisterUserException;
import com.sistemaclinica.repo.AgendamentoRepo;
import com.sistemaclinica.repo.AgendamentoRepo.HorarioDisponivelProjection;
import com.sistemaclinica.repo.ClinicasRepo;
import com.sistemaclinica.repo.MedicoRepo;
import com.sistemaclinica.repo.PermissoesRepo;
import com.sistemaclinica.repo.UserAtendenteRepo;
import com.sistemaclinica.request.NovoUsuarioRequest;

@Service
@Transactional
public class MedicosService {
	
	
	@Autowired
	private MedicoRepo medicosRepo;
	
	@Autowired
	ClinicasRepo clinicaRepo;
	
	@Autowired
	private UserAtendenteRepo userRepo;
	
	@Autowired
	private PermissoesRepo permissoesRepo;

	@Autowired
	private AgendamentoRepo agendamentoRepo;
    
    
    // Opção 2 - Busca direta (mais performático)
    public List<MedicoSelectsDTO> getMedicosByClinica(String accessKey) {
    	
    	 List<Medico> resultados = medicosRepo.findByClinicaBean_AccessKey(accessKey);
	        
	        // Converte para DTO do frontend
	        return resultados.stream().map(proj -> {
	            	
	        		MedicoSelectsDTO dto = new MedicoSelectsDTO();
	                dto.setNome(proj.getNome());
	                dto.setValue(proj.getId());
	                // IMPORTANTE: no frontend, empty=true significa INDISPONÍVEL
	                // Se getOcupado() retorna true, significa que está OCUPADO, então empty=true
	                //dto.setOcupado(proj.getOcupado());
	                return dto;
	            })
	            .collect(Collectors.toList());
    	
    }

	// Opção 2 - Busca direta (mais performático)
    public List<MedicosProfileDTO> getMedicosProfileByClinica(String accessKey, LocalDate date) {

		// Busca no repositório usando a query	        
    	 List<Medico> resultados = medicosRepo.findByClinicaBean_AccessKey(accessKey);
	        
	        // Converte para DTO do frontend
	        return resultados.stream().map(proj -> {
	            	
	        	MedicosProfileDTO dto = new MedicosProfileDTO();
	            dto.setId(proj.getId());
	            dto.setNome(proj.getNome());
	            dto.setEmail(proj.getEmail());
	            dto.setAvatar(proj.getAvatar());
	            dto.setTelefone(proj.getTelefone());
	            dto.setEspecialidade(proj.getEspecialidade());
	            dto.setConselho(proj.getConselho());
				dto.setAgenda(this.agendamentoRepo.findHorariosDisponiveisPorMedicoEData(Long.valueOf(proj.getId()), date));
	            return dto;
	            })
	            .collect(Collectors.toList());
    	
    }
    
 // SAVE OR EDIT USER
 	public MedicoResponseDTO saveMedico(NovoUsuarioRequest request, String clinicaKey) {
 	    
 	    try {
 	        // 1. Busca permissão e clínica (comum para ambos)
 	        Funcaoclinica permissao = permissoesRepo.findById(request.getFuncaoId())
 	            .orElseThrow(() -> new RuntimeException("Permissão não encontrada para ID: " + request.getFuncao()));
 	        
 	        Clinica clinicaRegister = clinicaRepo.findByAccessKey(clinicaKey)
 	            .orElseThrow(() -> new RuntimeException("Clínica não encontrada para chave: " + clinicaKey));
 	        
 	        Medico userData;
 	        boolean isEdicao = request.getId() != null && request.getId() > 0;
 	        
 	        // 2. Se for EDIÇÃO: busca usuário existente
 	        if (isEdicao) {
 	        	
 	            userData = medicosRepo.findById(request.getId())
 	                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + request.getId()));
 	            
 	            // Atualiza campos da edição
 	            userData.setNome(request.getNome());
 	           // userData.setEmail(request.getEmail());  // Só se permitir alterar email
 	            userData.setTelefone(request.getTelefone());
 	            userData.setFuncaoclinica(permissao);
 	            userData.setClinicaBean(clinicaRegister);
 	            userData.setObservacoes(request.getObservacoes());
 	            userData.setEspecialidade(request.getEspecialidade());
 	            userData.setConselho(request.getConselho());
 	            
 	            // Campos opcionais na edição
 	            if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
 	                userData.setAvatar(request.getAvatar());
 	            }
 	            
 	            if (request.getStatus() != null) {
 	                userData.setStatus(request.getStatus());
 	            }
 	            
 	        } else {
 	        	
 	            //Check if user exists
 	            if (medicosRepo.existsByEmailAndClinicaBean_AccessKeyAndDeletedIsNull(request.getEmail(), clinicaRegister.getAccessKey())) {
 	                throw new ErrorRegisterUserException(request.getEmail());
 	            }
 	            
	            if (userRepo.existsByEmailAndClinicaAccessKeyAndDeletedIsNull(request.getEmail(), clinicaRegister.getAccessKey())) {
	                throw new ErrorRegisterUserException(request.getEmail());
	            }
 	            
 	            userData = new Medico();
 	            String senhaTemporaria = gerarSenhaAleatoria();
 	            
 	            
 	            userData.setNome(request.getNome());
 	            userData.setEmail(request.getEmail());
 	            userData.setTelefone(request.getTelefone());
 	            userData.setFuncaoclinica(permissao);
 	            userData.setClinicaBean(clinicaRegister);
 	            userData.setObservacoes(request.getObservacoes());
 	            userData.setConselho(request.getConselho());
 	            userData.setEspecialidade(request.getEspecialidade());
 	            
 	           
 	            
 	            // Campos específicos de criação
 	            userData.setPwdu59k7auvwyu(senhaTemporaria);
 	            userData.setStatus(1);  // Ativo por padrão
 	            userData.setFirstlogin(1);
 	            
 	            String avatarPath = selecionarAvatar(request.getSexo());
 	            userData.setAvatar(avatarPath);
 	            
 	            
 	            /*
 	            userData.setAvatar(request.getAvatar() != null ? 
 	                request.getAvatar() : "./assets/avatars/genericRandomAvatar.png");*/
 	        }
 	        
 	        //Save on database
 	        Medico savedUser = medicosRepo.save(userData);
 	        
 	        return new MedicoResponseDTO(savedUser);

 	    } catch (ErrorRegisterUserException e) {
 	       // log.error("❌ Email duplicado: {}", e.getMessage());
 	        throw e;  // Relança para o GlobalExceptionHandler
 	        
 	    } catch (RuntimeException e) {
 	      //  log.error("❌ Erro ao salvar usuário: {}", e.getMessage());
 	        throw new RuntimeException("Falha ao cadastrar usuário: " + e.getMessage());
 	    }
 	}
 	
 	private String selecionarAvatar(Integer sexo) {
 		
 	    String sufixo = (sexo != null && sexo == 1) ? "M" : "F";
 	    return String.format("%s_professional.png", sufixo);
 	    // Resultado: "M_professional.png" ou "F_professional.png"
 	}
 	
 	public String gerarSenhaAleatoria() {
 	    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
 	}

}
