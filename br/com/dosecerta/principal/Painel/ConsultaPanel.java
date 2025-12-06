package br.com.dosecerta.principal.Painel;

import br.com.dosecerta.banco.DataStore;
import br.com.dosecerta.cadastro.Enfermeiro;
import br.com.dosecerta.cadastro.Medico;
import br.com.dosecerta.cadastro.Paciente;
import br.com.dosecerta.historico.Consultas;
import br.com.dosecerta.medicamento.Medicamento;
import br.com.dosecerta.calculo.TipoCalculo;
import br.com.dosecerta.util.GeradorReceita;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class ConsultaPanel extends JPanel {

    private final DataStore store;

    private JComboBox<String> cbProfissional;
    private JComboBox<Medico> cbMedico;
    private JComboBox<Enfermeiro> cbEnfermeiro;
    private JComboBox<Paciente> cbPaciente;
    private JComboBox<Medicamento> cbMedicamento;
    private JComboBox<TipoCalculo> cbTipoCalculo;

    private JTextArea txtPrescricao;
    private JTextArea txtCalculo;

    private DefaultTableModel historicoModel;

    public ConsultaPanel(DataStore store, DefaultTableModel historicoModel) {
        this.store = store;
        this.historicoModel = historicoModel;
        initComponents();
    }

    private void initComponents() {

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cbProfissional = new JComboBox<>(new String[]{"Médico", "Enfermeiro"});
        cbMedico = new JComboBox<>();
        cbEnfermeiro = new JComboBox<>();
        cbPaciente = new JComboBox<>();
        cbMedicamento = new JComboBox<>();
        cbTipoCalculo = new JComboBox<>(TipoCalculo.values());

        reloadCombos();

        // alternar profissional
        cbProfissional.addActionListener(e -> {
            boolean isMedico = cbProfissional.getSelectedIndex() == 0;
            cbMedico.setVisible(isMedico);
            cbEnfermeiro.setVisible(!isMedico);
        });

        // medicamento define cálculo padrão
        cbMedicamento.addActionListener(e -> {
            Medicamento med = (Medicamento) cbMedicamento.getSelectedItem();
            if (med != null) cbTipoCalculo.setSelectedItem(med.getTipoPadrao());
        });

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; top.add(new JLabel("Profissional: "), gbc);
        gbc.gridx = 1; top.add(cbProfissional, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; top.add(new JLabel("Médico: "), gbc);
        gbc.gridx = 1; top.add(cbMedico, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; top.add(new JLabel("Enfermeiro: "), gbc);
        gbc.gridx = 1; top.add(cbEnfermeiro, gbc);
        cbEnfermeiro.setVisible(false);

        row++;
        gbc.gridx = 0; gbc.gridy = row; top.add(new JLabel("Paciente: "), gbc);
        gbc.gridx = 1; top.add(cbPaciente, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; top.add(new JLabel("Medicamento: "), gbc);
        gbc.gridx = 1; top.add(cbMedicamento, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; top.add(new JLabel("Tipo de Cálculo: "), gbc);
        gbc.gridx = 1; top.add(cbTipoCalculo, gbc);

        JButton btnRefresh = new JButton("Atualizar Listas");
        btnRefresh.addActionListener(e -> reloadCombos());
        row++;
        gbc.gridx = 0; gbc.gridy = row; top.add(btnRefresh, gbc);

        add(top, BorderLayout.NORTH);

        //======== CÁLCULO ========//
        JPanel calcPanel = new JPanel(new BorderLayout());
        calcPanel.setBorder(BorderFactory.createTitledBorder("Cálculo da Dosagem"));

        txtCalculo = new JTextArea();
        txtCalculo.setEditable(false);
        txtCalculo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtCalculo.setLineWrap(true);
        txtCalculo.setWrapStyleWord(true);
        txtCalculo.setRows(6);

        calcPanel.add(new JScrollPane(txtCalculo), BorderLayout.CENTER);

        //======== CENTRO ========//
        JPanel center = new JPanel(new GridLayout(2, 1, 10, 10));

        txtPrescricao = new JTextArea();
        txtPrescricao.setEditable(false);
        txtPrescricao.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtPrescricao.setLineWrap(true);
        txtPrescricao.setWrapStyleWord(true);

        JPanel prescPanel = new JPanel(new BorderLayout());
        prescPanel.setBorder(BorderFactory.createTitledBorder("Prescrição"));
        prescPanel.add(new JScrollPane(txtPrescricao), BorderLayout.CENTER);

        center.add(calcPanel);
        center.add(prescPanel);

        add(center, BorderLayout.CENTER);

        //======== BOTAO ========//
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnGerarCalculo = new JButton("Gerar Cálculo");
        btnGerarCalculo.addActionListener(e -> atualizarCalculo());

        JButton btnGerar = new JButton("Gerar Prescrição / Registrar Consulta");
        btnGerar.addActionListener(e -> gerarPrescricao());

        JButton btnExport = new JButton("Exportar Receita");
        btnExport.addActionListener(e -> exportarReceita());

        bottom.add(btnGerarCalculo);
        bottom.add(btnGerar);
        bottom.add(btnExport);

        add(bottom, BorderLayout.SOUTH);
    }


    /* =========================
     *     FUNÇÕES AUXILIARES
     * ========================= */

    private void reloadCombos() {
        cbMedico.removeAllItems();
        cbEnfermeiro.removeAllItems();
        cbPaciente.removeAllItems();
        cbMedicamento.removeAllItems();

        store.getMedicos().forEach(cbMedico::addItem);
        store.getEnfermeiros().forEach(cbEnfermeiro::addItem);
        store.getPacientes().forEach(cbPaciente::addItem);
        store.getMedicamentos().forEach(cbMedicamento::addItem);
    }


    private void atualizarCalculo() {
        Paciente p = (Paciente) cbPaciente.getSelectedItem();
        Medicamento m = (Medicamento) cbMedicamento.getSelectedItem();
        TipoCalculo tipo = (TipoCalculo) cbTipoCalculo.getSelectedItem();

        if (p == null || m == null) {
            txtCalculo.setText("Selecione paciente e medicamento para calcular a dosagem.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== CÁLCULO DA DOSAGEM ===\n\n");
        sb.append("Paciente: ").append(p.getNome()).append("\n");
        sb.append("Peso: ").append(String.format("%.2f", p.getPeso())).append(" kg\n\n");
        sb.append("Medicamento: ").append(m.getNome()).append("\n");
        sb.append("Tipo de Cálculo: ").append(tipo).append("\n\n");

        switch (tipo) {
            case DOSE_MGKG -> {
                double dose = p.getPeso() * m.getDosePorKg();
                double doseFinal = Math.min(dose, m.getDoseMaxima());
                sb.append("Fórmula: Peso × Dose/kg = Resultado\n");
                sb.append("Cálculo: ").append(String.format("%.2f", p.getPeso())).append(" kg × ");
                sb.append(String.format("%.2f", m.getDosePorKg())).append(" mg/kg = ");
                sb.append(String.format("%.2f", dose)).append(" mg\n");
                if (dose > m.getDoseMaxima()) {
                    sb.append("Limite máximo aplicado: ").append(String.format("%.2f", m.getDoseMaxima())).append(" mg\n");
                }
                sb.append("\n✓ DOSAGEM FINAL: ").append(String.format("%.2f", doseFinal)).append(" mg");
            }
            case VOLUME_MLH -> {
                double mg = p.getPeso() * m.getDosePorKg();
                if (mg > m.getDoseMaxima()) mg = m.getDoseMaxima();
                double volume = mg / m.getConcentracaoMgPorMl();
                sb.append("Fórmula: (Peso × Dose/kg) ÷ Concentração = Volume\n");
                sb.append("Cálculo: ").append(String.format("%.2f", mg)).append(" mg ÷ ");
                sb.append(String.format("%.2f", m.getConcentracaoMgPorMl())).append(" mg/mL = ");
                sb.append(String.format("%.2f", volume)).append(" mL\n");
                sb.append("\n✓ VOLUME FINAL: ").append(String.format("%.2f", volume)).append(" mL/h");
            }
            case GOTAS_MIN -> {
                double mlh = calcularDose(TipoCalculo.VOLUME_MLH, p.getPeso(), m);
                double gotas = (mlh * 20) / 60.0;
                sb.append("Fórmula: (Volume mL/h × Fator) ÷ 60 = Gotas/min\n");
                sb.append("Cálculo: (").append(String.format("%.2f", mlh)).append(" mL/h × 20) ÷ 60 = ");
                sb.append(String.format("%.2f", gotas)).append(" gotas/min\n");
                sb.append("\n✓ FLUXO FINAL: ").append(String.format("%.2f", gotas)).append(" gotas/min");
            }
        }

        sb.append("\n\nIntervalo: ").append(m.getIntervalo());

        txtCalculo.setText(sb.toString());
    }


    private void gerarPrescricao() {

        Paciente p = (Paciente) cbPaciente.getSelectedItem();
        Medicamento m = (Medicamento) cbMedicamento.getSelectedItem();
        TipoCalculo tipo = (TipoCalculo) cbTipoCalculo.getSelectedItem();

        if (p == null || m == null) {
            JOptionPane.showMessageDialog(this, "Selecione paciente e medicamento.");
            return;
        }

        double resultado = calcularDose(tipo, p.getPeso(), m);

        Consultas consulta;

        if (cbProfissional.getSelectedIndex() == 0) {
            Medico med = (Medico) cbMedico.getSelectedItem();
            consulta = new Consultas(med, p, m, resultado, LocalDateTime.now(), "");
        } else {
            Enfermeiro enf = (Enfermeiro) cbEnfermeiro.getSelectedItem();
            consulta = new Consultas(enf, p, m, resultado, LocalDateTime.now(), "");
        }

        // Definir o tipo de cálculo utilizado
        consulta.setTipoCalculo(tipo);

        store.addConsultas(consulta);

        historicoModel.addRow(new Object[]{
                consulta.getFormattedTimestamp(),
                p.getNome(),
                consulta.getProfissionalNome() + " (" + consulta.getProfissionalTipo() + ")",
                m.getNome(),
                String.format("%.2f", resultado)
        });

        txtPrescricao.setText(
                GeradorReceita.formatarReceita(
                        consulta.getFormattedTimestamp(),
                        p.getNome(),
                        consulta.getProfissionalNome(),
                        consulta.getProfissionalTipo(),
                        m.getNome(),
                        resultado,
                        m.getIntervalo(),
                        m.getNotas()
                )
        );

        JOptionPane.showMessageDialog(this, "Prescrição gerada com sucesso!");
    }


    private double calcularDose(TipoCalculo tipo, double peso, Medicamento m) {

        return switch (tipo) {
            case DOSE_MGKG -> {
                double dose = peso * m.getDosePorKg();
                yield Math.min(dose, m.getDoseMaxima());
            }

            case VOLUME_MLH -> {
                double mg = peso * m.getDosePorKg();
                if (mg > m.getDoseMaxima()) mg = m.getDoseMaxima();
                yield mg / m.getConcentracaoMgPorMl();
            }

            case GOTAS_MIN -> {
                double ml = calcularDose(TipoCalculo.VOLUME_MLH, peso, m);
                yield (ml * 20) / 60.0;
            }

            default -> 0;
        };
    }


    private void exportarReceita() {
        String conteudo = txtPrescricao.getText();

        if (conteudo.isBlank()) {
            JOptionPane.showMessageDialog(this, "Gere a receita antes de exportar.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("Receita_" + System.currentTimeMillis() + ".txt"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                GeradorReceita.gerarReceita(chooser.getSelectedFile().getAbsolutePath(), conteudo);
                JOptionPane.showMessageDialog(this, "Receita exportada com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            }
        }
    }

}
