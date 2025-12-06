package br.com.dosecerta.principal.Painel;

import br.com.dosecerta.banco.DataStore;
import br.com.dosecerta.cadastro.Enfermeiro;
import br.com.dosecerta.util.ValidadorCPF;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Painel responsável pelo cadastro e visualização de enfermeiros.
 */
public class EnfermeiroPanel extends JPanel {

    private final DataStore store;
    private final DefaultListModel<Enfermeiro> listModel = new DefaultListModel<>();

    private JTextField txtNome;
    private JTextField txtCorem;
    private JTextField txtEstado;
    private JList<Enfermeiro> listEnfermeiros;
    private JButton btnSalvar;
    private Enfermeiro enfermeiroEmEdicao;

    public EnfermeiroPanel(DataStore store) {
        this.store = store;
        initComponents();
        loadEnfermeiros();
    }

    /**
     * Inicializa os componentes visuais e monta o layout.
     */
    private void initComponents() {

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCorem = new JLabel("COREN:");
        JLabel lblEstado = new JLabel("Estado (UF):");

        txtNome = new JTextField(30);
        txtNome.setDocument(new ValidadorCPF.ApenasLetrasDocument());

        txtCorem = new JTextField(14);
        txtCorem.setDocument(new ValidadorCPF.ApenasNumerosDocument(10));

        txtEstado = new JTextField(2);

        btnSalvar = new JButton("Salvar Enfermeiro");
        btnSalvar.addActionListener(e -> salvarEnfermeiro());

        gbc.gridx = 0; gbc.gridy = 0; form.add(lblNome, gbc);
        gbc.gridx = 1; gbc.gridy = 0; form.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1; form.add(lblCorem, gbc);
        gbc.gridx = 1; gbc.gridy = 1; form.add(txtCorem, gbc);

        gbc.gridx = 0; gbc.gridy = 2; form.add(lblEstado, gbc);
        gbc.gridx = 1; gbc.gridy = 2; form.add(txtEstado, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(btnSalvar);
        
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> {
            clearFields();
            enfermeiroEmEdicao = null;
            btnSalvar.setText("Salvar Enfermeiro");
        });
        buttonPanel.add(btnLimpar);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarEnfermeiro());
        buttonPanel.add(btnEditar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirEnfermeiro());
        buttonPanel.add(btnExcluir);
        
        form.add(buttonPanel, gbc);

        add(form, BorderLayout.NORTH);

        listEnfermeiros = new JList<>(listModel);
        listEnfermeiros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listEnfermeiros.addListSelectionListener(e -> {
            if (listEnfermeiros.getSelectedIndex() >= 0) {
                preencherFormularioComEnfermeiro(listEnfermeiros.getSelectedIndex());
            }
        });
        add(new JScrollPane(listEnfermeiros), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Atualizar Lista");
        btnRefresh.addActionListener(e -> loadEnfermeiros());
        add(btnRefresh, BorderLayout.SOUTH);
    }

    /**
     * Salva um novo enfermeiro após validações.
     */
    private void salvarEnfermeiro() {
        try {
            String nome = txtNome.getText().trim();
            String corem = txtCorem.getText().trim();
            String estado = txtEstado.getText().trim().toUpperCase();

            if (nome.isEmpty()) {
                showError("Nome não pode ser vazio.");
                return;
            }

            if (estado.length() != 2) {
                showError("Estado deve conter 2 letras (ex: SP, RJ).");
                return;
            }

            if (enfermeiroEmEdicao == null) {
                Enfermeiro enf = new Enfermeiro(nome, corem, estado);
                store.addEnfermeiro(enf);
                loadEnfermeiros();
                showSuccess("Enfermeiro cadastrado com sucesso!");
            } else {
                enfermeiroEmEdicao.setNome(nome);
                enfermeiroEmEdicao.setCoren(corem);
                enfermeiroEmEdicao.setEstado(estado);
                loadEnfermeiros();
                showSuccess("Enfermeiro atualizado com sucesso!");
            }

            clearFields();
            enfermeiroEmEdicao = null;
            btnSalvar.setText("Salvar Enfermeiro");

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    /**
     * Recarrega a lista com os enfermeiros armazenados.
     */
    private void loadEnfermeiros() {
        listModel.clear();
        for (Enfermeiro e : store.getEnfermeiros()) {
            listModel.addElement(e);
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
        txtCorem.setText("");
        txtEstado.setText("");
    }

    private void preencherFormularioComEnfermeiro(int index) {
        Enfermeiro e = listModel.get(index);
        txtNome.setText(e.getNome());
        txtCorem.setText(e.getCoren());
        txtEstado.setText(e.getEstado());
    }

    private void editarEnfermeiro() {
        int index = listEnfermeiros.getSelectedIndex();
        if (index < 0) {
            showError("Selecione um enfermeiro para editar.");
            return;
        }

        enfermeiroEmEdicao = listModel.get(index);
        preencherFormularioComEnfermeiro(index);
        btnSalvar.setText("Atualizar Enfermeiro");
    }

    private void excluirEnfermeiro() {
        int index = listEnfermeiros.getSelectedIndex();
        if (index < 0) {
            showError("Selecione um enfermeiro para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este enfermeiro?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            store.getEnfermeiros().remove(index);
            loadEnfermeiros();
            clearFields();
            enfermeiroEmEdicao = null;
            btnSalvar.setText("Salvar Enfermeiro");
            showSuccess("Enfermeiro excluído com sucesso!");
        }
    }
}
