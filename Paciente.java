class Paciente {
    private String nome;
    private double peso;
    private double altura;
    private int idade;

    public Paciente(String nome, double peso, double altura, int idade) {
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
