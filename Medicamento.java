class Medicamento {
    private String nome;
    private double dosePorKg;
    private double doseMaxima;

    public Medicamento(String nome, double dosePorKg, double doseMaxima) {
        this.nome = nome;
        this.dosePorKg = dosePorKg;
        this.doseMaxima = doseMaxima;
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
}

