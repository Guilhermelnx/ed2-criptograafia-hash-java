package br.com.ed2.seguranca.util;

import java.util.Scanner;

/**
 * Utilitário para leitura segura de dados no console.
 */
public class ConsoleUtil {
    private final Scanner scanner;

    public ConsoleUtil() {
        this.scanner = new Scanner(System.in);
    }

    public String lerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite apenas números inteiros.");
            }
        }
    }

    public void fechar() {
        scanner.close();
    }
}