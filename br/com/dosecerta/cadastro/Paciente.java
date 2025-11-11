package br.com.dosecerta.cadastro;

public class Paciente {
    private String nome;
    private String cpf;
    private double peso;
    private int idade;

    public Paciente(String nome, String cpf, double peso, int idade) {
        if(nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome invalido");
        if(cpf == null || cpf.trim().isEmpty()) throw new IllegalArgumentException("CPF invalido");
        if(peso <= 0) throw new IllegalArgumentException("Peso invalido");
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

    @Override
    public String toString() {
        return nome + "(CPF: " + cpf + ")";
    }
}
