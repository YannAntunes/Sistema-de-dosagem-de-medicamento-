package br.com.dosecerta.cadastro;

public class Enfermeiro {
    private String nome;
    private String coren;
    private String estado;

    public Enfermeiro(String nome, String coren, String estado){
        if(nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome invalido");
        if(coren == null || coren.trim().isEmpty()) throw new IllegalArgumentException("COREN invalido");
        if(estado == null || estado.trim().isEmpty()) throw new IllegalArgumentException("Estado invalido");

        this.nome = nome.trim();
        this.coren = coren.trim();
        this.estado = estado.trim().toUpperCase();
    }

    public String getNome() {
        return nome;
    }

    public String getCoren() {
        return coren;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return nome + " (COREN: " + coren + " - " + estado + ")";
    }
}
