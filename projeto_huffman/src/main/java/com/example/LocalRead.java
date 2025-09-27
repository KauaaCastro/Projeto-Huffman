package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LocalRead {
    String arquivo = "projeto_huffman/src/main/java/com/example/Huffman_Coding.txt";
    File decodificar = new File(arquivo);
    Map<Character, Integer> mapa = new HashMap<>();

    public String getArquivoOriginalPath() {
        return this.arquivo;
    }

    public void Leitor() {
        try (FileReader ler = new FileReader(decodificar)) {
            Contador(ler);

            String caminhoArquivoTabela = "projeto_huffman/src/main/java/com/example/tabela_frequencia.txt";
            System.out.println("\nSalvando a tabela de frequência formatada em: " + caminhoArquivoTabela);

            try (FileWriter escritor = new FileWriter(caminhoArquivoTabela)) {

                escritor.write("--- Tabela de Frequência de Caracteres ---\n");

                java.util.List<Map.Entry<Character, Integer>> listaOrdenada = new java.util.ArrayList<>(
                        mapa.entrySet());
                listaOrdenada.sort(Map.Entry.comparingByValue());

                for (Map.Entry<Character, Integer> entrada : listaOrdenada) {
                    char caractere = entrada.getKey();
                    int frequencia = entrada.getValue();
                    String linhaParaEscrever;

                    if (caractere == '\n') {
                        linhaParaEscrever = "Caractere: '\\n' (Quebra de Linha) | Frequência: " + frequencia;

                    } else if (caractere == ' ') {
                        linhaParaEscrever = "Caractere: ' ' (Espaço) | Frequência: " + frequencia;

                    } else if (caractere == '\r') {
                        linhaParaEscrever = "Caractere: '\\r' (Retorno de Carro) | Frequência: " + frequencia;

                    } else {
                        linhaParaEscrever = "Caractere: '" + caractere + "' | Frequência: " + frequencia;

                    }

                    escritor.write(linhaParaEscrever + "\n");
                }

                escritor.write("-----------------------------------------\n");
                System.out.println("Tabela de frequência salva com sucesso.");

            } catch (IOException e) {
                System.out.println("Ocorreu um erro ao salvar o arquivo da tabela.");
                e.printStackTrace();
            }

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
}