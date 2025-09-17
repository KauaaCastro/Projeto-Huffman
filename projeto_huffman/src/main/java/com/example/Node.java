package com.example;

public class Node {
    private int frequencia;
    private char caractere;
    Node filhoEsq;
    Node filhoDir;

    Node(int frequencia, char caractere) {
        this.caractere = caractere;
        this.frequencia = frequencia;

        this.filhoDir = null;
        this.filhoEsq = null;
    }

    Node(Node filhoEsq, Node filhoDir) {
        this.filhoDir = filhoDir;
        this.filhoEsq = filhoEsq;

        this.frequencia = filhoEsq.frequencia + filhoDir.frequencia;
        this.caractere = '\0';
    }

    public int getFrequencia() {
        return frequencia;
    }

    public char getCaractere() {
        return caractere;
    }

}
