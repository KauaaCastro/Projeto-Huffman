package com.example;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

public class Btree {
    private Node raiz;

    public Btree(Map<Character, Integer> mapaFrequencia) {
        Comparator<Node> comparadorFrequencia = Comparator.comparingInt(Node::getFrequencia);
        Queue<Node> listaPrioritaria = new PriorityQueue<>(comparadorFrequencia);

        for (Entry<Character, Integer> entrada : mapaFrequencia.entrySet()) {
            char atualCaractere = entrada.getKey();
            int atualFrequencia = entrada.getValue();

            Node novaFolha = new Node(atualFrequencia, atualCaractere);

            listaPrioritaria.add(novaFolha);
        }

        while (listaPrioritaria.size() > 1) {
            Node filhoDir = listaPrioritaria.poll();
            Node filhoEsq = listaPrioritaria.poll();

            Node folhaPai = new Node(filhoEsq, filhoDir);
            listaPrioritaria.add(folhaPai);
        }

        this.raiz = listaPrioritaria.poll();
    }

    public Node getRaiz() {
        return this.raiz;
    }

    // Exibindo a arvore binaria:

    public void exibirArvore() {
        System.out.println("\n--- Árvore de Huffman ---");
        // A chamada inicial começa na raiz, com um prefixo vazio.
        exibirNo(this.raiz, "");
        System.out.println("-------------------------\n");
    }

    private void exibirNo(Node no, String prefixo) {
        // 1. Condição de Parada (Caso Base): Se o nó é nulo, chegamos ao fim de um
        // galho.
        if (no == null) {
            return;
        }

        // 2. Verifica se o nó atual é uma folha.
        // Uma folha é um nó que não tem filhos.
        boolean isFolha = (no.getFilhoEsq() == null && no.getFilhoDir() == null);

        // 3. Imprime a informação do nó atual
        System.out.print(prefixo);
        System.out.print(isFolha ? "└── Folha" : "├── Nó Interno");

        System.out.print(" [freq: " + no.getFrequencia());
        // Se for uma folha, imprime também o caractere.
        if (isFolha) {
            // Usamos um switch para imprimir caracteres especiais de forma legível
            switch (no.getCaractere()) {
                case '\n':
                    System.out.print(", char: '\\n'");
                    break;
                case '\r':
                    System.out.print(", char: '\\r'");
                    break;
                case '\t':
                    System.out.print(", char: '\\t'");
                    break;
                default:
                    System.out.print(", char: '" + no.getCaractere() + "'");
                    break;
            }
        }
        System.out.println("]");

        // 4. Chamadas Recursivas para os filhos (a mágica do desenho)
        // Aumentamos o prefixo para os filhos para criar o efeito de "galhos".
        // O uso de "│ " (para o filho da esquerda) e " " (para o da direita)
        // cria as linhas de conexão da árvore.
        exibirNo(no.getFilhoEsq(), prefixo + "│   ");
        exibirNo(no.getFilhoDir(), prefixo + "    ");
    }

}
