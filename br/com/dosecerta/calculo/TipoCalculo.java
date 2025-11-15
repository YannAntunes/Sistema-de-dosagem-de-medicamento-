package br.com.dosecerta.calculo;

public enum TipoCalculo {
    DOSE_MGKG("Dose (mg/kg)"),
    VOLUME_MLH("Volume (mL/h)"),
    GOTAS_MIN("Gotas por minuto");

    private final String nome;

    TipoCalculo(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
