package br.com.dosecerta.medicamento;

import br.com.dosecerta.calculo.TipoCalculo;

public class Medicamento {

    private int id;
    private String nome;
    private String brand;
    private double dosePorKg;          // mg/kg
    private double doseMaxima;         // mg
    private String intervalo;
    private String notas;

    // ===== Novos atributos para cálculos profissionais =====
    private double doseDisponivel;     // mg na ampola / comprimido
    private double volumeDisponivel;   // mL da ampola
    private int fatorGotejamento;      // gotas/mL
    private int tempoMinutos;          // tempo da infusão
    private TipoCalculo tipoPadrao;    // cálculo padrão para o medicamento

    // ==========================================================
    // ============== CONSTRUTOR COMPLETO =======================
    // ==========================================================

    public Medicamento(
            int id,
            String nome,
            String brand,
            double dosePorKg,
            double doseMaxima,
            String intervalo,
            String notas,
            double doseDisponivel,
            double volumeDisponivel,
            int fatorGotejamento,
            int tempoMinutos,
            TipoCalculo tipoPadrao
    ) {

        // ======== validações básicas ========
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome inválido.");

        if (dosePorKg <= 0)
            throw new IllegalArgumentException("dosePorKg deve ser maior que zero.");

        if (doseMaxima <= 0)
            throw new IllegalArgumentException("doseMaxima deve ser maior que zero.");

        if (doseDisponivel <= 0)
            throw new IllegalArgumentException("doseDisponivel deve ser maior que zero.");

        if (volumeDisponivel <= 0)
            throw new IllegalArgumentException("volumeDisponivel deve ser maior que zero.");

        if (fatorGotejamento < 0)
            throw new IllegalArgumentException("fatorGotejamento não pode ser negativo.");

        if (tempoMinutos < 0)
            throw new IllegalArgumentException("tempoMinutos não pode ser negativo.");

        // ======== atribuições ========
        this.id = id;
        this.nome = nome.trim();
        this.brand = (brand == null) ? "" : brand.trim();
        this.dosePorKg = dosePorKg;
        this.doseMaxima = doseMaxima;
        this.intervalo = (intervalo == null) ? "" : intervalo.trim();
        this.notas = (notas == null) ? "" : notas.trim();

        this.doseDisponivel = doseDisponivel;
        this.volumeDisponivel = volumeDisponivel;
        this.fatorGotejamento = fatorGotejamento;
        this.tempoMinutos = tempoMinutos;

        // se não passar nada: padrão DOSE_MGKG
        this.tipoPadrao = (tipoPadrao == null) ? TipoCalculo.DOSE_MGKG : tipoPadrao;
    }


    // ==========================================================
    // ===================== GETTERS ============================
    // ==========================================================

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getBrand() {
        return brand;
    }

    public double getDosePorKg() {
        return dosePorKg;
    }

    public double getDoseMaxima() {
        return doseMaxima;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public String getNotas() {
        return notas;
    }

    public double getDoseDisponivel() {
        return doseDisponivel;
    }

    public double getVolumeDisponivel() {
        return volumeDisponivel;
    }

    public int getFatorGotejamento() {
        return fatorGotejamento;
    }

    public int getTempoMinutos() {
        return tempoMinutos;
    }

    public TipoCalculo getTipoPadrao() {
        return tipoPadrao;
    }

    // ==========================================================
    // ===================== TO STRING ==========================
    // ==========================================================

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
