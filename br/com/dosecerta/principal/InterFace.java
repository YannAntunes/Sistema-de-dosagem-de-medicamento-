package br.com.dosecerta.principal;

import br.com.dosecerta.banco.DataStore;
import br.com.dosecerta.principal.Painel.ConsultaPanel;
import br.com.dosecerta.principal.Painel.EnfermeiroPanel;
import br.com.dosecerta.principal.Painel.HistoricoPanel;
import br.com.dosecerta.principal.Painel.MedicoPanel;
import br.com.dosecerta.principal.Painel.MedicamentoPanel;
import br.com.dosecerta.principal.Painel.PacientePanel;

import javax.swing.*;
import java.awt.*;
/**
 * Classe principal que inicializa a interface gráfica do sistema de dosagem de medicamentos.
 * Utiliza Swing para criar uma interface modular com abas para diferentes funcionalidades.
 *
 * Desenvolvido por: Yann Antunes
 */
public class InterFace {

    private final JFrame frame;
    private final DataStore store;

    public InterFace() {
        this.store = new DataStore();
        this.frame = buildFrame();
    }

    private void initLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIManager.put("Control", new Color(245, 247, 250));
            UIManager.put("info", new Color(245, 247, 250));
            UIManager.put("nimbusBase", new Color(60, 63, 65));
            UIManager.put("nimbusBlueGrey", new Color(200, 200, 200));
            UIManager.put("text", new Color(30, 30, 30));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println("Erro ao definir look & feel: " + ex.getMessage());
        }
    }

    private JFrame buildFrame() {
        initLookAndFeel();

        JFrame f = new JFrame("Sistema de Dosagem - MVP - Yann Antunes");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setSize(1024, 768);
        f.setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Pacientes", new PacientePanel(store));
        tabs.addTab("Médicos", new MedicoPanel(store));
        tabs.addTab("Enfermeiros", new EnfermeiroPanel(store));
        tabs.addTab("Medicamentos", new MedicamentoPanel(store));
        tabs.addTab("Consulta", new ConsultaPanel(store, buildHistoricoModel()));
        tabs.addTab("Histórico", new HistoricoPanel(store));

        f.getContentPane().add(tabs, BorderLayout.CENTER);
        return f;
    }

    private javax.swing.table.DefaultTableModel buildHistoricoModel() {
        String[] cols = new String[] {"Data", "Paciente", "Profissional", "Medicamento", "Resultado"};
        return new javax.swing.table.DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public static void main(String[] args) {
        new InterFace().show();
    }
}
