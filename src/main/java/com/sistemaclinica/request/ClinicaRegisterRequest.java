package com.sistemaclinica.request;

import lombok.Data;

@Data
public class ClinicaRegisterRequest {
    private String cnpj;
    private String companyName;
    private String email;
}
