package com.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileOutputStream;

public class Btree {
    private Node raiz;
    private String arquivoModificado = "projeto_huffman/src/main/java/com/example/Huffman_Compressão.txt";
    private String arquivoOriginal = "projeto_huffman/src/main/java/com/example/Huffman_Coding.txt";
    private String arquivoBinario = "projeto_huffman/src/main/java/com/example/Huffman_Binario.chf";
    private Map<Character, String> mapaDeCodigos = new HashMap<>();
    private Map<Character, Integer> salvarFrequencia;

    public Btree(Map<Character, Integer> mapaFrequencia) {
        this.salvarFrequencia = mapaFrequencia;
        if (mapaFrequencia == null || mapaFrequencia.isEmpty()) {
            this.raiz = null;
            return;
        }

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

    // Mapa do código, dicionario do código binário
    public void GerarCodigo() {
        GerarCodigoRecursivo(this.raiz, "");
    }

    private void GerarCodigoRecursivo(Node noAtual, String atualBinario) {
        if (noAtual.getFilhoDir() == null && noAtual.getFilhoEsq() == null) {
            // Armazena o caractere e armazena o valor final dos return
            mapaDeCodigos.put(noAtual.getCaractere(), atualBinario);

        } else {
            if (noAtual.getFilhoDir() != null) {
                // Lógica para retornar o valor 1
                GerarCodigoRecursivo(noAtual.getFilhoDir(), atualBinario + "1");
            }

            if (noAtual.getFilhoEsq() != null) {
                // Lógica para retornar o valor 0
                GerarCodigoRecursivo(noAtual.getFilhoEsq(), atualBinario + "0");
            }
        }
    }

    // Tradução e compressão do texto para código binário
    public void ComprimirArquivo() {
        ComprimirEmArquivo(arquivoOriginal, arquivoModificado);

    }

    private void ComprimirEmArquivo(String arquivoOriginal, String arquivoModificado) {
        try (FileReader leitor = new FileReader(arquivoOriginal);
                FileWriter escritor = new FileWriter(arquivoModificado)) {

            int valorLido = leitor.read();

            while (valorLido != -1) {
                char novaCaractere = (char) valorLido;
                String traducao = mapaDeCodigos.get(novaCaractere);

                escritor.write(traducao);
                valorLido = leitor.read();
            }

            System.out.println("Fim da tradução");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler e traduzir o arquivo");
            e.printStackTrace();
        }
    }

    // Comprimindo os bits
    public void CallCompressaoBit() {
        CompressaoBit(arquivoModificado, arquivoBinario);
    }

    private void CompressaoBit(String arquivoModificado, String arquivoBinario) {
        StringBuilder bufferDebitss = new StringBuilder();

        System.out.println("Lendo e convertendo arquivo final");
        try (FileReader leitor = new FileReader(arquivoModificado)) {
            int valorLido = leitor.read();

            while (valorLido != -1) {
                char caractereAdquirido = (char) valorLido;

                bufferDebitss.append(caractereAdquirido);
                valorLido = leitor.read();
            }
        } catch (IOException e) {
            System.out.println("Erro ao converter os bits durante a execucao");
            e.printStackTrace();
        }

        try (FileOutputStream escritor = new FileOutputStream(arquivoBinario)) {
            int bitsValidosNoUltimoByte = bufferDebitss.length() % 8;

            if (bufferDebitss.length() == 0) {
                bitsValidosNoUltimoByte = 0;
            } else if (bitsValidosNoUltimoByte == 0) {
                bitsValidosNoUltimoByte = 8;
            }

            escritor.write(bitsValidosNoUltimoByte);

            while (bufferDebitss.length() >= 8) {
                String oitoBits = bufferDebitss.substring(0, 8);
                int byteValor = Integer.parseInt(oitoBits, 2);
                escritor.write(byteValor);
                bufferDebitss.delete(0, 8);
            }

            if (bufferDebitss.length() > 0) {
                while (bufferDebitss.length() < 8) {
                    bufferDebitss.append('0');
                }

                String ultimoByteString = bufferDebitss.toString();
                int ultimoByteValor = Integer.parseInt(ultimoByteString, 2);
                escritor.write(ultimoByteValor);
            }

            System.out.println("Escrita dos bytes finalizada.");
        } catch (IOException e) {
            System.out.println("Erro ao escrever o novo arquivo!");
            e.printStackTrace();
        }
    }

    // Teste visual:
    public void exibirArvore() {
        System.out.println("\n--- Árvore de Huffman ---");
        exibirNo(this.raiz, "");
        System.out.println("-------------------------\n");
    }

    private void exibirNo(Node no, String prefixo) {
        if (no == null) {
            return;
        }

        boolean isFolha = (no.getFilhoEsq() == null && no.getFilhoDir() == null);

        System.out.print(prefixo);
        System.out.print(isFolha ? "└── Folha" : "├── Nó Interno");

        System.out.print(" [freq: " + no.getFrequencia());
        if (isFolha) {
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
        exibirNo(no.getFilhoEsq(), prefixo + "│   ");
        exibirNo(no.getFilhoDir(), prefixo + "    ");
    }

}
