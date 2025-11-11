package br.com.dosecerta.medicamento;

import br.com.dosecerta.cadastro.Paciente;

public class CalculadoraDosagem {
    public static double calcular (Paciente paciente, Medicamento medicamento) {
      double doseCalculada = paciente.getPeso() * medicamento.getDosePorKg();

      if (doseCalculada > medicamento.getDoseMaxima()) {
        doseCalculada = medicamento.getDoseMaxima();
      }

      if (doseCalculada < 1) {
        System.out.println("Atencao: dose e muito baixa (" + doseCalculada + " mg). Verifique os dados.");
      } else if (paciente.getIdade() > 65) {
        System.out.println("Atencao: paciente idoso. Dose reduzida recomendada");
      }

      if (paciente.getPeso() < 3 || paciente.getPeso() > 300) {
        System.out.println("Dados de peso fora da faixa típica");
      } 

      return doseCalculada;
    }  
}
