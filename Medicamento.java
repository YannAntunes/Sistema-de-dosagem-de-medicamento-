class Medicamento {
    private String nome;
    private double dosePorKg;
    private double doseMaxima;

    public Medicamento(String nome, double dosePorKg, double doseMaxima) {
        if(dosePorKg <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
        if(doseMaxima <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
        
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

