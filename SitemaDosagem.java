import java.util.Scanner;

public class SitemaDosagem {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);

        Paciente paciente = null;
        Medicamento medicamento = null;

        int tentativasMaximas = 3;
        int tentativas = 0;

        while (paciente == null && tentativas < tentativasMaximas) {
            try {
        
                System.out.println("Nome do paciente: ");
                String nomePaciente = scanner.nextLine();

                System.out.println("Digite o peso (kg): ");
                double peso = scanner.nextDouble();

                System.out.println("Digite a altura (m): ");
                double altura = scanner.nextDouble();

                System.out.println("Digite a idade: ");
                int idade = scanner.nextInt();

                scanner.nextLine();

                paciente = new Paciente(nomePaciente, peso, altura, idade);
            } catch (IllegalArgumentException e) {
                tentativas++;
                System.out.println("Erro: " + e.getMessage());
                if (tentativas < tentativasMaximas) {
                    System.out.println("Tente novamente (" + (tentativasMaximas - tentativas) + " tenativas restantes)");
                }
            }
            scanner.nextLine();
        }
        
        while (medicamento == null && tentativas < tentativasMaximas) {
            try {
                System.out.println("Nome do medicamento: ");
                String nomeMedicamento = scanner.nextLine();

                System.out.println("Dose recomendada por Kg (mg/kg): ");
                double dosePorKg = scanner.nextDouble();

                System.out.println("Digite maxima permitida (mg): ");
                double doseMaxima = scanner.nextDouble();

                scanner.nextLine();

                medicamento = new Medicamento(nomeMedicamento, dosePorKg, doseMaxima);

            } catch (IllegalArgumentException e) {
                tentativas++;
                System.out.println("Erro: " + e.getMessage());
                if (tentativas < tentativasMaximas) {
                    System.out.println("Tente novamente (" + (tentativasMaximas - tentativas) + " tenativas restantes)");
                }
            }
            scanner.nextLine();
        }

        if (medicamento == null && paciente == null) {
            System.out.println("Numero maximo de tentativas atingido");
            return;
        }
                
        double doseFinal = CalculadoraDosagem.calcular(paciente, medicamento);

        System.out.println("\n===== Resultado ====");
        System.out.println("Paciente: " + paciente.getNome());
        System.out.println("Medicamento: " + medicamento.getNome());
        System.out.println("Dosagem recomendada: " + doseFinal + " mg");
    }
}