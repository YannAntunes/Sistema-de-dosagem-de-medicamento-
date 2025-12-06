package br.com.dosecerta.principal;

import br.com.dosecerta.banco.DataStore;
import br.com.dosecerta.seguranca.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {

    private final DataStore store;
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private Usuario usuarioAutenticado;

    public LoginDialog(Frame parent, DataStore store) {
        super(parent, "Login - Sistema de Dosagem", true);
        this.store = store;
        this.usuarioAutenticado = null;

        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Acesso ao Sistema");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        // Login
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Usuário:"), gbc);

        txtLogin = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtLogin, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Senha:"), gbc);

        txtSenha = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtSenha, gbc);

        // Botões
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setPreferredSize(new Dimension(100, 35));
        btnEntrar.addActionListener(e -> autenticar());
        btnPanel.add(btnEntrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 35));
        btnCancelar.addActionListener(e -> {
            usuarioAutenticado = null;
            dispose();
        });
        btnPanel.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnPanel, gbc);

        add(panel);
    }

    private void autenticar() {
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario u = store.autenticar(login, senha);
        if (u != null) {
            usuarioAutenticado = u;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
            txtLogin.requestFocus();
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}
