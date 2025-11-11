package br.com.dosecerta.cadastro;

public class Enfermeiro {
    private String nome;
    private String coren;

    public Enfermeiro(String nome, String coren){
        if(nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome invalido");
        if(coren == null || coren.trim().isEmpty()) throw new IllegalArgumentException("COREN invalido");

        this.nome = nome.trim();
        this.coren = coren.trim();
    }

    public String getNome() {
        return nome;
    }

    public String getCoren() {
        return coren;
    }

    @Override
    public String toString() {
        return nome + " (COREN: " + coren + ")";
    }
}
