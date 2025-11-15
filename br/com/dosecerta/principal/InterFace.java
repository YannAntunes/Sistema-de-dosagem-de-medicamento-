package br.com.dosecerta.principal;

import br.com.dosecerta.banco.DataStore;
import br.com.dosecerta.cadastro.Paciente;
import br.com.dosecerta.calculo.TipoCalculo;
import br.com.dosecerta.cadastro.Medico;
import br.com.dosecerta.cadastro.Enfermeiro;
import br.com.dosecerta.medicamento.Medicamento;
import br.com.dosecerta.historico.Consultas;
import br.com.dosecerta.util.ValidadorCPF;
import br.com.dosecerta.util.GeradorReceita;
import java.awt.*;
import java.time.LocalDateTime;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;


public class InterFace {
    private JFrame frame;
    private final DataStore store = new DataStore();

    private DefaultTableModel historyTableModel;

    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            UIManager.put("Control", new Color(245, 247, 250));
            UIManager.put("info", new Color(245, 247, 250));
            UIManager.put("nimbusBase", new Color(60, 63, 65));
            UIManager.put("nimbusBlueGrey", new Color(200, 200, 200));
            UIManager.put("text", new Color(30, 30, 30));
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.err.println("Erro ao carregar Look and Feel: " + ex.getMessage());
        }

        EventQueue.invokeLater(() -> {
            try {
                InterFace window = new InterFace();
                window.frame.setVisible(true);
            } catch (Exception ex) {
                System.err.println("Erro ao inicializar interface: " + ex.getMessage());
            }
        });

    }

    public InterFace() {
        initialize();
        System.out.println("Inicializando interface...");
        System.out.println("Interface carregada com sucesso.");
    }

    private void initialize() {
        frame = new JFrame("Sistema de Prescricao - MVP - By Yann Antunes");
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela cheia

        JTabbedPane tabbedPane = new JTabbedPane();
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addTab("Pacientes", makePacientesPainel());
        tabbedPane.addTab("Medicos", makeMedicoPainel());
        tabbedPane.addTab("Enfermeiros", makeEnfermeirosPainel());
        tabbedPane.addTab("Consulta", makeConsultaPainel());
        tabbedPane.addTab("Historico", makeHistoricoPainel());
    }

    private JPanel makePacientesPainel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(12,12,12,12));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNome = new JLabel("Nome: ");
        JTextField txtNome = new JTextField(30);
        txtNome.setDocument(new ValidadorCPF.ApenasLetrasDocument());
        
        JLabel lblCpf = new JLabel("CPF: ");
        JTextField txtCpf = new JTextField(14);
        JLabel lblPeso = new JLabel("Peso (Kg): ");
        JTextField txtPeso = new JTextField(8);
        JLabel lblIdade = new JLabel("Idade: ");
        JTextField txtIdade = new JTextField(4);

        gbc.gridx = 0; gbc.gridy =0; form.add(lblNome, gbc);
        gbc.gridx = 1; gbc.gridy =0; form.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy =1; form.add(lblCpf, gbc);
        gbc.gridx = 1; gbc.gridy =1; form.add(txtCpf, gbc);

        gbc.gridx = 0; gbc.gridy =2; form.add(lblPeso, gbc);
        gbc.gridx = 1; gbc.gridy =2; form.add(txtPeso, gbc);

        gbc.gridx = 0; gbc.gridy =3; form.add(lblIdade, gbc);
        gbc.gridx = 1; gbc.gridy =3; form.add(txtIdade, gbc);

        JButton btnSave = new JButton("Salvar Paciente");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; form.add(btnSave,gbc);

        p.add(form, BorderLayout.NORTH);

        DefaultTableModel modelPacientes = new DefaultTableModel(new Object[]{"Nome","CPF","Peso","Idade"}, 0);
        JTable table = new JTable(modelPacientes);
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        for (Paciente paciente : store.getPacientes()) {
            modelPacientes.addRow(new Object[]{paciente.getNome(), paciente.getCpf(), paciente.getPeso(), paciente.getIdade()});
        }

        JButton btnRefresh = new JButton("Atualizar Lista");
        p.add(btnRefresh, BorderLayout.SOUTH);
        btnRefresh.addActionListener(e -> {
            modelPacientes.setRowCount(0);
            for (Paciente paciente : store.getPacientes()) {
                modelPacientes.addRow(new Object[]{paciente.getNome(), paciente.getCpf(), paciente.getPeso(), paciente.getIdade()});
            }
        });

        btnSave.addActionListener(e -> {

            try {
                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                
                if (nome.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nome não pode ser vazio", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!ValidadorCPF.validarCPF(cpf)) {
                    JOptionPane.showMessageDialog(frame, "CPF invalido! Verifique o número", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (store.cpfJaExiste(cpf)) {
                    JOptionPane.showMessageDialog(frame, "CPF já cadastrado! Use um CPF diferente", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                cpf = ValidadorCPF.formatarCPF(cpf);
                double peso = Double.parseDouble(txtPeso.getText().replace(",", "."));
                
                if (peso <= 0 || peso > 200) {
                    JOptionPane.showMessageDialog(frame, "Peso deve estar entre 0 e 200 kg", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int idade = Integer.parseInt(txtIdade.getText());

                Paciente paciente = new Paciente(nome, cpf, peso, idade);
                store.addPaciente(paciente);

                modelPacientes.addRow(new Object[]{paciente.getNome(), paciente.getCpf(), paciente.getPeso(), paciente.getIdade()});
                JOptionPane.showMessageDialog(frame,"Paciente salvo com sucesso", "OK", JOptionPane.INFORMATION_MESSAGE);

                txtNome.setText(""); txtCpf.setText(""); txtPeso.setText(""); txtIdade.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Verifique os campos numericos", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro de validacao", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,"Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return p;
    }

    private JPanel makeMedicoPainel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(12,12,12,12));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNome = new JLabel("Nome: ");
        JTextField txtNome = new JTextField(30);
        txtNome.setDocument(new ValidadorCPF.ApenasLetrasDocument());
        
        JLabel lblCrm = new JLabel("CRM: ");
        JTextField txtCrm = new JTextField(14);
        txtCrm.setDocument(new ValidadorCPF.ApenasNumerosDocument(10));
        
        JLabel lblEstado = new JLabel("Estado (UF): ");
        JTextField txtEstado = new JTextField(2);

        gbc.gridx = 0; gbc.gridy = 0; form.add(lblNome, gbc);
        gbc.gridx = 1; gbc.gridy = 0; form.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1; form.add(lblCrm, gbc);
        gbc.gridx = 1; gbc.gridy = 1; form.add(txtCrm, gbc);

        gbc.gridx = 0; gbc.gridy = 2; form.add(lblEstado, gbc);
        gbc.gridx = 1; gbc.gridy = 2; form.add(txtEstado, gbc);

        JButton btnSave = new JButton("Salvar Medico");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; form.add(btnSave, gbc);

        p.add(form, BorderLayout.NORTH);

        DefaultListModel<Medico> listModel = new DefaultListModel<>();
        JList<Medico> list = new JList<>(listModel);
        p.add(new JScrollPane(list), BorderLayout.CENTER);

        for (Medico m : store.getMedicos()) {
            listModel.addElement(m);
        }

        JButton btnRefresh = new JButton("Atualizar Lista");
        p.add(btnRefresh, BorderLayout.SOUTH);
        btnRefresh.addActionListener(e -> {
            listModel.clear();
            for (Medico m : store.getMedicos()) {
                listModel.addElement(m);
            }
        });

        btnSave.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                int crm = Integer.parseInt(txtCrm.getText().trim());
                String estado = txtEstado.getText().trim();
                
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nome não pode ser vazio", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (estado.isEmpty() || estado.length() != 2) {
                    JOptionPane.showMessageDialog(frame, "Estado deve ter 2 letras (ex: SP, RJ, MG)", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Medico m = new Medico(nome, crm, estado);
                store.addMedico(m);
                listModel.addElement(m);
                JOptionPane.showMessageDialog(frame, "Medico salvo", "OK",JOptionPane.INFORMATION_MESSAGE);
                txtNome.setText(""); txtCrm.setText(""); txtEstado.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "O CRM deve ser um número válido", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        return p;
    }

    private JPanel makeEnfermeirosPainel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(12,12,12,12));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNome = new JLabel("Nome: ");
        JTextField txtNome = new JTextField(30);
        txtNome.setDocument(new ValidadorCPF.ApenasLetrasDocument());
        
        JLabel lblCorem = new JLabel("COREN: ");
        JTextField txtCorem = new JTextField(14);
        txtCorem.setDocument(new ValidadorCPF.ApenasNumerosDocument(10));
        
        JLabel lblEstado = new JLabel("Estado (UF): ");
        JTextField txtEstado = new JTextField(2);

        gbc.gridx = 0; gbc.gridy = 0; form.add(lblNome, gbc);
        gbc.gridx = 1; gbc.gridy = 0; form.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1; form.add(lblCorem, gbc);
        gbc.gridx = 1; gbc.gridy = 1; form.add(txtCorem, gbc);

        gbc.gridx = 0; gbc.gridy = 2; form.add(lblEstado, gbc);
        gbc.gridx = 1; gbc.gridy = 2; form.add(txtEstado, gbc);

        JButton btnSave = new JButton("Salvar Enfermeiro");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; form.add(btnSave, gbc);

        p.add(form, BorderLayout.NORTH);

        DefaultListModel<Enfermeiro> listModel = new DefaultListModel<>();
        JList<Enfermeiro> list = new JList<>(listModel);
        p.add(new JScrollPane(list), BorderLayout.CENTER);

        for (Enfermeiro enfermeiro : store.getEnfermeiros()) {
            listModel.addElement(enfermeiro);
        }

        JButton btnRefresh = new JButton("Atualizar Lista");
        p.add(btnRefresh, BorderLayout.SOUTH);
        btnRefresh.addActionListener(e -> {
            listModel.clear();
            for (Enfermeiro enfermeiro : store.getEnfermeiros()) {
                listModel.addElement(enfermeiro);
            }
        });

        btnSave.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                String corem = txtCorem.getText().trim();
                String estado = txtEstado.getText().trim();
                
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Nome não pode ser vazio", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (estado.isEmpty() || estado.length() != 2) {
                    JOptionPane.showMessageDialog(frame, "Estado deve ter 2 letras (ex: SP, RJ, MG)", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Enfermeiro enfermeiro = new Enfermeiro(nome, corem, estado);
                store.addEnfermeiro(enfermeiro);
                listModel.addElement(enfermeiro);
                JOptionPane.showMessageDialog(frame, "Enfermeiro salvo", "OK",JOptionPane.INFORMATION_MESSAGE);
                txtNome.setText(""); txtCorem.setText(""); txtEstado.setText("");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        return p;
    }

    private JPanel makeConsultaPainel() {
    JPanel p = new JPanel(new BorderLayout());
    p.setBorder(new EmptyBorder(12,12,12,12));

    JPanel top = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(6,6,6,6);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.WEST;

    JComboBox<String> cbProfissional = new JComboBox<>(new String[]{"Médico", "Enfermeiro"});

    JComboBox<Medico> cbMedico = new JComboBox<>();
    JComboBox<Enfermeiro> cbEnfermeiro = new JComboBox<>();
    JComboBox<Paciente> cbPaciente = new JComboBox<>();
    JComboBox<Medicamento> cbMedicamento = new JComboBox<>();
    JComboBox<TipoCalculo> cbTipoCalculo = new JComboBox<>(TipoCalculo.values());

    reloadMedicoCombo(cbMedico);
    reloadEnfermeiroCombo(cbEnfermeiro);
    reloadPacienteCombo(cbPaciente);
    reloadMedicamentoCombo(cbMedicamento);

    cbMedicamento.addActionListener(e -> {
        Medicamento med = (Medicamento) cbMedicamento.getSelectedItem();
        if (med != null) {
            cbTipoCalculo.setSelectedItem(med.getTipoPadrao());
        }
    });

    cbProfissional.addActionListener(e -> {
        boolean isMedico = cbProfissional.getSelectedIndex() == 0;
        cbMedico.setVisible(isMedico);
        cbEnfermeiro.setVisible(!isMedico);
    });

    JButton btnRefresh = new JButton("Atualizar listas");
    btnRefresh.addActionListener(e -> {
        reloadMedicoCombo(cbMedico);
        reloadEnfermeiroCombo(cbEnfermeiro);
        reloadPacienteCombo(cbPaciente);
        reloadMedicamentoCombo(cbMedicamento);
    });

    gbc.gridx = 0; gbc.gridy = 0; top.add(new JLabel("Profissional: "), gbc);
    gbc.gridx = 1; gbc.gridy = 0; top.add(cbProfissional, gbc);

    gbc.gridx = 0; gbc.gridy = 1; top.add(new JLabel("Médico: "), gbc);
    gbc.gridx = 1; gbc.gridy = 1; top.add(cbMedico, gbc);

    gbc.gridx = 0; gbc.gridy = 2; top.add(new JLabel("Enfermeiro: "), gbc);
    gbc.gridx = 1; gbc.gridy = 2; top.add(cbEnfermeiro, gbc);
    cbEnfermeiro.setVisible(false);

    gbc.gridx = 0; gbc.gridy = 3; top.add(new JLabel("Paciente: "), gbc);
    gbc.gridx = 1; gbc.gridy = 3; top.add(cbPaciente, gbc);

    gbc.gridx = 0; gbc.gridy = 4; top.add(new JLabel("Medicamento: "), gbc);
    gbc.gridx = 1; gbc.gridy = 4; top.add(cbMedicamento, gbc);

    gbc.gridx = 0; gbc.gridy = 5; top.add(new JLabel("Tipo de Cálculo: "), gbc);
    gbc.gridx = 1; gbc.gridy = 5; top.add(cbTipoCalculo, gbc);

    gbc.gridx = 0; gbc.gridy = 6; top.add(btnRefresh, gbc);

    p.add(top, BorderLayout.NORTH);

    // ================= PAINEL CENTRAL =====================

    JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));
    center.setBorder(new EmptyBorder(10, 10, 10, 10));

    JPanel medInfoPanel = new JPanel(new BorderLayout());
    medInfoPanel.setBorder(BorderFactory.createTitledBorder("Informações do Medicamento"));

    JButton btnShowMed = new JButton("Buscar Informações");
    JPanel btnPanelMed = new JPanel(new FlowLayout(FlowLayout.LEFT));
    btnPanelMed.add(btnShowMed);
    medInfoPanel.add(btnPanelMed, BorderLayout.NORTH);

    JTextArea txtMedInfo = new JTextArea(6, 40);
    txtMedInfo.setEditable(false);
    txtMedInfo.setLineWrap(true);
    txtMedInfo.setWrapStyleWord(true);

    medInfoPanel.add(new JScrollPane(txtMedInfo), BorderLayout.CENTER);
    center.add(medInfoPanel);

    // ========== PAINEL RESULTADO DO CÁLCULO ==========
    JPanel calcPanel = new JPanel(new BorderLayout());
    calcPanel.setBorder(BorderFactory.createTitledBorder("Resultado do Cálculo"));

    JTextArea txtCalculo = new JTextArea(6, 40);
    txtCalculo.setEditable(false);
    txtCalculo.setFont(new Font("Courier New", Font.PLAIN, 12));
    txtCalculo.setLineWrap(true);
    txtCalculo.setWrapStyleWord(true);

    calcPanel.add(new JScrollPane(txtCalculo), BorderLayout.CENTER);
    center.add(calcPanel);

    // ========== PAINEL PRESCRIÇÃO/RECEITA ==========
    JPanel prescPanel = new JPanel(new BorderLayout());
    prescPanel.setBorder(BorderFactory.createTitledBorder("Prescrição / Receita Médica"));

    JTextArea txtPrescricao = new JTextArea(6, 40);
    txtPrescricao.setEditable(false);
    txtPrescricao.setFont(new Font("Courier New", Font.PLAIN, 11));
    txtPrescricao.setLineWrap(true);
    txtPrescricao.setWrapStyleWord(true);

    prescPanel.add(new JScrollPane(txtPrescricao), BorderLayout.CENTER);
    center.add(prescPanel);

    p.add(center, BorderLayout.CENTER);

    // ============= BOTÕES INFERIORES ====================

    JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
    JButton btnGenerate = new JButton("Gerar Cálculo");
    JButton btnSalvar = new JButton("Salvar Consulta / Prescrição");
    JButton btnExportReceita = new JButton("Exportar Receita");

    bottom.add(btnGenerate);
    bottom.add(btnSalvar);
    bottom.add(btnExportReceita);
    p.add(bottom, BorderLayout.SOUTH);

    // =============== LÓGICA DOS BOTÕES ====================

    btnShowMed.addActionListener(e -> {
        Medicamento med = (Medicamento) cbMedicamento.getSelectedItem();
        if (med == null) {
            JOptionPane.showMessageDialog(frame, "Selecione um medicamento!", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        txtMedInfo.setText(
            "Nome: " + med.getNome() + "\n" +
            "Marca: " + med.getBrand() + "\n" +
            "Dose/kg: " + med.getDosePorKg() + " mg\n" +
            "Dose Máxima: " + med.getDoseMaxima() + " mg\n" +
            "Intervalo: " + med.getIntervalo() + "\n" +
            "Notas: " + med.getNotas()
        );
    });

    // Variáveis para armazenar o cálculo realizado
    final double[] doseCalculada = {0};
    final TipoCalculo[] tipoCalculoSelecionado = {null};
    final double[] volumeFinal = {0};
    final double[] gotasFinal = {0};

    btnGenerate.addActionListener(e -> {
        Paciente pac = (Paciente) cbPaciente.getSelectedItem();
        Medicamento med = (Medicamento) cbMedicamento.getSelectedItem();
        TipoCalculo tipo = (TipoCalculo) cbTipoCalculo.getSelectedItem();

        if (pac == null || med == null) {
            JOptionPane.showMessageDialog(frame, "Selecione paciente e medicamento!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double peso = pac.getPeso();
        double doseKg = med.getDosePorKg();
        double doseMax = med.getDoseMaxima();
        double doseFinal = peso * doseKg;
        if (doseFinal > doseMax) doseFinal = doseMax;
        if (pac.getIdade() <= 12) doseFinal *= 0.7;
        else if (pac.getIdade() >= 60) doseFinal *= 0.8;

        // Armazena os dados do cálculo
        doseCalculada[0] = doseFinal;
        tipoCalculoSelecionado[0] = tipo;
        volumeFinal[0] = 0;
        gotasFinal[0] = 0;


        // Preenche APENAS o campo de cálculo
        String textCalculo = switch (tipo) {
            case DOSE_MGKG -> {
                String calc = "Cálculo por Peso (mg/kg)\n\n" +
                    "Paciente: " + pac.getNome() + "\n" +
                    "Medicamento: " + med.getNome() + "\n" +
                    "Peso do paciente: " + peso + " kg\n" +
                    "Dose por kg: " + doseKg + " mg/kg\n" +
                    "Dose máxima: " + doseMax + " mg\n" +
                    "Idade do paciente: " + pac.getIdade() + " anos\n" +
                    "Tipo de cálculo: " + med.getTipoPadrao() + "\n\n" +
                    ">>> Dose final calculada: " + String.format("%.2f", doseFinal) + " mg <<<";
                volumeFinal[0] = 0;
                gotasFinal[0] = 0;
                yield calc;
            }
            case VOLUME_MLH -> {
                double volumeDisp = med.getVolumeDisponivel();
                double doseDisp = med.getDoseDisponivel();
                double volCalculado = (doseFinal * volumeDisp) / doseDisp;
                volumeFinal[0] = volCalculado;
                gotasFinal[0] = 0;
                String calc = "Cálculo de Volume (mL/h)\n\n" +
                    "Paciente: " + pac.getNome() + "\n" +
                    "Medicamento: " + med.getNome() + "\n" +
                    "Dose calculada: " + String.format("%.2f", doseFinal) + " mg\n" +
                    "Volume disponível: " + volumeDisp + " mL\n" +
                    "Dose disponível: " + doseDisp + " mg\n\n" +
                    ">>> Volume final a infundir: " + String.format("%.2f", volCalculado) + " mL <<<";
                yield calc;
            }
            case GOTAS_MIN -> {
                double volume = med.getVolumeDisponivel();
                int fator = med.getFatorGotejamento();
                int tempoMin = med.getTempoMinutos();
                double gotasCalculadas = (volume * fator) / tempoMin;
                volumeFinal[0] = 0;
                gotasFinal[0] = gotasCalculadas;
                String calc = "Cálculo de Gotejamento\n\n" +
                    "Paciente: " + pac.getNome() + "\n" +
                    "Medicamento: " + med.getNome() + "\n" +
                    "Volume a infundir: " + volume + " mL\n" +
                    "Fator de gotejamento: " + fator + " gotas/mL\n" +
                    "Tempo de infusão: " + tempoMin + " minutos\n\n" +
                    ">>> Gotas por minuto: " + String.format("%.1f", gotasCalculadas) + " <<<";
                yield calc;
            }
        };

        txtCalculo.setText(textCalculo);
        txtPrescricao.setText(""); // Limpa a prescrição até salvar
    });

    btnSalvar.addActionListener(e -> {
        Paciente pac = (Paciente) cbPaciente.getSelectedItem();
        Medicamento med = (Medicamento) cbMedicamento.getSelectedItem();
        
        if (pac == null || med == null) {
            JOptionPane.showMessageDialog(frame, "Selecione paciente e medicamento!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (doseCalculada[0] == 0 || tipoCalculoSelecionado[0] == null) {
            JOptionPane.showMessageDialog(frame, "Gere um cálculo primeiro com o botão 'Gerar Cálculo'!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String nomeProf;
            if (cbProfissional.getSelectedIndex() == 0) {
                Medico medico = (Medico) cbMedico.getSelectedItem();
                if (medico == null) {
                    JOptionPane.showMessageDialog(frame, "Selecione um médico!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                nomeProf = medico.getNome();
                Consultas c = new Consultas(medico, pac, med, doseCalculada[0], LocalDateTime.now(), med.getNotas());
                
                // Configura o tipo de cálculo e o resultado específico
                c.setTipoCalculo(tipoCalculoSelecionado[0]);
                switch (tipoCalculoSelecionado[0]) {
                    case DOSE_MGKG -> {
                        c.setResultadoCalculo(doseCalculada[0]);
                        c.setUnidadeResultado("mg");
                    }
                    case VOLUME_MLH -> {
                        c.setResultadoCalculo(volumeFinal[0]);
                        c.setUnidadeResultado("mL");
                    }
                    case GOTAS_MIN -> {
                        c.setResultadoCalculo(gotasFinal[0]);
                        c.setUnidadeResultado("gotas/min");
                    }
                }
                
                store.addConsultas(c);
                addHistoricoRow(c);
            } else {
                Enfermeiro enfermeiro = (Enfermeiro) cbEnfermeiro.getSelectedItem();
                if (enfermeiro == null) {
                    JOptionPane.showMessageDialog(frame, "Selecione um enfermeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                nomeProf = enfermeiro.getNome();
                Consultas c = new Consultas(enfermeiro, pac, med, doseCalculada[0], LocalDateTime.now(), med.getNotas());
                
                // Configura o tipo de cálculo e o resultado específico
                c.setTipoCalculo(tipoCalculoSelecionado[0]);
                switch (tipoCalculoSelecionado[0]) {
                    case DOSE_MGKG -> {
                        c.setResultadoCalculo(doseCalculada[0]);
                        c.setUnidadeResultado("mg");
                    }
                    case VOLUME_MLH -> {
                        c.setResultadoCalculo(volumeFinal[0]);
                        c.setUnidadeResultado("mL");
                    }
                    case GOTAS_MIN -> {
                        c.setResultadoCalculo(gotasFinal[0]);
                        c.setUnidadeResultado("gotas/min");
                    }
                }
                
                store.addConsultas(c);
                addHistoricoRow(c);
            }

            // Preenche APENAS AGORA o painel de receita com dados específicos do cálculo
            String tipoProf = cbProfissional.getSelectedIndex() == 0 ? "Médico" : "Enfermeiro";
            String prescricao = gerarReceitaComCalculoEspecifico(
                LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                pac.getNome(),
                nomeProf,
                tipoProf,
                med.getNome(),
                doseCalculada[0],
                med.getIntervalo(),
                med.getNotas(),
                tipoCalculoSelecionado[0],
                volumeFinal[0],
                gotasFinal[0]
            );
            
            txtPrescricao.setText(prescricao);
            JOptionPane.showMessageDialog(frame, "Consulta registrada e receita gerada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    });

    btnExportReceita.addActionListener(e -> {
        String conteudo = txtPrescricao.getText();
        
        if (conteudo == null || conteudo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Gere uma prescrição primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Receita");
        fileChooser.setSelectedFile(new java.io.File("Prescricao_" + System.currentTimeMillis() + ".txt"));
        
        int resultado = fileChooser.showSaveDialog(frame);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                GeradorReceita.gerarReceita(fileChooser.getSelectedFile().getAbsolutePath(), conteudo);
                JOptionPane.showMessageDialog(frame, "Receita exportada com sucesso!\n" + fileChooser.getSelectedFile().getAbsolutePath(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao exportar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    return p;
}

    private JPanel makeHistoricoPainel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(12,12,12,12));
        
        historyTableModel = new DefaultTableModel(new Object[]{"Data", "Paciente", "Medico / Enfermeiro", "Medicamento", "Resultado"}, 0);
        JTable table = new JTable(historyTableModel);
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        for (Consultas c : store.getConsultas()) {
            addHistoricoRow(c);
        }
        
        return p;
    }

    private void reloadMedicoCombo (JComboBox<Medico> cb) {
        cb.removeAllItems();
        for (Medico m : store.getMedicos()) cb.addItem(m);
    }
    
    private void reloadEnfermeiroCombo (JComboBox<Enfermeiro> cb) {
        cb.removeAllItems();
        for (Enfermeiro e : store.getEnfermeiros()) cb.addItem(e);
    }
    
    private void reloadPacienteCombo (JComboBox<Paciente> cb) {
        cb.removeAllItems();
        for (Paciente p : store.getPacientes()) cb.addItem(p);
    }
    private void reloadMedicamentoCombo(JComboBox<Medicamento> cb) {
        cb.removeAllItems();
        for (Medicamento m : store.getMedicamentos()) cb.addItem(m);
    }

    private void addHistoricoRow(Consultas c) {
        historyTableModel.addRow(new Object[]{
                c.getFormattedTimestamp(),
                c.getPaciente().getNome(),
                c.getNomeProfissional(),
                c.getMedicamento().getNome(),
                c.getResultadoFormatado()
        });
    }

    private String gerarReceitaComCalculoEspecifico(String data, String paciente, String profissional,
                                                    String tipoProf, String medicamento, double dose,
                                                    String intervalo, String observacoes,
                                                    TipoCalculo tipoCalculo, double volume, double gotas) {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
        sb.append("║                          RECEITA MÉDICA                             ║\n");
        sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
        
        sb.append("Data: ").append(data).append("\n");
        sb.append("Paciente: ").append(paciente).append("\n");
        sb.append(tipoProf).append(": ").append(profissional).append("\n\n");
        
        sb.append("┌────────────────────────────────────────────────────────────────────┐\n");
        sb.append("│ MEDICAMENTO                                                        │\n");
        sb.append("└────────────────────────────────────────────────────────────────────┘\n");
        sb.append("Medicamento: ").append(medicamento).append("\n");
        sb.append("Intervalo: ").append(intervalo).append("\n\n");
        
        sb.append("┌────────────────────────────────────────────────────────────────────┐\n");
        sb.append("│ RESULTADO DO CÁLCULO                                               │\n");
        sb.append("└────────────────────────────────────────────────────────────────────┘\n");
        
        switch (tipoCalculo) {
            case DOSE_MGKG -> {
                sb.append("Tipo de Cálculo: Dose (mg/kg)\n");
                sb.append("Dose Final: ").append(String.format("%.2f", dose)).append(" mg\n\n");
            }
            case VOLUME_MLH -> {
                sb.append("Tipo de Cálculo: Volume (mL/h)\n");
                sb.append("Dose Prescrita: ").append(String.format("%.2f", dose)).append(" mg\n");
                sb.append("Volume Final a Infundir: ").append(String.format("%.2f", volume)).append(" mL\n\n");
            }
            case GOTAS_MIN -> {
                sb.append("Tipo de Cálculo: Gotejamento (gotas/min)\n");
                sb.append("Gotas por Minuto: ").append(String.format("%.1f", gotas)).append(" gotas/min\n\n");
            }
        }
        
        sb.append("┌────────────────────────────────────────────────────────────────────┐\n");
        sb.append("│ OBSERVAÇÕES                                                        │\n");
        sb.append("└────────────────────────────────────────────────────────────────────┘\n");
        sb.append(observacoes.isEmpty() ? "Sem observações" : observacoes).append("\n\n");
        
        sb.append("_____________________________\n");
        sb.append("Assinatura do Profissional\n\n");
        
        sb.append("Gerado pelo Sistema de Dosagem\n");
        sb.append("╚════════════════════════════════════════════════════════════════════╝\n");
        
        return sb.toString();
    }
}

