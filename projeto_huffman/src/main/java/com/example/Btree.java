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
}
