package Grafos;

import java.util.*;



import java.util.*; // Importa as classes necessárias para usar listas, pilhas, filas, e outras coleções

public class Grafo_Matriz_Incidencia {
    int[][] matrizAdjacencia; // Matriz de adjacência para representar o grafo
    String[] vertices; // Array de vértices do grafo
    int indiceVertices; // Índice para controlar quantos vértices foram adicionados

    // Construtor que cria um grafo com uma quantidade de vértices especificada
    public Grafo_Matriz_Incidencia(int qtdVertices) {
        this.matrizAdjacencia = new int[qtdVertices][qtdVertices]; // Cria uma matriz quadrada de adjacência'
        this.vertices = new String[qtdVertices]; // Cria o array de vértices
        indiceVertices = 0; // Inicializa o contador de vértices
    }

    // Método para adicionar um novo vértice no grafo
    public void addVertice(String nome) {
        if (indiceVertices < vertices.length) { // Verifica se há espaço para adicionar um novo vértice
            vertices[indiceVertices] = nome; // Adiciona o nome do vértice
            indiceVertices++; // Incrementa o índice de vértices
        }
    }

    // Método para remover o último vértice adicionado
    public void removerVertice() {
        if (indiceVertices > 0) { // Verifica se existe vértices para remover
            vertices[indiceVertices - 1] = ""; // Limpa o nome do último vértice
            indiceVertices--; // Decrementa o contador de vértices
        }
    }

    // Método para adicionar uma aresta bidirecional entre dois vértices
    public void addArestaBidireconais(int iVerticeA, int iVerticeB, int valor) {
        matrizAdjacencia[iVerticeA][iVerticeB] = valor; // Adiciona a aresta entre os vértices A e B
        matrizAdjacencia[iVerticeB][iVerticeA] = valor; // Adiciona a aresta inversa para garantir bidirecionalidade
    }

    // Método para adicionar uma aresta unidirecional de um vértice A para um vértice B
    public void addArestasUnidirecionais(int iVerticeA, int iVerticeB, int valor) {
        matrizAdjacencia[iVerticeA][iVerticeB] = valor; // Adiciona a aresta unidirecional de A para B
    }

    // Método para exibir as relações (arestas) de um vértice específico
    public String relacoesDosVertices(int iVertice) {
        String relacoes = ""; // String para armazenar as relações
        for (int i = 0; i < vertices.length; i++) { // Percorre todos os vértices
            relacoes += "\n Vertice: " + i + "=>"; // Adiciona o índice do vértice à string
            for (int j = 0; j < vertices.length; j++) { // Percorre todas as arestas do vértice
                if (matrizAdjacencia[i][j] != 0) { // Se houver uma aresta (valor diferente de zero)
                    relacoes = relacoes + "Vertice " + j + "(" + matrizAdjacencia[i][j] + ")"; // Exibe o vértice vizinho e o valor da aresta
                }
            }
        }
        return relacoes; // Retorna as relações de um vértice
    }

    // Implementação do método de busca em profundidade (DFS)
    public String buscaProfundidade(int iVerticeInicial, int iVerticeFinal) {
        Stack<Integer> stack = new Stack<>(); // Pilha para armazenar os vértices a serem explorados
        boolean[] visitados = new boolean[vertices.length]; // Vetor para controlar os vértices visitados
        StringBuilder caminho = new StringBuilder(); // StringBuilder para construir o caminho percorrido

        stack.push(iVerticeInicial); // Adiciona o vértice inicial à pilha

        while (!stack.isEmpty()) { // Enquanto houver vértices na pilha
            int atual = stack.pop(); // Retira o vértice da pilha

            if (!visitados[atual]) { // Se o vértice ainda não foi visitado
                visitados[atual] = true; // Marca o vértice como visitado
                caminho.append(atual).append(" => "); // Adiciona o vértice ao caminho

                if (atual == iVerticeFinal) { // Se encontrou o vértice final
                    caminho.append("==FIM=="); // Marca o fim do caminho
                    return caminho.toString(); // Retorna o caminho
                }

                // Adiciona os vizinhos do vértice atual na pilha, em ordem inversa
                for (int i = vertices.length - 1; i >= 0; i--) {
                    if (matrizAdjacencia[atual][i] > 0 && !visitados[i]) { // Se houver aresta e o vértice vizinho não foi visitado
                        stack.push(i); // Empurra o vizinho para a pilha
                    }
                }
            }
        }
        return "Caminho não encontrado."; // Caso não encontre o caminho
    }

