package com.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("\033\143");
        LocalRead leitor = new LocalRead();

        System.out.println(">>> Iniciando o teste da classe LocalRead...");
        leitor.Leitor();

        System.out.println(">>> Teste finalizado.");

        if (!leitor.mapa.isEmpty()) {
            System.out.println("\n>>> Construindo a árvore de Huffman...");
            Btree arvoreHuffman = new Btree(leitor.mapa);
            System.out.println(">>> Árvore construída com sucesso!");

            // ADICIONE ESTA LINHA PARA EXIBIR A ÁRVORE:
            arvoreHuffman.exibirArvore();
        }
    }
}