package br.com.dosecerta.principal.Painel;

import br.com.dosecerta.banco.DataStore;
import br.com.dosecerta.cadastro.Medico;
import br.com.dosecerta.util.ValidadorCPF;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Painel responsável pelo cadastro e visualização de médicos.
 */
public class MedicoPanel extends JPanel {

    private final DataStore store;
    private final DefaultListModel<Medico> listModel = new DefaultListModel<>();

    private JTextField txtNome;
    private JTextField txtCrm;
    private JTextField txtEstado;
    private JList<Medico> listMedicos;
    private JButton btnSalvar;
    private Medico medicoEmEdicao;

    public MedicoPanel(DataStore store) {
        this.store = store;
        initComponents();
        loadMedicos();
    }

    /**
     * Configura todos os componentes do painel e o layout visual.
     */
    private void initComponents() {

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCrm = new JLabel("CRM:");
        JLabel lblEstado = new JLabel("Estado (UF):");

        txtNome = new JTextField(30);
        txtNome.setDocument(new ValidadorCPF.ApenasLetrasDocument());

        txtCrm = new JTextField(10);
        txtCrm.setDocument(new ValidadorCPF.ApenasNumerosDocument(8));

        txtEstado = new JTextField(2);

        btnSalvar = new JButton("Salvar Médico");
        btnSalvar.addActionListener(e -> salvarMedico());

        gbc.gridx = 0; gbc.gridy = 0; form.add(lblNome, gbc);
        gbc.gridx = 1; gbc.gridy = 0; form.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1; form.add(lblCrm, gbc);
        gbc.gridx = 1; gbc.gridy = 1; form.add(txtCrm, gbc);

        gbc.gridx = 0; gbc.gridy = 2; form.add(lblEstado, gbc);
        gbc.gridx = 1; gbc.gridy = 2; form.add(txtEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(btnSalvar);
        
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> {
            clearFields();
            medicoEmEdicao = null;
            btnSalvar.setText("Salvar Médico");
        });
        buttonPanel.add(btnLimpar);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarMedico());
        buttonPanel.add(btnEditar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirMedico());
        buttonPanel.add(btnExcluir);
        
        form.add(buttonPanel, gbc);

        add(form, BorderLayout.NORTH);

        listMedicos = new JList<>(listModel);
        listMedicos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listMedicos.addListSelectionListener(e -> {
            if (listMedicos.getSelectedIndex() >= 0) {
                preencherFormularioComMedico(listMedicos.getSelectedIndex());
            }
        });
        add(new JScrollPane(listMedicos), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Atualizar Lista");
        btnRefresh.addActionListener(e -> loadMedicos());
        add(btnRefresh, BorderLayout.SOUTH);
    }

    /**
     * Salva o médico após validar entradas.
     */
    private void salvarMedico() {
        try {
            String nome = txtNome.getText().trim();
            String crmStr = txtCrm.getText().trim();
            String estado = txtEstado.getText().trim().toUpperCase();

            if (nome.isEmpty()) {
                showError("O nome não pode ser vazio.");
                return;
            }

            if (crmStr.isEmpty()) {
                showError("O CRM não pode ser vazio.");
                return;
            }

            int crm;
            try {
                crm = Integer.parseInt(crmStr);
            } catch (NumberFormatException e) {
                showError("O CRM deve ser um número inteiro válido.");
                return;
            }

            if (estado.length() != 2) {
                showError("O estado deve ter 2 letras (ex: SP, RJ).");
                return;
            }

            if (medicoEmEdicao == null) {
                Medico med = new Medico(nome, crm, estado);
                store.addMedico(med);
                loadMedicos();
                showSuccess("Médico cadastrado com sucesso!");
            } else {
                medicoEmEdicao.setNome(nome);
                medicoEmEdicao.setCrm(crm);
                medicoEmEdicao.setEstado(estado);
                loadMedicos();
                showSuccess("Médico atualizado com sucesso!");
            }

            clearFields();
            medicoEmEdicao = null;
            btnSalvar.setText("Salvar Médico");

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    /**
     * Carrega médicos já cadastrados.
     */
    private void loadMedicos() {
        listModel.clear();
        for (Medico m : store.getMedicos()) {
            listModel.addElement(m);
        }
    }

    /**
     * Exibe mensagem de erro.
     */
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Exibe mensagem de sucesso.
     */
    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Limpa os campos do formulário.
     */
    private void clearFields() {
        txtNome.setText("");
        txtCrm.setText("");
        txtEstado.setText("");
    }

    private void preencherFormularioComMedico(int index) {
        Medico m = listModel.get(index);
        txtNome.setText(m.getNome());
        txtCrm.setText(String.valueOf(m.getCrm()));
        txtEstado.setText(m.getEstado());
    }

    private void editarMedico() {
        int index = listMedicos.getSelectedIndex();
        if (index < 0) {
            showError("Selecione um médico para editar.");
            return;
        }

        medicoEmEdicao = listModel.get(index);
        preencherFormularioComMedico(index);
        btnSalvar.setText("Atualizar Médico");
    }

    private void excluirMedico() {
        int index = listMedicos.getSelectedIndex();
        if (index < 0) {
            showError("Selecione um médico para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este médico?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            store.getMedicos().remove(index);
            loadMedicos();
            clearFields();
            medicoEmEdicao = null;
            btnSalvar.setText("Salvar Médico");
            showSuccess("Médico excluído com sucesso!");
        }
    }
}
