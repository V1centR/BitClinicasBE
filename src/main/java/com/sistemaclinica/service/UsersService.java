package com.sistemaclinica.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemaclinica.dto.MedicoResponseDTO;
import com.sistemaclinica.dto.UsersatendenteResponseDTO;
import com.sistemaclinica.dto.UsuarioBaseDTO;
import com.sistemaclinica.entity.Clinica;
import com.sistemaclinica.entity.Funcaoclinica;
import com.sistemaclinica.entity.Medico;
import com.sistemaclinica.entity.Usersatendente;
import com.sistemaclinica.exception.ErrorRegisterUserException;
import com.sistemaclinica.repo.ClinicasRepo;
import com.sistemaclinica.repo.MedicoRepo;
import com.sistemaclinica.repo.PermissoesRepo;
import com.sistemaclinica.repo.UserAtendenteRepo;
import com.sistemaclinica.request.LoginRequest;
import com.sistemaclinica.request.NovoUsuarioRequest;

@Service
public class UsersService {
	
	private static final int ADMIN_SISTEMA = 1;
	private static final int USUARIO_PERMISSOES_ESPECIAIS = 3;
	private static final int TOTAL_AVATARES = 10;
	private static final int MIN_AVATAR = 1;
	private static final int MAX_AVATAR_ADMIN = 6; // Admins têm 6 opções
	private static final int MASCULINO = 1;
	
	private static final Logger log = LoggerFactory.getLogger(UsersService.class);
	
	@Autowired
	private PermissoesRepo permissoesRepo;
	
	@Autowired
	private ClinicasRepo clinicaRepo;
	
	@Autowired
	private UserAtendenteRepo userRepo;
	
	@Autowired
	private MedicoRepo medicosRepo;
	
	public UsersatendenteResponseDTO getUserLogin(LoginRequest userData) {
	    
	    //STATUS 1 = active
	    Usersatendente usuario = userRepo.findByEmailAndDeletedIsNullAndStatus(userData.getEmail(),1)
	        .orElseThrow(() -> new RuntimeException("no credentials")); // Mensagem genérica
	    
	    
	    System.out.println("USER FOUND::: " + usuario.getNome());
	    
	    if (!userData.getEncryptedpass().equals(usuario.getPassword())) {
	        throw new RuntimeException("no credentials");
	    }
	    
	    // 4. Log de auditoria (opcional, mas recomendado)
	    log.info("Login bem-sucedido para usuário: {}", usuario.getEmail());
	    
	    // 5. Atualizar último login
	   // usuario.setUltimoLogin(LocalDateTime.now());
	   //userRepo.save(usuario);
	    
	    // 6. Retornar DTO seguro (sem dados sensíveis)
	    return new UsersatendenteResponseDTO(usuario);
	}
	
	public boolean deleteUser(String clinicaKey, String mailUser) {
		
		
		try {
	        // Busca o usuário pelo EMAIL e clínica
	        Usersatendente usuario = userRepo.findByEmailAndClinica_AccessKey(mailUser, clinicaKey)
	            .orElseThrow(() -> new RuntimeException("X User error X"));
	        
	        // Soft Delete
	        usuario.setStatus(0);
	        usuario.setDeleted(1);
	       // usuario.setDataExclusao(LocalDateTime.now());
	        
	        // Anonimiza o email para não conflitar em cadastros futuros
	        String emailOriginal = usuario.getEmail();
	        usuario.setEmail("deleted_" + usuario.getId() + "_" + emailOriginal);
	        
	        userRepo.save(usuario);
	        
	        log.info("User deleted: {}");
	        
	        return true;
	        
	    } catch (Exception e) {
	    	
	    	throw new RuntimeException("X ERROR X");
	    }
		
	}
	
	
	
	public List<UsuarioBaseDTO> getUsuariosByClinica(String clinicaKey) {
	    
		 try {
	            List<UsuarioBaseDTO> todosUsuarios = new ArrayList<>();
	            
	            // Busca atendentes
	            List<Usersatendente> atendentes = userRepo.findByClinica_AccessKeyAndDeletedIsNull(clinicaKey);
	            for (Usersatendente atendente : atendentes) {
	                todosUsuarios.add(new UsersatendenteResponseDTO(atendente));
	            }
	            
	            // Busca médicos
	            List<Medico> medicos = medicosRepo.findByClinicaBean_AccessKeyAndDeletedIsNull(clinicaKey);
	            for (Medico medico : medicos) {
	                todosUsuarios.add(new MedicoResponseDTO(medico));
	            }
	            
	            //oder by functioId
	            todosUsuarios.sort(Comparator.comparing(UsuarioBaseDTO::getFuncaoID));
	            
	            return todosUsuarios;
	            
	        } catch (Exception e) {
	            log.error("❌ Erro ao buscar usuários: {}", e.getMessage(), e);
	            throw new RuntimeException("Erro ao buscar usuários da clínica");
	        }
		
	}
	
