package com.sistemaclinica.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaclinica.dto.ClinicaDTO;
import com.sistemaclinica.dto.MedicoResponseDTO;
import com.sistemaclinica.entity.Clinica;
import com.sistemaclinica.entity.Medico;
import com.sistemaclinica.repo.ClinicasRepo;
import com.sistemaclinica.repo.MedicoRepo;
import com.sistemaclinica.request.ClinicaUpdateRequest;

@Service
@Transactional
public class ClinicasService {
	
	@Autowired
	ClinicasRepo clinicaRepo;
	
	@Autowired
	MedicoRepo medicoRepo;
	
	public void updateLogo(String clinicaKey, String imageEndpoint) {
		
		 Clinica clinica = clinicaRepo.findByAccessKey(clinicaKey)
		            .orElseThrow(() -> new RuntimeException("Clínica não existe: " + clinicaKey));
		 
		 clinica.setLogomarca(imageEndpoint);
		 clinicaRepo.save(clinica);
		
		
	}
	
	public void updateClinica(String clinicaKey, Map<String, Object> updates) {

        Clinica clinica = clinicaRepo.findByAccessKey(clinicaKey)
            .orElseThrow(() -> new RuntimeException("Clínica não existe: " + clinicaKey));
        
        // Aplica cada atualização recebida
        updates.forEach((campo, valor) -> {
            switch (campo) {
                case "nomeclinica":
                    clinica.setNomeclinica((String) valor);
                    break;
                case "cnpjclinica":
                    clinica.setCnpjclinica((String) valor);
                    break;
                case "telefoneclinica":
                    clinica.setTelefoneclinica((String) valor);
                    break;
                case "enderecoclinica":
                    clinica.setEnderecoclinica((String) valor);
                    break;
                case "cidadeclinica":
                    clinica.setCidadeclinica((String) valor);
                    break;
                case "estadoclinica":
                    clinica.setUfclinica((String) valor);
                    break;
                case "cepclinica":
                    clinica.setCepclinica((String) valor);
                    break;
                case "descricaoclinica":
                    clinica.setDescricaoclinica((String) valor);
                    break;
                case "logomarca":
                    clinica.setLogomarca((String) valor);
                    break;
                case "medresponsavel":
                    Integer medicoId = Integer.parseInt(valor.toString());
                    Medico medico = medicoRepo.findById(medicoId)
                        .orElseThrow(() -> new RuntimeException("Medico não encontrado"));
                    clinica.setMedico(medico);
                    break;
                default:
                   // log.warn("Campo ignorado: {}", campo);
            }
        });
        
        clinicaRepo.save(clinica);
    }
	

	public Optional<ClinicaDTO> getClinicaByAccessKey(String clinicaKey) {
	    
	    Optional<Clinica> resultados = clinicaRepo.findByAccessKey(clinicaKey);
	    
	    if (resultados.isEmpty()) {
	        return Optional.empty();
	    }
	    
	    Clinica clinica = resultados.get();
	    
	    //if medico exists set dto object
	    MedicoResponseDTO medicoResponsavel = clinica.getMedico() != null ? 
	        new MedicoResponseDTO(clinica.getMedico()) : null;
	    
	    ClinicaDTO dto = new ClinicaDTO();
	    dto.setNomeclinica(clinica.getNomeclinica());
	    dto.setCnpjclinica(clinica.getCnpjclinica());
	    dto.setTelefoneclinica(clinica.getTelefoneclinica());
	    dto.setEnderecoclinica(clinica.getEnderecoclinica());
	    dto.setCidadeclinica(clinica.getCidadeclinica());
	    dto.setEstadoclinica(clinica.getUfclinica());
	    dto.setCepclinica(clinica.getCepclinica());
	    dto.setMedresponsavel(medicoResponsavel);
	    dto.setRegistrodata(clinica.getRegistrodata());
	    dto.setDescricaoclinica(clinica.getDescricaoclinica());
	    dto.setLogomarca(clinica.getLogomarca());
	    dto.setAccesskey(clinica.getAccessKey());
	    dto.setBannertype(clinica.getBannertype());
	    
	    return Optional.of(dto);
	}
	

}
