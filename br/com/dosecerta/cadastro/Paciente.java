package br.com.dosecerta.cadastro;

public class Paciente {
    private String nome;
    private String cpf;
    private double peso;
    private int idade;

    public Paciente(String nome, String cpf, double peso, int idade) {
        if(nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome invalido");
        if(cpf == null || cpf.trim().isEmpty()) throw new IllegalArgumentException("CPF invalido");
        if(peso <= 0 || peso > 500) throw new IllegalArgumentException("Peso deve estar entre 0 e 500 kg");
        if(idade < 0 || idade > 120) throw new IllegalArgumentException("Idade invalido");

        this.nome = nome.trim();
        this.cpf = cpf.trim();
        this.peso = peso;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf(){
        return cpf;
    }

    public double getPeso() {
        return peso;
    }

    public int getIdade() {
        return idade;
    }

    public void setNome(String nome) {
        if(nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome invalido");
        this.nome = nome.trim();
    }

    public void setCpf(String cpf) {
        if(cpf == null || cpf.trim().isEmpty()) throw new IllegalArgumentException("CPF invalido");
        this.cpf = cpf.trim();
    }

    public void setPeso(double peso) {
        if(peso <= 0 || peso > 500) throw new IllegalArgumentException("Peso deve estar entre 0 e 500 kg");
        this.peso = peso;
    }

    public void setIdade(int idade) {
        if(idade < 0 || idade > 120) throw new IllegalArgumentException("Idade invalido");
        this.idade = idade;
    }

    @Override
    public String toString() {
        return nome + "(CPF: " + cpf + ")";
    }
}