	// SAVE OR EDIT USER
	public UsersatendenteResponseDTO saveUser(NovoUsuarioRequest request, String clinicaKey) {
	    
	    try {
	        // 1. Busca permissão e clínica (comum para ambos)
	        Funcaoclinica permissao = permissoesRepo.findById(request.getFuncaoId())
	            .orElseThrow(() -> new RuntimeException("Permissão não encontrada para ID: " + request.getFuncao()));
	        
	        Clinica clinicaRegister = clinicaRepo.findByAccessKey(clinicaKey)
	            .orElseThrow(() -> new RuntimeException("Clínica não encontrada para chave: " + clinicaKey));
	        
	        Usersatendente userData;
	        boolean isEdicao = request.getId() != null && request.getId() > 0;
	        
	        // 2. Se for EDIÇÃO: busca usuário existente
	        if (isEdicao) {
	            userData = userRepo.findById(request.getId())
	                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + request.getId()));
	            
	            // Atualiza campos da edição
	            userData.setNome(request.getNome());
	           // userData.setEmail(request.getEmail());  // Só se permitir alterar email
	            userData.setTelefone(request.getTelefone());
	            userData.setFuncaoclinica(permissao);
	            userData.setClinica(clinicaRegister);
	            userData.setObservacoes(request.getObservacoes());
	            
	            // Campos opcionais na edição
	            if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
	                userData.setAvatar(request.getAvatar());
	            }
	            
	            if (request.getStatus() != null) {
	                userData.setStatus(request.getStatus());
	            }
	            
	        } else {
	        	
	        	//Check if user exists
	            if (userRepo.existsByEmailAndClinicaAccessKeyAndDeletedIsNull(request.getEmail(), clinicaRegister.getAccessKey())) {
	                throw new ErrorRegisterUserException(request.getEmail());
	            }
	            
 	            if (medicosRepo.existsByEmailAndClinicaBean_AccessKeyAndDeletedIsNull(request.getEmail(), clinicaRegister.getAccessKey())) {
 	                throw new ErrorRegisterUserException(request.getEmail());
 	            }
	            
	            userData = new Usersatendente();
	            String senhaTemporaria = gerarSenhaAleatoria();
	            
	            
	            userData.setNome(request.getNome());
	            userData.setEmail(request.getEmail());
	            userData.setTelefone(request.getTelefone());
	            userData.setFuncaoclinica(permissao);
	            userData.setClinica(clinicaRegister);
	            userData.setObservacoes(request.getObservacoes());
	            
	            // Campos específicos de criação
	            userData.setPassword(senhaTemporaria);
	            userData.setStatus(1);  // Ativo por padrão
	            userData.setFirstlogin(1);
	            
	            String avatarPath = selecionarAvatar(request.getSexo(),permissao.getLevel());
	            userData.setAvatar(avatarPath);
	            
	            /*
	            userData.setAvatar(request.getAvatar() != null ? 
	                request.getAvatar() : "./assets/avatars/genericRandomAvatar.png");*/
	        }
	        
	        //Save on database
	        Usersatendente savedUser = userRepo.save(userData);
	        
	        return new UsersatendenteResponseDTO(savedUser);

	    } catch (ErrorRegisterUserException e) {
	        log.error("❌ Email duplicado: {}", e.getMessage());
	        throw e;  // Relança para o GlobalExceptionHandler
	        
	    } catch (RuntimeException e) {
	        log.error("❌ Erro ao salvar usuário: {}", e.getMessage());
	        throw new RuntimeException("Falha ao cadastrar usuário: " + e.getMessage());
	    }
	}
	
	private String selecionarAvatar(Integer sexo, Integer level) {
	    
	    // Gera número aleatório baseado no nível
	    int numero;
	    
	    if (level == ADMIN_SISTEMA) {
	        // Admin: 5 opções de avatar
	        numero = ThreadLocalRandom.current().nextInt(1, MAX_AVATAR_ADMIN + 1);
	        return String.format("%dgeneric_avatar_admin.png", numero);
	    }
	    
	    // Usuários normais e especiais: 10 opções
	    numero = ThreadLocalRandom.current().nextInt(MIN_AVATAR, TOTAL_AVATARES + 1);
	    String sufixo = (sexo != null && sexo == MASCULINO) ? "M" : "F";
	    
	    if (level == USUARIO_PERMISSOES_ESPECIAIS) {
	        // Usuário com permissões especiais
	        return String.format("%dgenericIcon%s_admin.png", numero, sufixo);
	    } else {
	        // Usuário normal
	        return String.format("%dgenericIcon%s.png", numero, sufixo);
	    }
	}
	
	public String gerarSenhaAleatoria() {
	    return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}

}
