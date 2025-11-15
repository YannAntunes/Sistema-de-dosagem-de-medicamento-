package br.com.dosecerta.banco;

import java.util.Optional;
import br.com.dosecerta.cadastro.Enfermeiro;
import br.com.dosecerta.cadastro.Medico;
import br.com.dosecerta.cadastro.Paciente;
import br.com.dosecerta.historico.Consultas;
import br.com.dosecerta.medicamento.Medicamento;
import br.com.dosecerta.calculo.TipoCalculo;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private final List<Paciente> pacientes = new ArrayList<>();
    private final List<Medico> medicos = new ArrayList<>();
    private final List<Enfermeiro> enfermeiros = new ArrayList<>();
    private final List<Medicamento> medicamentos = new ArrayList<>();
    private final List<Consultas> consultas = new ArrayList<>();

    private int nextMedId = 1;

    public DataStore() {
        preloadMedicamentos();
    }

    // ===============================================================
    //                  MEDICAMENTOS PADRÃO PARA TESTES
    // ===============================================================
    private void preloadMedicamentos() {

        // 1 — DIPIRONA 500mg/mL (500mg em 1mL)
        medicamentos.add(new Medicamento(
            nextMedId++, "Dipirona", "Genérico",
            10.0, 500.0,
            "6h", "Analgésico e antipirético",
            500.0, 1.0,
            20, 60,
            TipoCalculo.DOSE_MGKG
        ));

        // 2 — PARACETAMOL 200mg/5mL (~40 mg/mL)
        medicamentos.add(new Medicamento(
            nextMedId++, "Paracetamol", "Tylenol",
            15.0, 1000.0,
            "6h", "Analgesico",
            200.0, 5.0,
            0, 0,
            TipoCalculo.DOSE_MGKG
        ));

        // 3 — AMOXICILINA (50 mg/mL)
        medicamentos.add(new Medicamento(
            nextMedId++, "Amoxicilina 250mg/5mL", "EMS",
            20.0, 1000.0,
            "8h", "Antibiótico penicilina",
            250.0, 5.0,
            0, 0,
            TipoCalculo.DOSE_MGKG
        ));

        // 4 — IBUPROFENO (100mg/mL)
        medicamentos.add(new Medicamento(
            nextMedId++, "Ibuprofeno Gotas", "Marca Y",
            5.0, 800.0,
            "6-8h", "Anti-inflamatório",
            100.0, 1.0,
            0, 0,
            TipoCalculo.DOSE_MGKG
        ));

        // 5 — CEFTRIAXONA 1g diluída
        medicamentos.add(new Medicamento(
            nextMedId++, "Ceftriaxona EV 1g", "Roche",
            50.0, 2000.0,
            "24h", "Antibiótico cefalosporina",
            1000.0, 10.0,
            20, 30,
            TipoCalculo.VOLUME_MLH
        ));

        // 6 — FUROSEMIDA
        medicamentos.add(new Medicamento(
            nextMedId++, "Furosemida 20mg", "Sanofi",
            1.0, 80.0,
            "8h", "Diurético",
            20.0, 2.0,
            20, 20,
            TipoCalculo.VOLUME_MLH
        ));

        // 7 — VANCOMICINA
        medicamentos.add(new Medicamento(
            nextMedId++, "Vancomicina EV", "Sandoz",
            15.0, 2000.0,
            "12h", "Infusão lenta",
            500.0, 10.0,
            20, 120,
            TipoCalculo.VOLUME_MLH
        ));

        // 8 — NORADRENALINA (bomba de infusão)
        medicamentos.add(new Medicamento(
            nextMedId++, "Noradrenalina", "Hospira",
            0.5, 40.0,
            "Contínuo", "Usar em bomba de infusão",
            4.0, 250.0,
            60, 60,
            TipoCalculo.GOTAS_MIN
        ));

        // 9 — METOCLOPRAMIDA
        medicamentos.add(new Medicamento(
            nextMedId++, "Metoclopramida", "Teuto",
            0.1, 10.0,
            "8h", "Antiemético",
            10.0, 2.0,
            20, 30,
            TipoCalculo.VOLUME_MLH
        ));

        // 10 — TRAMADOL EV
        medicamentos.add(new Medicamento(
            nextMedId++, "Tramadol EV", "Cristália",
            2.0, 200.0,
            "6h", "Analgésico opióide",
            100.0, 2.0,
            20, 30,
            TipoCalculo.VOLUME_MLH
        ));
    }

    // ==========================================================
    //                     CRUD BÁSICO
    // ==========================================================

    public void addPaciente(Paciente p) { pacientes.add(p); }
    public void addMedico(Medico m) { medicos.add(m); }
    public void addEnfermeiro(Enfermeiro e) { enfermeiros.add(e); }
    public void addConsultas(Consultas c) { consultas.add(c); }

    public List<Paciente> getPacientes() { return pacientes; }
    public List<Medico> getMedicos() { return medicos; }
    public List<Enfermeiro> getEnfermeiros() { return enfermeiros; }
    public List<Medicamento> getMedicamentos() { return medicamentos; }
    public List<Consultas> getConsultas() { return consultas; }

    // ==========================================================
    //                    MÉTODOS AUXILIARES
    // ==========================================================

    public Optional<Medicamento> findMedicamentoById(int id) {
        return medicamentos.stream().filter(m -> m.getId() == id).findFirst();
    }

    public boolean cpfJaExiste(String cpf) {
        return pacientes.stream().anyMatch(p -> p.getCpf().equalsIgnoreCase(cpf));
    }
}