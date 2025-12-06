package br.com.dosecerta.historico;

import br.com.dosecerta.cadastro.Medico;
import br.com.dosecerta.cadastro.Enfermeiro;
import br.com.dosecerta.cadastro.Paciente;
import br.com.dosecerta.medicamento.Medicamento;
import br.com.dosecerta.calculo.TipoCalculo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consultas {
    private final Medico medico;
    private final Enfermeiro enfermeiro;
    private final Paciente paciente;
    private final Medicamento medicamento;
    private final double calcularDose;
    private final LocalDateTime timestamp;
    private final String observacoes;
    private final String profissional; // "medico" ou "enfermeiro"
    private TipoCalculo tipoCalculo;
    private double resultadoCalculo; // Volume em mL ou Gotas/min
    private String unidadeResultado; // "mg", "mL", "gotas/min"

    public Consultas(Medico medico, Paciente paciente, Medicamento medicamento, double calcularDose, LocalDateTime timestamp, String observacoes) {
        this.medico = medico;
        this.enfermeiro = null;
        this.paciente = paciente;
        this.medicamento = medicamento;
        this.calcularDose = calcularDose;
        this.observacoes = (observacoes == null) ? "" : observacoes;
        this.timestamp = LocalDateTime.now();
        this.profissional = "medico";
        this.tipoCalculo = TipoCalculo.DOSE_MGKG;
        this.resultadoCalculo = calcularDose;
        this.unidadeResultado = "mg";
    }

    public Consultas(Enfermeiro enfermeiro, Paciente paciente, Medicamento medicamento, double calcularDose, LocalDateTime timestamp, String observacoes) {
        this.medico = null;
        this.enfermeiro = enfermeiro;
        this.paciente = paciente;
        this.medicamento = medicamento;
        this.calcularDose = calcularDose;
        this.observacoes = (observacoes == null) ? "" : observacoes;
        this.timestamp = LocalDateTime.now();
        this.profissional = "enfermeiro";
        this.tipoCalculo = TipoCalculo.DOSE_MGKG;
        this.resultadoCalculo = calcularDose;
        this.unidadeResultado = "mg";
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

    public Enfermeiro getEnfermeiro() {
        return enfermeiro;
    }

    public String getProfissional() {
        return profissional;
    }

    public String getNomeProfissional() {
        return profissional.equals("medico") ? medico.getNome() : enfermeiro.getNome();
    }

    public String getProfissionalNome() {
        return getNomeProfissional();
    }

    public String getProfissionalTipo() {
        return profissional.equals("medico") ? "Médico" : "Enfermeiro";
    }

    public double getDoseCalculada() {
        return calcularDose;
    }

    public String getObservacao() {
        return observacoes;
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

    // Novos getters para tipo de cálculo e resultado específico
    public TipoCalculo getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(TipoCalculo tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    public double getResultadoCalculo() {
        return resultadoCalculo;
    }

    public void setResultadoCalculo(double resultadoCalculo) {
        this.resultadoCalculo = resultadoCalculo;
    }

    public String getUnidadeResultado() {
        return unidadeResultado;
    }

    public void setUnidadeResultado(String unidadeResultado) {
        this.unidadeResultado = unidadeResultado;
    }

    public String getResultadoFormatado() {
        return switch (tipoCalculo) {
            case DOSE_MGKG -> String.format("%.2f %s", resultadoCalculo, unidadeResultado);
            case VOLUME_MLH -> String.format("%.2f %s", resultadoCalculo, unidadeResultado);
            case GOTAS_MIN -> String.format("%.1f %s", resultadoCalculo, unidadeResultado);
        };
    }
}
