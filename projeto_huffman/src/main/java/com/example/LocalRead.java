package com.example;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class LocalRead {
    String arquivo = "projeto_huffman/src/main/java/com/example/Huffman_Coding.txt";
    File decodificar = new File(arquivo);
    Map<Character, Integer> mapa = new HashMap<>();

    public void Leitor() {
        try (FileReader ler = new FileReader(decodificar)) {
            Contador(ler);

            /*
             * System.out.println("\n--- Tabela de Frequência de Caracteres ---");
             * for (Map.Entry<Character, Integer> entrada : mapa.entrySet()) {
             * char caractere = entrada.getKey();
             * int frequencia = entrada.getValue();
             * if (caractere == '\n') {
             * System.out.println("Caractere: '\\n' (Quebra de Linha) | Frequência: " +
             * frequencia);
             * } else if (caractere == ' ') {
             * System.out.println("Caractere: ' ' (Espaço) | Frequência: " + frequencia);
             * } else if (caractere == '\r') {
             * System.out.println("Caractere: '\\r' (Quebra de Linha) | Frequência: " +
             * frequencia);
             * 
             * } else {
             * System.out.println("Caractere: '" + caractere + "' | Frequência: " +
             * frequencia);
             * }
             * }
             * System.out.println("-----------------------------------------\n");
             */

        } catch (Exception e) {
            System.out.println("log de erro: ");
            e.printStackTrace();
        }
    }

    public void Contador(FileReader ler) throws Exception {
        int valorLido = ler.read();

        if (valorLido == -1) {
            return;
        }

        char caractere = (char) valorLido;

        if (mapa.containsKey(caractere)) {
            int contagemCaract = mapa.get(caractere);
            mapa.put(caractere, contagemCaract + 1);
        } else {
            mapa.put(caractere, 1);
        }

        Contador(ler);
    }

    /*
     * Rascunhos:
     * public void Leitor() {
     * try (FileReader ler = new FileReader(decodificar)) {
     * int linha;
     * 
     * while ((linha = ler.read()) != -1) {
     * char caractere = (char) linha;
     * 
     * Contador(caractere);
     * }
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * public void Contador(char caractere) {
     * if (mapa.containsKey(caractere)) {
     * mapa.get(caractere);
     * caractere++;
     * Leitor();
     * } else {
     * mapa.put(caractere, 1);
     * Leitor();
     * }
     * }
     */
}