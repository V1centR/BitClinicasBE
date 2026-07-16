package com.sistemaclinica.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sistemaclinica.dto.AgendamentoPorDataDTO;
import com.sistemaclinica.entity.Agendamento;
import com.sistemaclinica.entity.Paciente;
import com.sistemaclinica.request.AgendamentoRequest;
import com.sistemaclinica.request.PacienteCreateUpdateRequest;
import com.sistemaclinica.service.AgendamentoGravarService;
import com.sistemaclinica.service.AgendamentoService;
import com.sistemaclinica.service.FluxoTrabalhoService;
import com.sistemaclinica.service.PacientesService;


@RestController
@RequestMapping("/api/agendamentos")
public class ConsultasController {
	
	private static final Logger LOGGER = LogManager.getLogger(ConsultasController.class);
	
	@Autowired
    private AgendamentoService agendamentoService;
	
	@Autowired
	private AgendamentoGravarService agendamentoGravarService;
	
	@Autowired
	private PacientesService pacienteService;
	
	//Register 
	@PostMapping
	public ResponseEntity<?> novoAgendamento(@RequestBody AgendamentoRequest request, @RequestHeader(value = "X-Clinica-Key", required = true) String clinicaKey) {
		
		if (request.getPacienteName() == null ) {
            return ResponseEntity.badRequest().body("Nome do paciente é obrigatório");
        }       
        
        if (request.getMedicoId() == null) {
            return ResponseEntity.badRequest().body("Médico é obrigatório");
        }
		
		//PacienteCreateUpdateRequest
		try {
			
			if(request.isNewuser() && request.getPacienteId() == 0) {
				
				PacienteCreateUpdateRequest newPaciente = new PacienteCreateUpdateRequest();
				
				newPaciente.setNomecompleto(request.getPacienteName());
				newPaciente.setCpf(request.getPacienteCPF());
				newPaciente.setSexo(request.getSexo());
				newPaciente.setTelefone(request.getPacienteTel());
				newPaciente.setObservacoes(request.getObservacao());
				newPaciente.setEmail("-");
				
				System.out.println("Recebendo dados do novo paciente: " + request);
		        Paciente pacienteSalvo = pacienteService.salvarPacientes(newPaciente);
		        
		        request.setPacienteId(pacienteSalvo.getId());
				
			}
            
            Agendamento agendamentoSalvo = agendamentoGravarService.salvarAgendamento(request,clinicaKey);
            
            LOGGER.info("registro de consulta | clinica: {}", clinicaKey);
            
            
            return ResponseEntity.ok(agendamentoSalvo);
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Erro ao salvar agendamento: " + e.getMessage());
        }
		
	}
	
	//PUT /api/agendamentos/hoje
	@PutMapping("/updateagenda/{idAgendamento}/status")
    public HttpStatus updateStatusAgendamento(@RequestBody String dataUpdate, @PathVariable("idAgendamento") Integer idAgendamento) {
		
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(dataUpdate, JsonObject.class);
        String status = jsonObject.get("status").getAsString();
		
        try {
        	agendamentoGravarService.updateStatusAgendamento(status, idAgendamento);
        	return HttpStatus.OK;
			
		} catch (Exception e) {
			 return HttpStatus.INTERNAL_SERVER_ERROR;
		}
    }
    
    // ✅ GET /api/agendamentos/hoje
    @GetMapping("/hoje")
    public ResponseEntity<List<Agendamento>> getAgendamentosDeHoje() {
        List<Agendamento> agendamentos = agendamentoService.buscarAgendamentosDeHoje();
        return ResponseEntity.ok(agendamentos);
    }
    
    
    //GET /api/agendamentos/hoje/medico/{medicoId}
    @GetMapping("/hoje/medico/{medicoId}")
    public ResponseEntity<List<Agendamento>> getAgendamentosDeHojePorMedico(
            @PathVariable("medicoId") Long medicoId) {
        List<Agendamento> agendamentos = agendamentoService.buscarAgendamentosDeHojePorMedico(medicoId);
        return ResponseEntity.ok(agendamentos);
    }

    // GET /api/agendamentos/medico/{medicoId}/periodo
    @GetMapping("/medico/{medicoId}/periodo")
    public ResponseEntity<List<AgendamentoPorDataDTO>> getAgendamentosPorMedicoEPeriodo(
            @PathVariable("medicoId") Long medicoId,
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        List<AgendamentoPorDataDTO> agendamentos = agendamentoService.buscarPorMedicoEPeriodo(medicoId, inicio, fim);
        return ResponseEntity.ok(agendamentos);
    }
    
    //READY
    //GET /api/agendamentos/data?data=2024-01-15
    @GetMapping("/data")
    public ResponseEntity<List<AgendamentoPorDataDTO>> getAgendamentosPorData(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<AgendamentoPorDataDTO> agendamentos = agendamentoService.buscarPorData(data);
        return ResponseEntity.ok(agendamentos);
    }
    
    // ✅ GET /api/agendamentos/hoje/status/{status}
    @GetMapping("/hoje/status/{status}")
    public ResponseEntity<List<Agendamento>> getAgendamentosDeHojePorStatus(
            @PathVariable("status") String status) {
        List<Agendamento> agendamentos = 
            agendamentoService.buscarAgendamentosDeHojePorStatus(status);
        return ResponseEntity.ok(agendamentos);
    }
    
    // ✅ GET /api/agendamentos/hoje/nativo
    @GetMapping("/hoje/nativo")
    public ResponseEntity<List<Agendamento>> getAgendamentosDeHojeNativo() {
        List<Agendamento> agendamentos = agendamentoService.buscarAgendamentosDeHojeNativo();
        return ResponseEntity.ok(agendamentos);
    }
}
