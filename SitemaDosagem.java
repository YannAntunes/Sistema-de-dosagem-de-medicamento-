import java.util.Scanner;

public class SitemaDosagem {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nome do paciente: ");
        String nomePaciente = scanner.nextLine();

        System.out.println("Digite o peso (kg): ");
        double peso = scanner.nextDouble();

        System.out.println("Digite a altura (m): ");
        double altura = scanner.nextDouble();

        System.out.println("Digite a idade: ");
        int idade = scanner.nextInt();

        scanner.nextLine();

        Paciente paciente = new Paciente(nomePaciente, peso, altura, idade);

        System.out.println("Nome do medicamento: ");
        String nomeMedicamento = scanner.nextLine();

        System.out.println("Dose recomendada por Kg (mg/kg): ");
        double dosePorKg = scanner.nextDouble();

        System.out.println("Digite maxima permitida (mg): ");
        double doseMaxima = scanner.nextDouble();

        scanner.nextLine();

        Medicamento medicamento = new Medicamento(nomeMedicamento, dosePorKg, doseMaxima);

        double doseFinal = CalculadoraDosagem.calcular(paciente, medicamento);

        System.out.println("\n===== Resultado ====");
        System.out.println("Paciente: " + paciente.getNome());
        System.out.println("Medicamento: " + medicamento.getNome());
        System.out.println("Dosagem recomendada: " + doseFinal + " mg");
    }
}