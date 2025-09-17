# Implementação do Algoritmo de Huffman em Java

![Status do Projeto](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)

## 📖 Sobre o Projeto

Este projeto é uma implementação acadêmica do clássico algoritmo de compressão de dados de Huffman, desenvolvido inteiramente em Java. O objetivo é criar, passo a passo, uma aplicação capaz de ler um arquivo de texto, comprimi-lo para um formato binário e, subsequentemente, descomprimi-lo para restaurar o texto original.

O algoritmo de Huffman baseia-se no princípio de que caracteres mais frequentes em um texto devem ser representados por códigos binários mais curtos, enquanto caracteres mais raros recebem códigos mais longos. Isso resulta em uma representação final do texto que ocupa menos espaço, efetivamente comprimindo o arquivo.

## ✨ Funcionalidades

-   [✔️] **Leitura de Arquivos:** Capacidade de ler um arquivo de texto (`.txt`) caractere por caractere.
-   [🚧] **Análise de Frequência:** Contagem precisa da frequência de cada caractere único no texto de entrada.
-   [◻️] **Construção da Árvore de Huffman:** Geração da árvore binária a partir da tabela de frequências.
-   [◻️] **Geração de Códigos Binários:** Criação do mapa de códigos de Huffman para cada caractere.
-   [◻️] **Compressão:** Escrita dos dados comprimidos em um novo arquivo binário.
-   [◻️] **Descompressão:** Leitura do arquivo comprimido e restauração do texto original.

*(Legenda: ✔️=Concluído, 🚧=Em Desenvolvimento, ◻️=Planejado)*

## ⚙️ Como Funciona

O processo é dividido nas seguintes etapas lógicas:

1.  **Contagem de Frequência:**
    -   O programa lê o arquivo de texto de entrada caractere por caractere.
    -   Um `HashMap<Character, Integer>` é utilizado para armazenar cada caractere único (a chave) e sua respectiva contagem de frequência (o valor).

2.  **Construção da Árvore de Huffman:**
    -   Cada par (caractere, frequência) é usado para criar um "nó folha".
    -   Todos os nós são inseridos em uma `PriorityQueue` (Fila de Prioridade), que os ordena pela menor frequência.
    -   O algoritmo, então, entra em um loop: remove os dois nós de menor frequência da fila, combina-os em um novo "nó interno" (cuja frequência é a soma dos filhos) e insere este novo nó de volta na fila.
    -   Este processo se repete até que reste apenas um nó na fila: a raiz da árvore completa.

3.  **Geração dos Códigos:**
    -   A árvore é percorrida a partir da raiz. Ao navegar para um filho à esquerda, adiciona-se `0` ao código; ao navegar para a direita, adiciona-se `1`.
    -   O caminho da raiz até cada "nó folha" define o código de Huffman para o caractere contido naquele nó.

4.  **Compressão e Descompressão:**
    -   **Para comprimir:** O texto original é relido, и cada caractere é substituído pelo seu novo código de Huffman. A sequência de bits resultante é escrita em um arquivo.
    -   **Para descomprimir:** O arquivo comprimido é lido bit a bit, e a árvore de Huffman é usada para decodificar a sequência de volta para os caracteres originais.

## 🚀 Como Usar

1.  Clone este repositório para a sua máquina local.
2.  Abra o projeto em sua IDE Java preferida (Eclipse, IntelliJ, VS Code, etc.).
3.  Na classe `LocalRead.java`, altere o valor da variável `arquivo` para o caminho do arquivo `.txt` que você deseja analisar.
    ```java
    String arquivo = "caminho/para/seu/arquivo.txt";
    ```
4.  Execute o método `main` na classe `LocalRead.java` para iniciar o processo de contagem de frequência.
5.  O resultado da análise será exibido no console.

## 🛠️ Estrutura do Projeto (Planejada)

-   `LocalRead.java`: Classe principal que atualmente contém a lógica de leitura e contagem.
-   `Node.java`: (Futuro) Representará um nó na Árvore de Huffman.
-   `Huffman.java`: (Futuro) Orquestrará todo o processo de compressão e descompressão.

## Autores

[Kauã Ricardo de Castro Galvão](https://github.com/KauaaCastro) e [Sofia David de Carvalho](https://github.com/sofiacarvalho2)
