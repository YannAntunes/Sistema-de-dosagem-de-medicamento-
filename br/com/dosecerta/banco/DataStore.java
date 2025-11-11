package br.com.dosecerta.banco;

import java.util.Optional;
import br.com.dosecerta.cadastro.Enfermeiro;
import br.com.dosecerta.cadastro.Medico;
import br.com.dosecerta.cadastro.Paciente;
import br.com.dosecerta.historico.Consultas;
import br.com.dosecerta.medicamento.Medicamento;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Medico> medicos = new ArrayList<>();
    private List<Enfermeiro> enfermeiros = new ArrayList<>();
    private List<Medicamento> medicamentos = new ArrayList<>();
    private List<Consultas> consultas = new ArrayList<>();

    private int nextMedId = 1;

    public DataStore() {
        preloadMedicamentos();
    }

    private void preloadMedicamentos() {
       medicamentos.add(new Medicamento(nextMedId++, "Paracetamol 500mg", "Generico", 10.0, 1000.0, "8h", "Analgesico/antipiretico"));
       medicamentos.add(new Medicamento(nextMedId++, "Amoxicilina 500gm", "Marca X", 20.0, 2000.0, "8h", "Antibiotico"));
       medicamentos.add(new Medicamento(nextMedId++, "Ibuprofeno 400mg", "Marca Y", 5.0, 1200.0, "6-8h", "Anti-inflamatorio"));
    }

    public void addPaciente(Paciente p) { pacientes.add(p); }
    public void addMedico(Medico m) { medicos.add(m); }
    public void addEnfermeiro(Enfermeiro e) { enfermeiros.add(e); }
    public void addConsultas(Consultas c) { consultas.add(c); }

    public List<Paciente> getPacientes() { return pacientes; }
    public List<Medico> getMedicos() { return medicos; }
    public List<Enfermeiro> getEnfermeiros() { return enfermeiros; }
    public List<Medicamento> getMedicamentos() { return medicamentos; }
    public List<Consultas> getConsultas() { return consultas; }

    public Optional<Medicamento> findMedicamendoById(int id) {
        return medicamentos.stream().filter(m -> m.getId() == id).findFirst();
    }
}
