package com.example;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Btree {
    private Node raiz;
    private Map<Character, String> mapaDeCodigos = new HashMap<>();
    private Map<Character, Integer> mapaFrequencia;

    public Btree(Map<Character, Integer> mapaFrequencia) {
        this.mapaFrequencia = mapaFrequencia;

        if (mapaFrequencia == null || mapaFrequencia.isEmpty()) {
            this.raiz = null;
            return;
        }

        Queue<Node> listaPrioritaria = new PriorityQueue<>(Comparator.comparingInt(Node::getFrequencia));

        for (Map.Entry<Character, Integer> entrada : mapaFrequencia.entrySet()) {
            listaPrioritaria.add(new Node(entrada.getValue(), entrada.getKey()));
        }

        while (listaPrioritaria.size() > 1) {
            Node filhoEsq = listaPrioritaria.poll();
            Node filhoDir = listaPrioritaria.poll();
            listaPrioritaria.add(new Node(filhoEsq, filhoDir));
        }

        this.raiz = listaPrioritaria.poll();
    }

    public void comprimir(String arquivoOriginalPath, String arquivoDestinoPath) throws IOException {
        System.out.println("\nIniciando processo de compressão...");

        GerarCodigo();

        StringBuilder bufferDeBits = new StringBuilder();
        try (FileReader leitor = new FileReader(arquivoOriginalPath)) {
            int valorLido;
            while ((valorLido = leitor.read()) != -1) {
                bufferDeBits.append(mapaDeCodigos.get((char) valorLido));
            }
        }

        try (FileOutputStream escritor = new FileOutputStream(arquivoDestinoPath)) {

            StringBuilder cabecalhoTexto = new StringBuilder();
            for (Map.Entry<Character, Integer> entrada : this.mapaFrequencia.entrySet()) {
                if (entrada.getKey() == '\n') {
                    cabecalhoTexto.append("'\\n'").append(":").append(entrada.getValue()).append("\n");
                } else if (entrada.getKey() == '\r') {
                    cabecalhoTexto.append("'\\r'").append(":").append(entrada.getValue()).append("\n");
                } else {
                    cabecalhoTexto.append(entrada.getKey()).append(":").append(entrada.getValue()).append("\n");
                }
            }
            cabecalhoTexto.append("---FIM-CABECALHO---\n");
            escritor.write(cabecalhoTexto.toString().getBytes(StandardCharsets.UTF_8));

            int bitsValidosNoUltimoByte = bufferDeBits.length() % 8;
            if (bufferDeBits.length() > 0 && bitsValidosNoUltimoByte == 0) {
                bitsValidosNoUltimoByte = 8;
            }
            escritor.write(bitsValidosNoUltimoByte);

            while (bufferDeBits.length() >= 8) {
                String oitoBits = bufferDeBits.substring(0, 8);
                int byteValor = Integer.parseInt(oitoBits, 2);
                escritor.write(byteValor);
                bufferDeBits.delete(0, 8);
            }

            if (bufferDeBits.length() > 0) {
                while (bufferDeBits.length() < 8) {
                    bufferDeBits.append('0');
                }
                int ultimoByteValor = Integer.parseInt(bufferDeBits.toString(), 2);
                escritor.write(ultimoByteValor);
            }
            System.out.println("Compressão finalizada com sucesso para: " + arquivoDestinoPath);
        }
    }

    private void GerarCodigo() {
        GerarCodigoRecursivo(this.raiz, "");
    }

    private void GerarCodigoRecursivo(Node noAtual, String atualBinario) {
        if (noAtual == null)
            return;
        if (noAtual.getFilhoEsq() == null && noAtual.getFilhoDir() == null) {
            mapaDeCodigos.put(noAtual.getCaractere(), atualBinario);
        } else {
            GerarCodigoRecursivo(noAtual.getFilhoEsq(), atualBinario + "0");
            GerarCodigoRecursivo(noAtual.getFilhoDir(), atualBinario + "1");
        }
    }
}