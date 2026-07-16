package com.sistemaclinica.request;

import lombok.Data;

@Data
public class AgendamentoRequest {

    // private Long id;
    private Integer medicoId;
    private Long time; // timestamp
    private String pacienteName;
    private Integer pacienteId;
    private String pacienteTel;
    private String pacienteDoc;
    private Integer tipoConsulta;
    private String status;
    private String notes;
    private Integer convenio;
    private String sexo;
    private String atendenteid;
    private boolean newuser;
    private String pacienteCPF;
    private String observacao;

    // Construtor vazio
    public AgendamentoRequest() {
    }

    /*
     * 
     * {
     * "pacienteName": "Paciente AAA",
     * "pacienteId": 0,
     * "pacienteCPF": "987.987.987-98",
     * "pacienteTel": "(11) 45454-5454",
     * "tipoConsulta": {
     * "label": "Consulta",
     * "value": 1
     * },
     * "medicoAtendente": 4,
     * "dataAgendada": "07-00",
     * "obs": "test",
     * "sexo": "M",
     * "convenio": {
     * "label": "Amil",
     * "value": 1
     * },
     * "newuser": true,
     * "id": 0
     * }
     */

}
