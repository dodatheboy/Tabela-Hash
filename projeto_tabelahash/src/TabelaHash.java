
public abstract class TabelaHash {

    protected int capacidade;
    protected MinhaLista<String>[] tabela;
    protected int totalColisoes;
    protected long tempoInsercao;
    protected long tempoBusca;

    @SuppressWarnings("unchecked")
    public TabelaHash(int capacidade) {
        this.capacidade = capacidade;
        this.tabela = new MinhaLista[capacidade];
        this.totalColisoes = 0;
        this.tempoInsercao = 0;
        this.tempoBusca = 0;

        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new MinhaLista<>();
        }
    }


    public abstract int funcaoHash(String chave);

    public void inserir(String chave) {
        int posicao = funcaoHash(chave);

        if (!tabela[posicao].estaVazia()) {
            totalColisoes++;
        }

        tabela[posicao].adicionar(chave);
    }

    public boolean buscar(String chave) {
        int posicao = funcaoHash(chave);
        return tabela[posicao].contem(chave);
    }

    public void inserirTodos(String[] chaves) {
        long inicio = System.nanoTime();

        for (String chave : chaves) {
            inserir(chave);
        }

        long fim = System.nanoTime();

        tempoInsercao = fim - inicio;
    }


    public void buscarTodos(String[] chaves) {
        long inicio = System.nanoTime();

        for (String chave : chaves) {
            buscar(chave);
        }

        long fim = System.nanoTime();

        tempoBusca = fim - inicio;
    }


    public int getTotalColisoes() {
        return totalColisoes;
    }


    public double getTempoInsercaoMs() {
        return tempoInsercao / 1_000_000.0;
    }


    public double getTempoBuscaMs() {
        return tempoBusca / 1_000_000.0;
    }


    public void imprimirDistribuicao() {
        System.out.println("  Posição | Qtd. chaves | Colisões nessa posição");
        System.out.println("  --------+-------------+------------------------");
        for (int i = 0; i < capacidade; i++) {
            int qtd = tabela[i].tamanho();
            int colisoesPosicao = qtd > 1 ? qtd - 1 : 0;
            System.out.printf("  [%2d]    |     %5d   |        %5d%n",
                    i, qtd, colisoesPosicao);
        }
    }

 
    public abstract String getNomeFuncao();
}
