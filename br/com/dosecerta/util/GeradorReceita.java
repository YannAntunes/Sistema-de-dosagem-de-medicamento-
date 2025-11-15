package br.com.dosecerta.util;

import java.io.FileOutputStream;
import java.io.IOException;

public class GeradorReceita {
    
    public static void gerarReceita(String caminhoArquivo, String conteudo) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(caminhoArquivo)) {
            fos.write(conteudo.getBytes("UTF-8"));
        }
    }

    public static String formatarReceita(String data, String paciente, String profissional, 
                                        String tipoProf, String medicamento, double dose, 
                                        String intervalo, String observacoes) {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════════════════════════════╗\n");
        sb.append("║                          RECEITA MÉDICA                            ║\n");
        sb.append("╚════════════════════════════════════════════════════════════════════╝\n\n");
        
        sb.append("Data: ").append(data).append("\n");
        sb.append("Paciente: ").append(paciente).append("\n");
        sb.append(tipoProf).append(": ").append(profissional).append("\n\n");
        
        sb.append("┌────────────────────────────────────────────────────────────────────┐\n");
        sb.append("│ MEDICAMENTO                                                        │\n");
        sb.append("└────────────────────────────────────────────────────────────────────┘\n");
        sb.append("Medicamento: ").append(medicamento).append("\n");
        sb.append("Dose: ").append(String.format("%.2f", dose)).append(" mg\n");
        sb.append("Intervalo: ").append(intervalo).append("\n\n");
        
        sb.append("┌────────────────────────────────────────────────────────────────────┐\n");
        sb.append("│ OBSERVAÇÕES                                                        │\n");
        sb.append("└────────────────────────────────────────────────────────────────────┘\n");
        sb.append(observacoes.isEmpty() ? "Sem observações" : observacoes).append("\n\n");
        
        sb.append("_____________________________\n");
        sb.append("Assinatura do Profissional\n\n");
        
        sb.append("Gerado pelo Sistema de Dosagem\n");
        sb.append("╚════════════════════════════════════════════════════════════════════╝\n");
        
        return sb.toString();
    }
}
