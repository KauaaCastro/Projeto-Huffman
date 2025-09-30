package com.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("\033\143");
        LocalRead leitor = new LocalRead();

        System.out.println(">>> Iniciando o teste da classe LocalRead...");
        leitor.Leitor();

        System.out.println(">>> Teste finalizado.");

        if (leitor.mapa != null && !leitor.mapa.isEmpty()) {
            System.out.println("\n>>> Construindo a árvore de Huffman...");
            Btree arvoreHuffman = new Btree(leitor.mapa);
            System.out.println(">>> Árvore construída com sucesso!");

            String arquivoOriginal = leitor.getArquivoOriginalPath();
            String arquivoDestino = "projeto_huffman/src/main/java/com/example/arquivo_codificado.chf";

            arvoreHuffman.comprimir(arquivoOriginal, arquivoDestino);
        }
    }
}