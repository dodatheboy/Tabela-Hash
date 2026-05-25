
public class Main {

    private static final int CAPACIDADE = 16;

    private static final String CAMINHO_CSV = "nomes_20000_reais_distintos.csv";

    private static final int QUANTIDADE_BUSCAS = 1000;

    public static void main(String[] args) {

        System.out.println("=================================================");
        System.out.println("  TABELA HASH — TDE 03  ");
        System.out.println("=================================================");
        System.out.println("\nLendo arquivo CSV...");

        String[] nomes = LeitorCSV.lerNomes(CAMINHO_CSV);

        System.out.println("Total de nomes lidos: " + nomes.length);

        TabelaHash tabelaSoma        = new TabelaHashSoma(CAPACIDADE);
        TabelaHash tabelaPolinomial  = new TabelaHashPolinomial(CAPACIDADE);

        System.out.println("\nInserindo nomes nas tabelas hash...");

        tabelaSoma.inserirTodos(nomes);
        tabelaPolinomial.inserirTodos(nomes);

        System.out.println("Inserção concluída.");

        int qtdBuscas = Math.min(QUANTIDADE_BUSCAS, nomes.length);
        String[] amostraBusca = new String[qtdBuscas];
        for (int i = 0; i < qtdBuscas; i++) {
            amostraBusca[i] = nomes[i];
        }

        tabelaSoma.buscarTodos(amostraBusca);
        tabelaPolinomial.buscarTodos(amostraBusca);

        System.out.println("Buscas realizadas (" + qtdBuscas + " nomes).");

        System.out.println("\n\n=================================================");
        System.out.println("         RELATÓRIO COMPARATIVO");
        System.out.println("=================================================");

        imprimirRelatorio(tabelaSoma, nomes.length, qtdBuscas);
        imprimirRelatorio(tabelaPolinomial, nomes.length, qtdBuscas);

        System.out.println("\n=================================================");
        System.out.println("           COMPARAÇÃO RESUMIDA");
        System.out.println("=================================================");
        System.out.printf("  %-40s  Colisões: %,d%n",
                tabelaSoma.getNomeFuncao(), tabelaSoma.getTotalColisoes());
        System.out.printf("  %-40s  Colisões: %,d%n",
                tabelaPolinomial.getNomeFuncao(), tabelaPolinomial.getTotalColisoes());

        System.out.println();
        System.out.printf("  Tempo inserção  — Soma:        %.3f ms%n", tabelaSoma.getTempoInsercaoMs());
        System.out.printf("  Tempo inserção  — Polinomial:  %.3f ms%n", tabelaPolinomial.getTempoInsercaoMs());
        System.out.printf("  Tempo busca     — Soma:        %.3f ms%n", tabelaSoma.getTempoBuscaMs());
        System.out.printf("  Tempo busca     — Polinomial:  %.3f ms%n", tabelaPolinomial.getTempoBuscaMs());

        System.out.println("\n=================================================");
        System.out.println("Fim do relatório.");
    }

    private static void imprimirRelatorio(TabelaHash tabela, int totalInseridos, int totalBuscas) {
        System.out.println("\n-------------------------------------------------");
        System.out.println("  FUNÇÃO HASH: " + tabela.getNomeFuncao());
        System.out.println("-------------------------------------------------");
        System.out.println("  Capacidade da tabela : " + tabela.capacidade + " posições");
        System.out.println("  Nomes inseridos      : " + totalInseridos);
        System.out.printf ("  Total de colisões    : %,d%n", tabela.getTotalColisoes());
        System.out.printf ("  Tempo de inserção    : %.3f ms%n", tabela.getTempoInsercaoMs());
        System.out.printf ("  Tempo de busca (%d): %.3f ms%n", totalBuscas, tabela.getTempoBuscaMs());

        System.out.println("\n  --- Distribuição das chaves por posição ---");
        tabela.imprimirDistribuicao();
    }
}