    // Implementação do método de busca em largura (BFS)
    public String buscaLargura(int iVerticeInicial, int iVerticeFinal) {
        Queue<Integer> queue = new LinkedList<>(); // Fila para armazenar os vértices a serem explorados
        boolean[] visitados = new boolean[vertices.length]; // Vetor para controlar os vértices visitados
        StringBuilder caminho = new StringBuilder(); // StringBuilder para construir o caminho percorrido

        queue.add(iVerticeInicial); // Adiciona o vértice inicial à fila
        visitados[iVerticeInicial] = true; // Marca o vértice inicial como visitado

        while (!queue.isEmpty()) { // Enquanto houver vértices na fila
            int atual = queue.poll(); // Retira o vértice da fila
            caminho.append(atual).append(" => "); // Adiciona o vértice ao caminho

            if (atual == iVerticeFinal) { // Se encontrou o vértice final
                caminho.append("==FIM=="); // Marca o fim do caminho
                return caminho.toString(); // Retorna o caminho
            }

            // Adiciona os vizinhos não visitados à fila
            for (int i = 0; i < vertices.length; i++) {
                if (matrizAdjacencia[atual][i] > 0 && !visitados[i]) { // Se houver aresta e o vértice vizinho não foi visitado
                    queue.add(i); // Adiciona o vizinho à fila
                    visitados[i] = true; // Marca o vizinho como visitado
                }
            }
        }
        return "Caminho não encontrado."; // Caso não encontre o caminho
    }

    // Implementação do método de busca gulosa (menor custo)
    public String buscaGulosa(int iVerticeInicial, int iVerticeFinal) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); // Fila de prioridade para verificar o menor custo
        boolean[] visitados = new boolean[vertices.length]; // Vetor para controlar os vértices visitados
        StringBuilder caminho = new StringBuilder(); // StringBuilder para construir o caminho percorrido

        queue.add(new int[]{iVerticeInicial, 0}); // Adiciona o vértice inicial à fila de prioridade

        while (!queue.isEmpty()) { // Enquanto houver vértices na fila de prioridade
            int[] atual = queue.poll(); // Retira o vértice da fila
            int verticeAtual = atual[0]; // Obtém o vértice atual

            if (!visitados[verticeAtual]) { // Se o vértice ainda não foi visitado
                visitados[verticeAtual] = true; // Marca o vértice como visitado
                caminho.append(verticeAtual).append(" => "); // Adiciona o vértice ao caminho

                if (verticeAtual == iVerticeFinal) { // Se encontrou o vértice final
                    caminho.append("==FIM=="); // Marca o fim do caminho
                    return caminho.toString(); // Retorna o caminho
                }

                // Adiciona os vizinhos não visitados à fila de prioridade, com base no custo
                for (int i = 0; i < vertices.length; i++) {
                    if (matrizAdjacencia[verticeAtual][i] > 0 && !visitados[i]) {
                        queue.add(new int[]{i, matrizAdjacencia[verticeAtual][i]}); // Adiciona o vizinho com custo
                    }
                }
            }
        }
        return "Caminho não encontrado."; // Caso não encontre o caminho
    }

    // Método principal para testar as funcionalidades do grafo
    public static void main(String[] args) {
        Grafo_Matriz_Incidencia meuGrafo = new Grafo_Matriz_Incidencia(4); // Cria um grafo com 4 vértices

        // Adiciona as arestas ao grafo
        meuGrafo.addArestaBidireconais(0, 2, 10);
        meuGrafo.addArestaBidireconais(0, 3, 5);
        meuGrafo.addArestaBidireconais(0, 1, 2);
        meuGrafo.addArestasUnidirecionais(2, 3, 8);
        meuGrafo.addArestasUnidirecionais(3, 1, 7);

        // Executa os métodos de busca
        System.out.println("Busca em Profundidade:");
        System.out.println(meuGrafo.buscaProfundidade(0, 3)); // Busca em profundidade de 0 a 3

        System.out.println("Busca em Largura:");
        System.out.println(meuGrafo.buscaLargura(0, 3)); // Busca em largura de 0 a 3

        System.out.println("Busca Gulosa:");
        System.out.println(meuGrafo.buscaGulosa(0, 3)); // Busca gulosa de 0 a 3
    }
}
