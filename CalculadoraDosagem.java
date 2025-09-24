class CalculadoraDosagem {
    public static double calcular (Paciente paciente, Medicamento medicamento) {
        double dose = paciente.getPeso() * medicamento.getDosePorKg();
        if (paciente.getIdade() <= 12) {
            dose *= 0.7;
        }else if (paciente.getIdade() >= 60) {
            dose *= 0.8;
        }

        if (dose > medicamento.getDoseMaxima()) {
            dose = medicamento.getDoseMaxima();
        }
        
        return dose;
    }  
}
