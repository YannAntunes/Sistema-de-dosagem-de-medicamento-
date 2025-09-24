class Paciente {
    private String nome;
    private double peso;
    private double altura;
    private int idade;

    public Paciente(String nome, double peso, double altura, int idade) {
        if(peso <= 0) {
            throw new IllegalArgumentException("Peso invalido!");
        }
        if(altura <= 0) {
            throw new IllegalArgumentException("altura invalida!");
        }
        if(idade < 0 || idade > 150) {
            throw new IllegalArgumentException("Idade invalida!");
        }

        this.nome = nome;
        this.peso = peso;
        this.altura = altura;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public double getPeso() {
        return peso;
    }

    public double getaltura() {
        return altura;
    }

    public int getIdade() {
        return idade;
    }
}
