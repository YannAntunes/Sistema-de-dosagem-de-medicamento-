class CalculadoraDosagem {
    public static double calcular (Paciente paciente, Medicamento medicamento) {
        double dose = paciente.getPeso() * medicamento.getDosePorKg();
        if (dose > medicamento.getDoseMaxima()) {
            dose = medicamento.getDoseMaxima();
        }
        return dose;
    }  
}
