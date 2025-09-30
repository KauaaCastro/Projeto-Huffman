package com.example;

import java.io.File;
import java.io.FileReader;
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