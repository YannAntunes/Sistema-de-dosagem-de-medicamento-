package br.com.dosecerta.seguranca;

public class Usuario {

    private final String login;
    private final String senha; // em sistema real seria hash
    private final PerfilUsuario perfil;

    public Usuario(String login, String senha, PerfilUsuario perfil) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login inválido");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha inválida");
        }
        this.login = login.trim();
        this.senha = senha;
        this.perfil = perfil;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public boolean isAdmin() {
        return perfil == PerfilUsuario.ADMIN;
    }

    public boolean isMedico() {
        return perfil == PerfilUsuario.MEDICO;
    }

    public boolean isEnfermeiro() {
        return perfil == PerfilUsuario.ENFERMEIRO;
    }

    public boolean isRecepcao() {
        return perfil == PerfilUsuario.RECEPCAO;
    }
}
