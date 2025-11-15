package br.com.dosecerta.util;

import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class ValidadorCPF {
    
    public static String formatarCPF(String cpf) {
        if (cpf == null) return "";
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos");
        }
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }
    
    public static boolean validarCPF(String cpf) {
        if (cpf == null) return false;
        cpf = cpf.replaceAll("[^0-9]", "");
        
        if (cpf.length() != 11) return false;
        if (cpf.matches("(\\d)\\1{10}")) return false;
        
        int soma = 0;
        int resto;
        
        for (int i = 1; i <= 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i - 1)) * (11 - i);
        }
        
        resto = (soma * 10) % 11;
        if (resto == 10 || resto == 11) resto = 0;
        if (resto != Character.getNumericValue(cpf.charAt(9))) return false;
        
        soma = 0;
        for (int i = 1; i <= 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i - 1)) * (12 - i);
        }
        
        resto = (soma * 10) % 11;
        if (resto == 10 || resto == 11) resto = 0;
        return resto == Character.getNumericValue(cpf.charAt(10));
    }

    // Validador de texto - apenas letras e espaços
    public static class ApenasLetrasDocument extends PlainDocument {
        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;
            
            StringBuilder filtrado = new StringBuilder();
            for (char c : str.toCharArray()) {
                if (Character.isLetter(c) || Character.isWhitespace(c)) {
                    filtrado.append(c);
                }
            }
            
            super.insertString(offset, filtrado.toString(), attr);
        }
    }

    // Validador de texto - apenas números
    public static class ApenasNumerosDocument extends PlainDocument {
        private int maxLength = -1;

        public ApenasNumerosDocument(int maxLength) {
            this.maxLength = maxLength;
        }

        public ApenasNumerosDocument() {
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;

            if (maxLength > 0 && getLength() + str.length() > maxLength) {
                return;
            }

            StringBuilder filtrado = new StringBuilder();
            for (char c : str.toCharArray()) {
                if (Character.isDigit(c)) {
                    filtrado.append(c);
                }
            }

            super.insertString(offset, filtrado.toString(), attr);
        }
    }
}
