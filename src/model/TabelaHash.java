package model;

import java.util.LinkedList;

/**
 * Classe ABSTRATA que representa uma Tabela Hash genérica.
 *
 * Abstrata significa que ela não pode ser instanciada diretamente.
 * Ela serve como "molde" para as duas tabelas concretas que herdam dela.
 *
 * Tratamento de colisões: Encadeamento Separado (Separate Chaining)
 * → Cada posição da tabela guarda uma LinkedList (lista encadeada).
 * → Se dois nomes caem no mesmo índice, eles são adicionados à mesma lista.
 */
public abstract class TabelaHash {

    // Capacidade máxima da tabela (conforme enunciado: 16 posições)
    protected static final int CAPACIDADE = 16;

    // A tabela em si: um array de listas encadeadas
    // Cada posição pode guardar vários nomes (por isso a lista)
    protected LinkedList<String>[] tabela;

    // Contador de colisões (incrementado sempre que um nome cai numa posição já ocupada)
    protected int totalColisoes;

    // Nome da função hash (usado no relatório)
    protected String nomeFuncao;

    /**
     * Construtor: inicializa o array e cria uma LinkedList vazia em cada posição.
     */
    @SuppressWarnings("unchecked")
    public TabelaHash(String nomeFuncao) {
        this.nomeFuncao = nomeFuncao;
        this.totalColisoes = 0;

        // Cria o array com 32 posições
        tabela = new LinkedList[CAPACIDADE];

        // Inicializa cada posição com uma lista vazia (para evitar NullPointerException)
        for (int i = 0; i < CAPACIDADE; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    /**
     * Método ABSTRATO: cada subclasse vai implementar a sua própria função hash.
     * O método recebe uma chave (nome) e retorna um índice entre 0 e 31.
     */
    public abstract int funcaoHash(String chave);

    /**
     * Insere um nome na tabela hash.
     * 1. Calcula o índice usando a função hash.
     * 2. Se a posição já tiver algum elemento → colisão!
     * 3. Adiciona o nome na lista daquela posição.
     */
    public void inserir(String nome) {
        int indice = funcaoHash(nome);

        // Se já existe pelo menos um elemento nessa posição → é uma colisão
        if (!tabela[indice].isEmpty()) {
            totalColisoes++;
        }

        tabela[indice].add(nome);
    }

    /**
     * Busca um nome na tabela hash.
     * Retorna true se encontrar, false caso contrário.
     */
    public boolean buscar(String nome) {
        int indice = funcaoHash(nome);
        return tabela[indice].contains(nome);
    }

    /**
     * Retorna o total de colisões registradas durante as inserções.
     */
    public int getTotalColisoes() {
        return totalColisoes;
    }

    /**
     * Retorna o nome da função hash (para o relatório).
     */
    public String getNomeFuncao() {
        return nomeFuncao;
    }

    /**
     * Retorna quantos elementos existem em uma posição específica da tabela.
     */
    public int getTamanhoPosicao(int indice) {
        return tabela[indice].size();
    }

    /**
     * Imprime o relatório de distribuição das chaves por posição.
     * Mostra quantos nomes estão em cada posição da tabela.
     */
    public void imprimirDistribuicao() {
        System.out.println("\n--- Distribuição das chaves por posição (capacidade: " + CAPACIDADE + ") ---");
        for (int i = 0; i < CAPACIDADE; i++) {
            int quantidade = tabela[i].size();
            // Barra visual para facilitar a leitura
            String barra = "#".repeat(Math.min(quantidade, 50));
            System.out.printf("Posição %2d | %4d elemento(s) | %s%n", i, quantidade, barra);
        }
    }

    /**
     * Retorna o total de nomes inseridos na tabela.
     */
    public int getTotalElementos() {
        int total = 0;
        for (LinkedList<String> lista : tabela) {
            total += lista.size();
        }
        return total;
    }
}
