/* Interface de terminal antiga - mantida apenas para referência
package br.com.dosecerta.principal;

import br.com.dosecerta.cadastro.Paciente;
import br.com.dosecerta.medicamento.Medicamento;
import br.com.dosecerta.medicamento.CalculadoraDosagem;
import br.com.dosecerta.historico.Consultas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RegistroDosagem {
    private Paciente paciente;
    private Medicamento medicamento;
    private double doseFinal;

    public RegistroDosagem(Paciente paciente, Medicamento medicamento, double doseFinal) {
        this.paciente = paciente;
        this.medicamento = medicamento;
        this.doseFinal = doseFinal;
    }

    public Paciente getPaciente() { return paciente; }
    public Medicamento getMedicamento() { return medicamento; }
    public double getDoseFinal() { return doseFinal; }
}

public class SitemaDosagem {
    public static void main (String[] args) {
        InterFaceUsuario ui = new InterFaceUsuario();
        Scanner scanner = new Scanner(System.in);

        List<RegistroDosagem> historico = new ArrayList<>();

        boolean rodando = true;

        while (rodando) {

            System.out.println("\n=======================");
            System.out.println("  SISTEMA DE DOSAGEM MEDICA");
            System.out.println("=========================");
            System.out.println("1 - Nova calculo");
            System.out.println("2 - Ver historico");
            System.out.println("3 - Sair");
            System.out.println("Escolha uma opcao: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
             case 1:
                Paciente paciente = ui.criarPaciente();
                if (paciente == null) break;

                Medicamento medicamento = ui.criarMedicamento();
                if (medicamento == null) break;

                double doseFinal = CalculadoraDosagem.calcular(paciente, medicamento);

                ui.mostrarResultado(paciente, medicamento, doseFinal);

                historico.add(new RegistroDosagem(paciente, medicamento, doseFinal));
                break;
            
            case 2:    
                System.out.println("\n==== Historico de Atendimentos ====");
                if (historico.isEmpty()) {
                    System.out.println("Nenhum atendimento registrado");
                } else {
                    for (int i = 0; i < historico.size(); i++) {
                        RegistroDosagem r = historico.get(i);
                        System.out.println((i+1) + ", br.com.dosecerta.cadastro.Paciente: " + r.getPaciente().getNome() + " | Remedio: " + r.getMedicamento().getNome() + " | Dosagem: " + r.getDoseFinal() + " mg");
                    }
                }
                break;

            case 3: 
                rodando = false;
                System.out.println("\nEncerrando o sistema.");
                break;

                default:
                System.out.println("Opcao invalida! Escolha 1, 2, ou 3");
            }
        }

    }
}
*/