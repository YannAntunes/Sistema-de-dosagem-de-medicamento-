package br.com.dosecerta.principal;

import br.com.dosecerta.banco.DataStore;
import br.com.dosecerta.cadastro.Paciente;
import br.com.dosecerta.cadastro.Medico;
import br.com.dosecerta.cadastro.Enfermeiro;
import br.com.dosecerta.medicamento.Medicamento;
import br.com.dosecerta.historico.Consultas;
import java.awt.*;
import java.time.LocalDateTime;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class InterFace {
    private JFrame frame;
    private DataStore store = new DataStore();

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            try {
                InterFace window = new InterFace();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public InterFace() {
        initialize();
        System.out.println("Inicializando interface...");
        System.out.println("Interface carregada com sucesso.");
    }

    private void initialize() {
        frame = new JFrame("Sistema de Prescricao - MVP");
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

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

        // Carrega a lista inicial de pacientes
        for (Paciente paciente : store.getPacientes()) {
            modelPacientes.addRow(new Object[]{paciente.getNome(), paciente.getCpf(), paciente.getPeso(), paciente.getIdade()});
        }

        // Adiciona botão de atualizar
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
                double peso = Double.parseDouble(txtPeso.getText().replace(",", "."));
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
        JLabel lblCrm = new JLabel("CRM: ");
        JTextField txtCrm = new JTextField(14);

        gbc.gridx = 0; gbc.gridy = 0; form.add(lblNome, gbc);
        gbc.gridx = 1; gbc.gridy = 0; form.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1; form.add(lblCrm, gbc);
        gbc.gridx = 1; gbc.gridy = 1; form.add(txtCrm, gbc);

        JButton btnSave = new JButton("Salvar Medico");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; form.add(btnSave, gbc);

        p.add(form, BorderLayout.NORTH);

        DefaultListModel<Medico> listModel = new DefaultListModel<>();
        JList<Medico> list = new JList<>(listModel);
        p.add(new JScrollPane(list), BorderLayout.CENTER);

        // Carrega a lista inicial de médicos
        for (Medico m : store.getMedicos()) {
            listModel.addElement(m);
        }

        // Adiciona botão de atualizar
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
                Medico m = new Medico(nome, crm);
                store.addMedico(m);
                listModel.addElement(m);
                JOptionPane.showMessageDialog(frame, "Medico salvo", "OK",JOptionPane.INFORMATION_MESSAGE);
                txtNome.setText(""); txtCrm.setText("");
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

        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        JTextField txtNome = new JTextField();
        JTextField txtCorem = new JTextField();
        form.add(new JLabel("Nome: ")); form.add(txtNome);
        form.add(new JLabel("COREM: ")); form.add(txtCorem);

        JButton btnSave = new JButton("Salvar Enfermeiro");
        form.add(btnSave);

        p.add(form, BorderLayout.NORTH);

        DefaultListModel<Enfermeiro> listModel = new DefaultListModel<>();
        JList<Enfermeiro> list = new JList<>(listModel);
        p.add(new JScrollPane(list), BorderLayout.CENTER);

        // Carrega a lista inicial de enfermeiros
        for (Enfermeiro enfermeiro : store.getEnfermeiros()) {
            listModel.addElement(enfermeiro);
        }

        // Adiciona botão de atualizar
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
                Enfermeiro enfermeiro = new Enfermeiro(txtNome.getText(), txtCorem.getText());
                store.addEnfermeiro(enfermeiro);
                listModel.addElement(enfermeiro);
                JOptionPane.showMessageDialog(frame, "Enfermeiro salvo", "OK",JOptionPane.INFORMATION_MESSAGE);
                txtNome.setText(""); txtCorem.setText("");
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

        JComboBox<Medico> cbMedico = new JComboBox<>();
        JComboBox<Paciente> cbPaciente = new JComboBox<>();
        JComboBox<Medicamento> cbMedicamento = new JComboBox<>();

        reloadMedicoCombo(cbMedico);
        reloadPacienteCombo(cbPaciente);
        reloadMedicamentoCombo(cbMedicamento);

        JButton btnRefresh = new JButton("Atualizar listas");
        btnRefresh.addActionListener(e -> {
            reloadMedicoCombo(cbMedico);
            reloadPacienteCombo(cbPaciente);
            reloadMedicamentoCombo(cbMedicamento);
        });

        gbc.gridx = 0; gbc.gridy = 0; top.add(new JLabel("Medico: "), gbc);
        gbc.gridx = 1; gbc.gridy = 0; top.add(cbMedico, gbc);
        gbc.gridx = 0; gbc.gridy = 1; top.add(new JLabel("Paciente: "), gbc);
        gbc.gridx = 1; gbc.gridy = 1; top.add(cbPaciente, gbc);
        gbc.gridx = 0; gbc.gridy = 2; top.add(new JLabel("Medicamento: "), gbc);
        gbc.gridx = 1; gbc.gridy = 2; top.add(cbMedicamento, gbc);

        gbc.gridx = 0; gbc.gridy = 3; top.add(btnRefresh, gbc);

        p.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        JTextArea txtMedInfo = new JTextArea(8, 40);
        txtMedInfo.setEditable(false);
        center.add(new JScrollPane(txtMedInfo), BorderLayout.NORTH);

        JButton btnShowMed = new JButton("Mostrar informacoes do Medicamento");
        center.add(btnShowMed, BorderLayout.CENTER);

        JTextArea txtPrescricao = new JTextArea(6, 40);
        txtPrescricao.setEditable(false);
        center.add(new JScrollPane(txtPrescricao), BorderLayout.SOUTH);

        p.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGenerate = new JButton("Gerar Receita / Registrar Consulta");
        bottom.add(btnGenerate);
        p.add(bottom, BorderLayout.SOUTH);

        btnShowMed.addActionListener(e -> {
            Medicamento med = (Medicamento) cbMedicamento.getSelectedItem();
            if (med == null) {
                JOptionPane.showMessageDialog(frame, "Selecione um medicamento", "Atencao", JOptionPane.WARNING_MESSAGE);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Nome: ").append(med.getNome()).append("\n");
            sb.append("Marca: ").append(med.getBrand()).append("\n");
            sb.append("Dose por kg (mg / kg): ").append(med.getDosePorKg()).append("\n");
            sb.append("Dose maxima (mg): ").append(med.getDoseMaxima()).append("\n");
            sb.append("Intervalo: ").append(med.getIntervalo()).append("\n");
            sb.append("Notas: ").append(med.getNotas()).append("\n");
            txtMedInfo.setText(sb.toString());

            Paciente paciente = (Paciente) cbPaciente.getSelectedItem();
            if (paciente != null) {
                double dose = paciente.getPeso() * med.getDosePorKg();
                if (dose > med.getDoseMaxima()) dose = med.getDoseMaxima();
                if (paciente.getIdade() <= 12) dose *= 0.7;
                else if (paciente.getIdade() >= 60) dose *= 0.8;

                txtPrescricao.setText("Dose estimada: " + String.format("%.2f", dose) + " mg\nIntervalo: " + med.getIntervalo() + "\nObservações: " + med.getNotas());
            } else {
                txtPrescricao.setText("Selecione um paciente para ver dose estimada");
            }
        });

        btnGenerate.addActionListener(e -> {
            Medico medico = (Medico) cbMedico.getSelectedItem();
            Paciente paciente = (Paciente) cbPaciente.getSelectedItem();
            Medicamento med = (Medicamento) cbMedicamento.getSelectedItem();

            if (medico == null || paciente == null || med == null) {
                JOptionPane.showMessageDialog(frame, "Selecione medico, paciente e medicamento", "Atencao", JOptionPane.WARNING_MESSAGE);
                return ;
            }

            double dose = paciente.getPeso() * med.getDosePorKg();
            if (dose > med.getDoseMaxima()) dose = med.getDoseMaxima();
            if (paciente.getIdade() <= 12) dose *= 0.7;
            else if (paciente.getIdade() >= 60) dose *= 0.8;

            Consultas c = new Consultas(medico, paciente, med, dose, LocalDateTime.now(), med.getNotas());
            store.addConsultas(c);
            addHistoricoRow(c);

            StringBuilder pres = new StringBuilder();
            pres.append("=== RECEITA ===\n");
            pres.append("Data: ").append(c.getFormattedTimestamp()).append("\n");
            pres.append("Paciente: ").append(paciente.getNome()).append("\n");
            pres.append("Medico: ").append(medico.getNome()).append("\n");
            pres.append("Medicamento: ").append(med.getNome()).append("\n");
            pres.append("Dose: ").append(String.format("%.2f", dose)).append("mg\n");
            pres.append("Intervalo: ").append(med.getIntervalo()).append("\n");
            pres.append("Observações: ").append(med.getNotas()).append("\n");

            txtPrescricao.setText(pres.toString());
            JOptionPane.showMessageDialog(frame, "Consulta registrada e receita gerada", "OK", JOptionPane.INFORMATION_MESSAGE);
        });

        return p;
    }

    private JPanel makeHistoricoPainel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(12,12,12,12));
        
        historyTableModel = new DefaultTableModel(new Object[]{"Data", "Paciente", "Medico", "Medicamento", "Dose (mg)"}, 0);
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
                c.getMedico().getNome(),
                c.getMedicamento().getNome(),
                String.format("%.2f", c.getCalcularDose())
        });
    }
}

