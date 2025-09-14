package com.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("\033\143");
        LocalRead leitor = new LocalRead();

        // System.out.println(">>> Iniciando o teste da classe LocalRead...");

        leitor.Leitor();

        // System.out.println(">>> Teste finalizado.");
    }
}