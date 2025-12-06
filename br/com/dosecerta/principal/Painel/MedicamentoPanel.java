package br.com.dosecerta.principal.Painel;

import br.com.dosecerta.banco.DataStore;
import br.com.dosecerta.medicamento.Medicamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Painel de visualização dos medicamentos cadastrados no sistema.
 * Os medicamentos são carregados diretamente do DataStore.
 */
public class MedicamentoPanel extends JPanel {

    private final DataStore store;

    private JList<Medicamento> listaMedicamentos;
    private JTextArea txtInfo;
    private final DefaultListModel<Medicamento> listModel = new DefaultListModel<>();

    // Campos de cadastro
    private JTextField txtNome;
    private JTextField txtBrand;
    private JTextField txtDosePorKg;
    private JTextField txtDoseMaxima;
    private JTextField txtIntervalo;
    private JTextField txtNotas;
    private JTextField txtDoseDisponivel;
    private JTextField txtVolumeDisponivel;
    private JTextField txtFatorGotejamento;
    private JTextField txtTempoMinutos;
    private JButton btnSalvar;
    private Medicamento medicamentoEmEdicao;

    public MedicamentoPanel(DataStore store) {
        this.store = store;
        initComponents();
        loadMedicamentos();
    }

    /**
     * Inicializa os componentes visuais e organiza o painel.
     */
    private void initComponents() {

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel titulo = new JLabel("Medicamentos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // Painel esquerdo: lista de medicamentos
        listaMedicamentos = new JList<>(listModel);
        listaMedicamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaMedicamentos.addListSelectionListener(e -> mostrarInformacoes());

        JScrollPane scrollLista = new JScrollPane(listaMedicamentos);
        scrollLista.setPreferredSize(new Dimension(250, 0));
        scrollLista.setBorder(BorderFactory.createTitledBorder("Lista de Medicamentos"));

        add(scrollLista, BorderLayout.WEST);

        // Painel central: info + cadastro
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Painel de informações
        txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtInfo.setLineWrap(true);
        txtInfo.setWrapStyleWord(true);

        JScrollPane scrollInfo = new JScrollPane(txtInfo);
        scrollInfo.setBorder(BorderFactory.createTitledBorder("Informações do Medicamento"));
        centerPanel.add(scrollInfo);

        // Painel de cadastro
        JPanel cadastroPanel = buildCadastroPanel();
        centerPanel.add(cadastroPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Botão atualizar
        JButton btnRefresh = new JButton("Atualizar Lista");
        btnRefresh.addActionListener(e -> loadMedicamentos());
        add(btnRefresh, BorderLayout.SOUTH);
    }

    /**
     * Constrói o painel de cadastro de medicamentos.
     */
    private JPanel buildCadastroPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Cadastro de Medicamento"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        txtNome = new JTextField(7);
        txtBrand = new JTextField(7);
        txtDosePorKg = new JTextField(3);
        txtDoseMaxima = new JTextField(3);
        txtIntervalo = new JTextField(7);
        txtNotas = new JTextField(7);
        txtDoseDisponivel = new JTextField(3);
        txtVolumeDisponivel = new JTextField(3);
        txtFatorGotejamento = new JTextField(2);
        txtTempoMinutos = new JTextField(2);

        int row = 0;

        // Coluna esquerda
        // Linha 1: Nome | Marca
        gbc.gridx = 0; gbc.gridy = row; panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; panel.add(txtNome, gbc);
        gbc.gridx = 2; panel.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 3; panel.add(txtBrand, gbc);

        // Linha 2: Dose/kg | Dose Máx
        gbc.gridx = 0; gbc.gridy = ++row; panel.add(new JLabel("Dose/kg:"), gbc);
        gbc.gridx = 1; panel.add(txtDosePorKg, gbc);
        gbc.gridx = 2; panel.add(new JLabel("Dose Máx:"), gbc);
        gbc.gridx = 3; panel.add(txtDoseMaxima, gbc);

        // Linha 3: Intervalo | Notas
        gbc.gridx = 0; gbc.gridy = ++row; panel.add(new JLabel("Intervalo:"), gbc);
        gbc.gridx = 1; panel.add(txtIntervalo, gbc);
        gbc.gridx = 2; panel.add(new JLabel("Notas:"), gbc);
        gbc.gridx = 3; panel.add(txtNotas, gbc);

        // Linha 4: Dose Disp | Volume
        gbc.gridx = 0; gbc.gridy = ++row; panel.add(new JLabel("Dose Disp (mg):"), gbc);
        gbc.gridx = 1; panel.add(txtDoseDisponivel, gbc);
        gbc.gridx = 2; panel.add(new JLabel("Volume (mL):"), gbc);
        gbc.gridx = 3; panel.add(txtVolumeDisponivel, gbc);

        // Linha 5: Fator | Tempo
        gbc.gridx = 0; gbc.gridy = ++row; panel.add(new JLabel("Fator (gotas/mL):"), gbc);
        gbc.gridx = 1; panel.add(txtFatorGotejamento, gbc);
        gbc.gridx = 2; panel.add(new JLabel("Tempo (min):"), gbc);
        gbc.gridx = 3; panel.add(txtTempoMinutos, gbc);

        // Botão cadastrar
        btnSalvar = new JButton("Cadastrar");
        btnSalvar.addActionListener(e -> salvarMedicamento());
        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnSalvar, gbc);
        
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> {
            limparCampos();
            medicamentoEmEdicao = null;
            btnSalvar.setText("Cadastrar");
        });
        gbc.gridx = 2; gbc.gridwidth = 2;
        panel.add(btnLimpar, gbc);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> editarMedicamento());
        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2;
        panel.add(btnEditar, gbc);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirMedicamento());
        gbc.gridx = 2; gbc.gridwidth = 2;
        panel.add(btnExcluir, gbc);

        return panel;
    }

    /**
     * Carrega medicamentos do DataStore.
     */
    private void loadMedicamentos() {
        listModel.clear();
        for (Medicamento m : store.getMedicamentos()) {
            listModel.addElement(m);
        }
    }

    /**
     * Exibe as informações detalhadas do medicamento selecionado.
     */
    private void mostrarInformacoes() {
        Medicamento med = listaMedicamentos.getSelectedValue();

        if (med == null) {
            txtInfo.setText("");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(med.getNome()).append("\n");
        sb.append("Marca: ").append(med.getBrand()).append("\n\n");

        sb.append("=== Parâmetros de Dosagem ===\n");
        sb.append("Dose por kg: ").append(med.getDosePorKg()).append(" mg/kg\n");
        sb.append("Dose máxima: ").append(med.getDoseMaxima()).append(" mg\n");
        sb.append("Intervalo: ").append(med.getIntervalo()).append("\n\n");

        sb.append("=== Informações Técnicas ===\n");
        sb.append("Dose disponível: ").append(med.getDoseDisponivel()).append(" mg\n");
        sb.append("Volume: ").append(med.getVolumeDisponivel()).append(" mL\n");
        sb.append("Fator: ").append(med.getFatorGotejamento()).append(" gotas/mL\n");
        sb.append("Tempo: ").append(med.getTempoMinutos()).append(" min\n\n");

        sb.append("=== Notas ===\n");
        sb.append(med.getNotas()).append("\n");

        txtInfo.setText(sb.toString());
    }

    /**
     * Salva um novo medicamento no sistema.
     */
    private void salvarMedicamento() {
        try {
            String nome = txtNome.getText().trim();
            String brand = txtBrand.getText().trim();
            double dosePorKg = Double.parseDouble(txtDosePorKg.getText().trim());
            double doseMaxima = Double.parseDouble(txtDoseMaxima.getText().trim());
            String intervalo = txtIntervalo.getText().trim();
            String notas = txtNotas.getText().trim();
            double doseDisponivel = Double.parseDouble(txtDoseDisponivel.getText().trim());
            double volumeDisponivel = Double.parseDouble(txtVolumeDisponivel.getText().trim());
            int fatorGotejamento = Integer.parseInt(txtFatorGotejamento.getText().trim());
            int tempoMinutos = Integer.parseInt(txtTempoMinutos.getText().trim());

            if (nome.isEmpty() || brand.isEmpty() || intervalo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                return;
            }

            if (medicamentoEmEdicao == null) {
                // Novo cadastro
                int novoId = store.getMedicamentos().size() + 1;

                Medicamento med = new Medicamento(
                        novoId,
                        nome,
                        brand,
                        dosePorKg,
                        doseMaxima,
                        intervalo,
                        notas,
                        doseDisponivel,
                        volumeDisponivel,
                        fatorGotejamento,
                        tempoMinutos,
                        br.com.dosecerta.calculo.TipoCalculo.DOSE_MGKG
                );

                store.getMedicamentos().add(med);
                JOptionPane.showMessageDialog(this, "Medicamento cadastrado com sucesso!");
            } else {
                // Edição
                medicamentoEmEdicao.setNome(nome);
                medicamentoEmEdicao.setBrand(brand);
                medicamentoEmEdicao.setDosePorKg(dosePorKg);
                medicamentoEmEdicao.setDoseMaxima(doseMaxima);
                medicamentoEmEdicao.setIntervalo(intervalo);
                medicamentoEmEdicao.setNotas(notas);
                medicamentoEmEdicao.setDoseDisponivel(doseDisponivel);
                medicamentoEmEdicao.setVolumeDisponivel(volumeDisponivel);
                medicamentoEmEdicao.setFatorGotejamento(fatorGotejamento);
                medicamentoEmEdicao.setTempoMinutos(tempoMinutos);
                JOptionPane.showMessageDialog(this, "Medicamento atualizado com sucesso!");
            }

            limparCampos();
            medicamentoEmEdicao = null;
            btnSalvar.setText("Cadastrar");
            loadMedicamentos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preencha corretamente os campos numéricos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    /**
     * Limpa todos os campos de cadastro.
     */
    private void limparCampos() {
        txtNome.setText("");
        txtBrand.setText("");
        txtDosePorKg.setText("");
        txtDoseMaxima.setText("");
        txtIntervalo.setText("");
        txtNotas.setText("");
        txtDoseDisponivel.setText("");
        txtVolumeDisponivel.setText("");
        txtFatorGotejamento.setText("");
        txtTempoMinutos.setText("");
    }

    private void preencherFormularioComMedicamento(Medicamento med) {
        txtNome.setText(med.getNome());
        txtBrand.setText(med.getBrand());
        txtDosePorKg.setText(String.valueOf(med.getDosePorKg()));
        txtDoseMaxima.setText(String.valueOf(med.getDoseMaxima()));
        txtIntervalo.setText(med.getIntervalo());
        txtNotas.setText(med.getNotas());
        txtDoseDisponivel.setText(String.valueOf(med.getDoseDisponivel()));
        txtVolumeDisponivel.setText(String.valueOf(med.getVolumeDisponivel()));
        txtFatorGotejamento.setText(String.valueOf(med.getFatorGotejamento()));
        txtTempoMinutos.setText(String.valueOf(med.getTempoMinutos()));
    }

    private void editarMedicamento() {
        Medicamento med = listaMedicamentos.getSelectedValue();
        if (med == null) {
            JOptionPane.showMessageDialog(this, "Selecione um medicamento para editar.");
            return;
        }

        medicamentoEmEdicao = med;
        preencherFormularioComMedicamento(med);
        btnSalvar.setText("Atualizar");
    }

    private void excluirMedicamento() {
        Medicamento med = listaMedicamentos.getSelectedValue();
        if (med == null) {
            JOptionPane.showMessageDialog(this, "Selecione um medicamento para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir este medicamento?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            store.getMedicamentos().remove(med);
            loadMedicamentos();
            limparCampos();
            medicamentoEmEdicao = null;
            btnSalvar.setText("Cadastrar");
            JOptionPane.showMessageDialog(this, "Medicamento excluído com sucesso!");
        }
    }
}