package com.sistemaclinica.dto;

public enum UserRolesEnum {
    ADMIN("Administrador", 1),
    USER("Usuário", 2),
    DOCTOR("Médico", 3),
    ADVUSER("Usuário Avançado", 4),
    GUEST("Visitante", 5);

    private final String descricao;
    private final int nivel;

    UserRolesEnum(String descricao, int nivel) {
        this.descricao = descricao;
        this.nivel = nivel;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNivel() {
        return nivel;
    }
}