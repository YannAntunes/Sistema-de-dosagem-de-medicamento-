public class RegistroDosagem {
    private Paciente paciente;
    private Medicamento medicamento;
    private double doseFinal;

    public RegistroDosagem(Paciente paciente, Medicamento medicamento, double doseFinal) {
        this.paciente = paciente;
        this.medicamento = medicamento;
        this.doseFinal = doseFinal;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public double getDoseFinal() {
        return doseFinal;
    }
}
