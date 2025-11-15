package br.com.dosecerta.calculo;

import br.com.dosecerta.medicamento.Medicamento;
import br.com.dosecerta.cadastro.Paciente;

public class CalculadoraDosagem {

    /**
     * Cálculo de dose (mg/kg)
     */
    public static double calcularDoseMgKg(Paciente p, Medicamento m) {
        double dose = p.getPeso() * m.getDosePorKg();

        if (dose > m.getDoseMaxima()) {
            dose = m.getDoseMaxima();
        }
        return dose;
    }

    /**
     * Cálculo de volume (mL/h)
     * Fórmula: Volume / tempo (h)
     */
    public static double calcularVolumeMlH(double volume, double horas) {
        if (horas <= 0) return 0;
        return volume / horas;
    }

    /**
     * Cálculo de gotas por minuto
     * Fórmula: (Volume mL x gotas/mL) ÷ tempo minutos
     */
    public static double calcularGotasMin(double volume, int gotasPorMl, int tempoMin) {
        if (tempoMin <= 0) return 0;
        return (volume * gotasPorMl) / tempoMin;
    }
}
