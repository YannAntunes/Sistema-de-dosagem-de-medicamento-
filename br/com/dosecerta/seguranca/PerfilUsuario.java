package br.com.dosecerta.seguranca;

public enum PerfilUsuario {
    ADMIN("Administrador"),
    MEDICO("Médico"),
    ENFERMEIRO("Enfermeiro"),
    RECEPCAO("Recepção");

    private final String descricao;

    PerfilUsuario(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}