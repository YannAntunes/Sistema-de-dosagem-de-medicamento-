package br.com.dosecerta.medicamento;

public class Medicamento {
    private int id;
    private String nome;
    private String brand;
    private double dosePorKg;
    private double doseMaxima;
    private String intervalo;
    private String notas;

    public Medicamento(int id, String nome, String brand, double dosePorKg, double doseMaxima, String intervalo, String notas) {
        if(nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome invalido");
        if(dosePorKg <= 0) throw new IllegalArgumentException("O valor deve ser maior que zero.");
        if(doseMaxima <= 0) throw new IllegalArgumentException("O valor deve ser maior que zero.");

        this.id = id;
        this.nome = nome.trim();
        this.brand = (brand == null) ? "" : brand.trim();
        this.dosePorKg = dosePorKg;
        this.doseMaxima = doseMaxima;
        this.intervalo = (intervalo == null) ? "" : intervalo.trim();
        this.notas = (notas == null) ? "" : notas.trim();
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public String getNotas() {
        return notas;
    }

    public String getNome() {
        return nome;
    }

    public double getDosePorKg() {
        return dosePorKg;
    }

    public double getDoseMaxima() {
        return doseMaxima;
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}

