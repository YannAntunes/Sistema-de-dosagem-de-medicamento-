package br.com.dosecerta.cadastro;

public class Medico {
    private String nome;
    private int crm;

    public Medico(String nome, int crm){
        if(nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome invalido");
        if(crm <= 0) throw new IllegalArgumentException("CRM deve ser um número positivo");

        this.nome = nome.trim();
        this.crm = crm;
     }

     public String getNome() {
        return nome;
     }

     public int getCrm() {
        return crm;
     }

     @Override
    public String toString() {
        return nome + " (CRM: " + String.valueOf(crm) + ")";
     }
}
