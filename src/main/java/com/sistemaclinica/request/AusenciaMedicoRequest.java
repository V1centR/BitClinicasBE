package com.sistemaclinica.request;

import lombok.Data;

@Data
public class AusenciaMedicoRequest {

    /*
     *     {
        "mode": "hours",
        "date": "2026-03-23T03:00:00.000Z",
        "startTime": "08:00",
        "endTime": "18:00",
        "periodStart": null,
        "periodEnd": null,
        "description": "okok"
    }
     * 
     * 
     */

    private String mode;
    private String date;
    private String startTime;
    private String endTime;
    private String periodStart;
    private String periodEnd;
    private String description;
    private String emailMedico;
    private String clinicaKey;
    private Long time;
    
}
