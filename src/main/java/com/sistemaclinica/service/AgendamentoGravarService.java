package com.sistemaclinica.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemaclinica.entity.Agendamento;
import com.sistemaclinica.entity.Clinica;
import com.sistemaclinica.entity.Convenio;
import com.sistemaclinica.entity.Medico;
import com.sistemaclinica.entity.Paciente;
import com.sistemaclinica.entity.Tipoconsulta;
import com.sistemaclinica.entity.Usersatendente;
import com.sistemaclinica.repo.AgendamentoRepo;
import com.sistemaclinica.repo.ClinicasRepo;
import com.sistemaclinica.repo.ConvenioRepo;
import com.sistemaclinica.repo.MedicoRepo;
import com.sistemaclinica.repo.PacientesRepo;
import com.sistemaclinica.repo.TipoConsultaRepo;
import com.sistemaclinica.repo.UserAtendenteRepo;
import com.sistemaclinica.request.AgendamentoRequest;

@Service
@Transactional
public class AgendamentoGravarService {

        @Autowired
        private AgendamentoRepo agendamentoRepo;

        @Autowired
        private MedicoRepo medicoRepo;

        @Autowired
        private ConvenioRepo convenioRepo;

        @Autowired
        private TipoConsultaRepo tipoConsultaRepo;

        @Autowired
        private UserAtendenteRepo userAtendenteRepo;

        @Autowired
        private PacientesRepo pacienteRepo;

        @Autowired
        private ClinicasRepo clinicaRepo;

        @Autowired
        FluxoTrabalhoService fluxoService;

        public Agendamento updateStatusAgendamento(String status, Integer idAgendamento) {

                Agendamento agendamento = agendamentoRepo.findById(idAgendamento)
                                .orElseThrow(() -> new RuntimeException(
                                                "Agendamento não encontrado com ID: " + idAgendamento));

                // 2. Validar o status
                List<String> statusValidos = Arrays.asList("pendente", "confirmado", "cancelado", "realizado","ausente", "remarcado","atendido");

                if (!statusValidos.contains(status)) {
                        throw new RuntimeException("Status inválido");
                }

                // Atualizar o status "new", "confirmado", "cancelado"
                agendamento.setStatus(status);

                fluxoService.logFluxoDeTrabalho(agendamento.getClinica(), agendamento.getUsersatendente(), status, agendamento.getPacienteBean(), agendamento.getDataRegistro(),                                agendamento.getMedicoBean());

                return agendamentoRepo.save(agendamento);
        }

        public Agendamento salvarAgendamento(AgendamentoRequest request, String clinicaKey) {

                // 1. Converter dates
                LocalDateTime dataAgendamentoUTC = Instant.ofEpochMilli(request.getTime())
                                .atZone(ZoneId.of("GMT"))
                                .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                                .toLocalDateTime();

                // search relationships
                Medico medico = medicoRepo.findById(request.getMedicoId())
                                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
                // Usersatendente atendente =
                // userAtendenteRepo.findById(request.getAtendenteid()).orElseThrow(() -> new
                // RuntimeException("Usuário não encontrado")); //AQUI

                Usersatendente atendente = userAtendenteRepo.findByEmailAndClinica_AccessKeyAndDeletedIsNull(
                        request.getAtendenteid(),
                        clinicaKey).orElseThrow(() -> new RuntimeException("Usuário não encontrado")); // AQUI

                Convenio convenio = convenioRepo.findById(request.getConvenio())
                                .orElseThrow(() -> new RuntimeException("Convenio não encontrado"));

                Tipoconsulta tipoConsulta = tipoConsultaRepo.findById(request.getTipoConsulta())
                                .orElseThrow(() -> new RuntimeException(
                                                "Tipo de consulta não existente, favor registrar"));

                Paciente pacienteFound = pacienteRepo.findById(request.getPacienteId()).orElse(null);

                Clinica clinica = clinicaRepo.findByAccessKey(clinicaKey)
                                .orElseThrow(() -> new RuntimeException("Clinica não existente, favor registrar"));

                LocalDateTime registerDate = LocalDateTime.now();

                Agendamento agendamento = new Agendamento();
                agendamento.setPacienteBean(pacienteFound);
                agendamento.setSexo(request.getSexo());
                //agendamento.setCpf(request.getPacienteCPF().replace(".", "").replace("-", ""));
                agendamento.setTelefone(request.getPacienteTel());
                agendamento.setMedicoBean(medico);
                agendamento.setDataAgendamento(dataAgendamentoUTC);
                agendamento.setDataRegistro(registerDate);
                agendamento.setObservacoes(request.getNotes());
                agendamento.setStatus(request.getStatus());
                agendamento.setConvenioBean(convenio);
                agendamento.setTipoconsultaBean(tipoConsulta);
                agendamento.setUsersatendente(atendente);
                agendamento.setClinica(clinica);

                System.out.println("AGENDAMENTO OBJ ############ ");
                System.out.println(agendamento.getDataAgendamento());

                fluxoService.logFluxoDeTrabalho(clinica, atendente, "new", pacienteFound, registerDate, medico);

                // SAVE ########
                return agendamentoRepo.save(agendamento);
        }

}
