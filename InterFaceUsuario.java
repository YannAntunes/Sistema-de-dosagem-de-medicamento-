
import java.util.Scanner;

public class InterFaceUsuario {
    private Scanner scanner;

    public InterFaceUsuario() {
        this.scanner = new Scanner(System.in);
    }

    public Paciente criarPaciente() {
        int tentativasMaximas = 3;
        int tentativas = 0;

    
        while (tentativas < tentativasMaximas) {
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

                return new Paciente(nomePaciente, peso, altura, idade);

            } catch (IllegalArgumentException e) {
                tentativas++;
                System.out.println("Erro: " + e.getMessage());
                if (tentativas < tentativasMaximas) {
                    System.out.println("Tente novamente (" + (tentativasMaximas - tentativas) + " tenativas restantes)");
                }
                 scanner.nextLine();
            }          
          
        }
        System.out.println("Numero maximo de tentativas atingido");
        return null;    
        
    }

    public Medicamento criarMedicamento() {
        int tentativasMaximas = 3;
        int tentativas = 0;

        while (tentativas < tentativasMaximas) {
            try {
                System.out.println("Nome do medicamento: ");
                String nomeMedicamento = scanner.nextLine();

                System.out.println("Dose recomendada por Kg (mg/kg): ");
                double dosePorKg = scanner.nextDouble();

                System.out.println("Digite maxima permitida (mg): ");
                double doseMaxima = scanner.nextDouble();

                scanner.nextLine();

                return new Medicamento(nomeMedicamento, dosePorKg, doseMaxima);

            } catch (IllegalArgumentException e) {
                tentativas++;
                System.out.println("Erro: " + e.getMessage());
                if (tentativas < tentativasMaximas) {
                    System.out.println("Tente novamente (" + (tentativasMaximas - tentativas) + " tenativas restantes)");
                }
                scanner.nextLine();
            }
            
        }
        System.out.println("Numero maximo de tentativas atingido");
        return null;
    }
    
    public void mostrarResultado(Paciente paciente, Medicamento medicamento, double doseFinal) {
        System.out.println("\n===== Resultado ====");
        System.out.println("Paciente: " + paciente.getNome());
        System.out.println("Medicamento: " + medicamento.getNome());
        System.out.println("Dosagem recomendada: " + doseFinal + " mg");
    }   
}