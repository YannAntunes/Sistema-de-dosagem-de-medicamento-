package br.com.dosecerta.historico;

import br.com.dosecerta.cadastro.Medico;
import br.com.dosecerta.cadastro.Paciente;
import br.com.dosecerta.medicamento.Medicamento;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consultas {
    private Medico medico;
    private Paciente paciente;
    private Medicamento medicamento;
    private double calcularDose;
    private LocalDateTime timestamp;
    private String observacoes;

    public Consultas(Medico medico, Paciente paciente, Medicamento medicamento, double calcularDose, LocalDateTime timestamp, String observacoes) {
        this.medico = medico;
        this.paciente = paciente;
        this.medicamento = medicamento;
        this.calcularDose = calcularDose;
        this.observacoes = (observacoes == null) ? "" : observacoes;
        this.timestamp = LocalDateTime.now();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public Medico getMedico() {
        return medico;
    }

    public double getCalcularDose() {
        return calcularDose;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
}
