
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SitemaDosagem {
    public static void main (String[] args) {
        InterFaceUsuario ui = new InterFaceUsuario();
        Scanner scanner = new Scanner(System.in);

        List<RegistroDosagem> historico = new ArrayList<>();

        boolean continuar = true;

        while (continuar) {
            Paciente paciente = ui.criarPaciente();
            if (paciente == null) break;

            Medicamento medicamento = ui.criarMedicamento();
            if (medicamento == null) break;

            double doseFinal = CalculadoraDosagem.calcular(paciente, medicamento);

            ui.mostrarResultado(paciente, medicamento, doseFinal);

            historico.add(new RegistroDosagem(paciente, medicamento, doseFinal));

            System.out.println("\nDeseja calcular outro paciente? (s/n): ");
            String resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("n")) {
                continuar = false;
            }
        }

        System.out.println("\n==== Relatorio de Atendimentos ====");
        for (RegistroDosagem r : historico) {
            System.out.println(
                "Paciente " + r.getPaciente().getNome() +
                " | Medicamento: " + r.getMedicamento().getNome() +
                " | Dosagem: " + r.getDoseFinal() + " mg"
            );
        }

        System.out.println("Encerrando Sistema");
    }
}