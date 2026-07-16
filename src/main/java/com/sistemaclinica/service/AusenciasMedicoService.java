package com.sistemaclinica.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemaclinica.entity.Agendamento;
import com.sistemaclinica.entity.Clinica;
import com.sistemaclinica.entity.Medico;
import com.sistemaclinica.entity.Paciente;
import com.sistemaclinica.repo.AgendamentoRepo;
import com.sistemaclinica.repo.ClinicasRepo;
import com.sistemaclinica.repo.MedicoRepo;
import com.sistemaclinica.repo.PacientesRepo;
import com.sistemaclinica.request.AusenciaMedicoRequest;

@Service
public class AusenciasMedicoService {

    @Autowired
	private MedicoRepo medicosRepo;

    @Autowired
	ClinicasRepo clinicaRepo;

    @Autowired
	private AgendamentoRepo agendamentoRepo;

    @Autowired
    private PacientesRepo pacienteRepo;

    public void setAusenciaMedico(AusenciaMedicoRequest ausenciaMedicoRequest, String clinicaKey) {

        Medico medicoData = medicosRepo.findByEmailAndClinicaBean_AccessKey(ausenciaMedicoRequest.getEmailMedico(), clinicaKey);

        System.out.println(medicoData.getNome());
        
        
        Clinica clinicaRegister = clinicaRepo.findByAccessKey(clinicaKey)
 	            .orElseThrow(() -> new RuntimeException("ERROR Clinica"));

        Paciente pacienteFound = pacienteRepo.findByEmail("S7CAtSfBbGUr@bitclinica.com").orElse(null);

        LocalDateTime dataAgendamentoUTC = converterTimestampParaLocalDateTime(ausenciaMedicoRequest.getDate());
        LocalDateTime registerDate = LocalDateTime.now();

        Agendamento agendamento = new Agendamento();
        agendamento.setPacienteBean(pacienteFound);
        agendamento.setSexo("N");
       // agendamento.setCpf("00000000000");
        agendamento.setTelefone("00000000000");
        agendamento.setMedicoBean(medicoData);
        agendamento.setDataAgendamento(dataAgendamentoUTC);
        agendamento.setDataRegistro(registerDate);
        agendamento.setObservacoes("Reserva de hoario médico");
        agendamento.setStatus("reserva");
        agendamento.setConvenioBean(null);
        agendamento.setTipoconsultaBean(null);
        agendamento.setUsersatendente(null);
        agendamento.setClinica(clinicaRegister);

        agendamentoRepo.save(agendamento);
        
    }

    public void getAusenciasMedico(AusenciaMedicoRequest ausenciaMedicoRequest) {
        
    }


    public LocalDateTime converterTimestampParaLocalDateTime(String timestamp) {
        LocalDateTime dataAgendamentoUTC = Instant.parse(timestamp)
                                .atZone(ZoneId.of("GMT"))
                                .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                                .toLocalDateTime(); 
        return dataAgendamentoUTC;
    }
    
}
