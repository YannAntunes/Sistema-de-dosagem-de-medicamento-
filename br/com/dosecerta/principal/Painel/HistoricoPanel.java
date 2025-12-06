package br.com.dosecerta.principal.Painel;

import br.com.dosecerta.banco.DataStore;
import br.com.dosecerta.historico.Consultas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Painel responsável por exibir o histórico de consultas realizadas.
 */
public class HistoricoPanel extends JPanel {

    private final DataStore store;

    private JTable tabela;
    private DefaultTableModel modelo;

    public HistoricoPanel(DataStore store) {
        this.store = store;
        initComponents();
        loadHistorico();
    }

    /**
     * Inicialização dos componentes visuais.
     */
    private void initComponents() {

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel titulo = new JLabel("Histórico de Consultas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // Configuração da tabela
        String[] colunas = {
                "Data/Hora",
                "Profissional",
                "Paciente",
                "Medicamento",
                "Tipo de Cálculo",
                "Dose Calculada",
                "Intervalo"
        };

        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela somente leitura
            }
        };

        tabela = new JTable(modelo);
        tabela.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Atualizar Histórico");
        btnRefresh.addActionListener(e -> loadHistorico());
        add(btnRefresh, BorderLayout.SOUTH);
    }

    /**
     * Carrega todas as consultas registradas e preenche a tabela.
     */
    private void loadHistorico() {
        modelo.setRowCount(0);

        for (Consultas c : store.getConsultas()) {
            modelo.addRow(new Object[]{
                    c.getFormattedTimestamp(),
                    c.getProfissionalNome() + " (" + c.getProfissionalTipo() + ")",
                    c.getPaciente().getNome(),
                    c.getMedicamento().getNome(),
                    c.getTipoCalculo().toString(),
                    String.format("%.2f mg", c.getDoseCalculada()),
                    c.getMedicamento().getIntervalo()
            });
        }
    }
}