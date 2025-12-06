package br.com.dosecerta.principal.Painel;

import br.com.dosecerta.banco.DataStore;
import br.com.dosecerta.cadastro.Paciente;
import br.com.dosecerta.util.ValidadorCPF;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Painel responsável pelo cadastro de pacientes.
* Parte da Interface principal do Sistema de Prescrição.
 */
public class PacientePanel extends JPanel {

    private final DataStore store;

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtPeso;
    private JTextField txtIdade;

    private PacienteTableModel tableModel;
    private JTable tabelaPacientes;
    
    private JButton btnSalvar;
    private Paciente pacienteEmEdicao;

    public PacientePanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
    }

    // -------------------------------------------------------------
    // FORMULÁRIO DE CADASTRO
    // -------------------------------------------------------------
    private JPanel buildFormPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Cadastro de Paciente"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNome = new JTextField(30);
        txtCpf = new JTextField(15);
        txtPeso = new JTextField(5);
        txtIdade = new JTextField(5);

        btnSalvar = new JButton("Cadastrar Paciente");
        btnSalvar.setPreferredSize(new Dimension(140, 35));
        btnSalvar.addActionListener(e -> salvarPaciente());

        // Linha 1 – Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome completo:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNome, gbc);

        // Linha 2 – CPF
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        panel.add(txtCpf, gbc);

        // Linha 3 – Idade
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Idade (anos):"), gbc);
        gbc.gridx = 1;
        panel.add(txtIdade, gbc);

        // Linha 4 – Peso
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Peso (kg):"), gbc);
        gbc.gridx = 1;
        panel.add(txtPeso, gbc);

        // Botões
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(btnSalvar);
        
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setPreferredSize(new Dimension(100, 35));
        btnLimpar.addActionListener(e -> {
            limparCampos();
            pacienteEmEdicao = null;
            btnSalvar.setText("Cadastrar Paciente");
        });
        buttonPanel.add(btnLimpar);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setPreferredSize(new Dimension(100, 35));
        btnEditar.addActionListener(e -> editarPaciente());
        buttonPanel.add(btnEditar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setPreferredSize(new Dimension(100, 35));
        btnExcluir.addActionListener(e -> excluirPaciente());
        buttonPanel.add(btnExcluir);
        
        panel.add(buttonPanel, gbc);

        return panel;
    }

    // -------------------------------------------------------------
    // TABELA DE PACIENTES
    // -------------------------------------------------------------
    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Pacientes Cadastrados"));

        tableModel = new PacienteTableModel(store.getPacientes());
        tabelaPacientes = new JTable(tableModel);

        panel.add(new JScrollPane(tabelaPacientes), BorderLayout.CENTER);
        return panel;
    }

    // LÓGICA DE SALVAR PACIENTE COM VALIDAÇÃO
    private void salvarPaciente() {

        String nome = txtNome.getText().trim();
        String cpf  = txtCpf.getText().trim();
        String idadeStr = txtIdade.getText().trim();
        String pesoStr  = txtPeso.getText().trim();

        // ---- VALIDAÇÕES ----
        if (nome.isEmpty() || cpf.isEmpty() || idadeStr.isEmpty() || pesoStr.isEmpty()) {
            showErro("Preencha todos os campos antes de salvar.");
            return;
        }

        // Validar CPF
        if (!ValidadorCPF.validarCPF(cpf)) {
            showErro("CPF inválido. Por favor, digite um CPF válido.");
            return;
        }

        // Se não está editando, validar CPF duplicado
        if (pacienteEmEdicao == null && store.cpfJaExiste(cpf)) {
            showErro("Já existe um paciente cadastrado com este CPF.");
            return;
        }

        // Se está editando, permitir o mesmo CPF
        if (pacienteEmEdicao != null && !pacienteEmEdicao.getCpf().equals(cpf) && store.cpfJaExiste(cpf)) {
            showErro("Já existe outro paciente cadastrado com este CPF.");
            return;
        }

        int idade;
        double peso;

        try {
            idade = Integer.parseInt(idadeStr);
            peso  = Double.parseDouble(pesoStr);
        } catch (NumberFormatException ex) {
            showErro("Idade e peso devem ser valores numéricos.");
            return;
        }

        if (idade <= 0 || peso <= 0) {
            showErro("Idade e peso devem ser maiores que zero.");
            return;
        }

        if (pacienteEmEdicao == null) {
            // Novo cadastro
            Paciente p = new Paciente(nome, cpf, peso, idade);
            store.addPaciente(p);
            JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Edição
            pacienteEmEdicao.setNome(nome);
            pacienteEmEdicao.setCpf(cpf);
            pacienteEmEdicao.setPeso(peso);
            pacienteEmEdicao.setIdade(idade);
            JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        tableModel.fireTableDataChanged();
        limparCampos();
        pacienteEmEdicao = null;
        btnSalvar.setText("Cadastrar Paciente");
    }

    private void showErro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtPeso.setText("");
        txtIdade.setText("");
    }

    private void preencherFormularioComPaciente(int row) {
        Paciente p = store.getPacientes().get(row);
        txtNome.setText(p.getNome());
        txtCpf.setText(p.getCpf());
        txtIdade.setText(String.valueOf(p.getIdade()));
        txtPeso.setText(String.valueOf(p.getPeso()));
    }

    private void editarPaciente() {
        int row = tabelaPacientes.getSelectedRow();
        if (row < 0) {
            showErro("Selecione um paciente para editar.");
            return;
        }

        pacienteEmEdicao = store.getPacientes().get(row);
        preencherFormularioComPaciente(row);
        btnSalvar.setText("Atualizar Paciente");
    }

    private void excluirPaciente() {
        int row = tabelaPacientes.getSelectedRow();
        if (row < 0) {
            showErro("Selecione um paciente para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este paciente?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            store.getPacientes().remove(row);
            tableModel.fireTableDataChanged();
            limparCampos();
            pacienteEmEdicao = null;
            btnSalvar.setText("Cadastrar Paciente");
            JOptionPane.showMessageDialog(this, "Paciente excluído com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}