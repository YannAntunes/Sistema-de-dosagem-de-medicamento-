package br.com.dosecerta.principal.Painel;

import br.com.dosecerta.cadastro.Paciente;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PacienteTableModel extends AbstractTableModel {
    private final List<Paciente> pacientes;
    private final String[] columnNames = {"Nome", "CPF", "Peso (kg)", "Idade (anos)"};

    public PacienteTableModel(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    @Override
    public int getRowCount() {
        return pacientes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Paciente p = pacientes.get(row);
        return switch (column) {
            case 0 -> p.getNome();
            case 1 -> p.getCpf();
            case 2 -> p.getPeso();
            case 3 -> p.getIdade();
            default -> "";
        };
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return switch (column) {
            case 2 -> Double.class;
            case 3 -> Integer.class;
            default -> String.class;
        };
    }
}
