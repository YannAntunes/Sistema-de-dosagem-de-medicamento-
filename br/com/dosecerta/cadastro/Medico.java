package br.com.dosecerta.cadastro;

public class Medico {
    private String nome;
    private int crm;
    private String estado;

    public Medico(String nome, int crm, String estado){
        if(nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome invalido");
        if(crm <= 0) throw new IllegalArgumentException("CRM deve ser um número positivo");
        if(estado == null || estado.trim().isEmpty()) throw new IllegalArgumentException("Estado invalido");

        this.nome = nome.trim();
        this.crm = crm;
        this.estado = estado.trim().toUpperCase();
     }

     public String getNome() {
        return nome;
     }

     public int getCrm() {
        return crm;
     }

     public String getEstado() {
         return estado;
     }

     @Override
    public String toString() {
        return nome + " (CRM: " + String.valueOf(crm) + " - " + estado + ")";
     }
}
