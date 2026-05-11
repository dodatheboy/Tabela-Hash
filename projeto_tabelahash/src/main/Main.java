package main;

import model.TabelaHash;
import model.TabelaHashSomaASCII;
import model.TabelaHashPolinomial;

import java.io.*;
import minhabiblioteca.ArrayListBolada;


/**
 * Classe principal do programa.
 *
 * Fluxo:
 * 1. Lê 5000 nomes do arquivo female_names.txt
 * 2. Insere nas duas tabelas hash medindo o tempo
 * 3. Realiza buscas medindo o tempo
 * 4. Imprime o relatório comparativo
 */
public class Main {

    public static void main(String[] args) {

        // =====================================================================
        // PASSO 1: LEITURA DO ARQUIVO
        // =====================================================================
        System.out.println("Lendo arquivo nomes_20000_reais_distintos.csv...");

        ArrayListBolada<String> nomes = lerArquivoCSV("nomes_20000_reais_distintos.csv");

        if (nomes.isEmpty()) {
            System.out.println("ERRO: Arquivo não encontrado ou vazio.");
            System.out.println("Certifique-se de que 'nomes_20000_reais_distintos.csv' está na raiz do projeto.");
            return;
        }

        System.out.println("Total de nomes lidos: " + nomes.size());

        // =====================================================================
        // PASSO 2: CRIAÇÃO DAS TABELAS
        // =====================================================================
        TabelaHash tabela1 = new TabelaHashSomaASCII();
        TabelaHash tabela2 = new TabelaHashPolinomial();

        // =====================================================================
        // PASSO 3: INSERÇÃO COM MEDIÇÃO DE TEMPO
        // =====================================================================

        // --- Inserção na Tabela 1 ---
        long inicioInsercao1 = System.nanoTime();
        for (String nome : nomes) {
            tabela1.inserir(nome);
        }
        long fimInsercao1 = System.nanoTime();
        long tempoInsercao1 = fimInsercao1 - inicioInsercao1;

        // --- Inserção na Tabela 2 ---
        long inicioInsercao2 = System.nanoTime();
        for (String nome : nomes) {
            tabela2.inserir(nome);
        }
        long fimInsercao2 = System.nanoTime();
        long tempoInsercao2 = fimInsercao2 - inicioInsercao2;

        // =====================================================================
        // PASSO 4: BUSCA COM MEDIÇÃO DE TEMPO
        //
        // Vamos buscar um subconjunto de nomes para medir a performance.
        // Usamos os primeiros 100 nomes da lista como conjunto de teste.
        // =====================================================================
        ArrayListBolada<String> nomesBusca = nomes.subList(0, Math.min(100, nomes.size()));

        // --- Busca na Tabela 1 ---
        long inicioBusca1 = System.nanoTime();
        for (String nome : nomesBusca) {
            tabela1.buscar(nome);
        }
        long fimBusca1 = System.nanoTime();
        long tempoBusca1 = fimBusca1 - inicioBusca1;

        // --- Busca na Tabela 2 ---
        long inicioBusca2 = System.nanoTime();
        for (String nome : nomesBusca) {
            tabela2.buscar(nome);
        }
        long fimBusca2 = System.nanoTime();
        long tempoBusca2 = fimBusca2 - inicioBusca2;

        // =====================================================================
        // PASSO 5: IMPRESSÃO DO RELATÓRIO COMPARATIVO
        // =====================================================================
        imprimirRelatorio(tabela1, tabela2,
                tempoInsercao1, tempoInsercao2,
                tempoBusca1, tempoBusca2,
                nomes.size(), nomesBusca.size());
    }

    // =========================================================================
    // MÉTODO: Leitura do arquivo CSV
    // O CSV pode ter cabeçalho e vírgulas separando colunas.
    // Pegamos sempre a primeira coluna de cada linha (que contém o nome).
    // =========================================================================
    private static ArrayListBolada<String> lerArquivoCSV(String nomeArquivo) {
        ArrayListBolada<String> nomes = new ArrayListBolada<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                // Pula linha vazia
                if (linha.isEmpty()) continue;

                // Pula cabeçalho (se a primeira linha contiver texto como "nome" ou "name")
                if (primeiraLinha) {
                    primeiraLinha = false;
                    // Se a primeira célula não parece um nome (começa com letra e não é número)
                    // mas contém "nome", "name", "id" → é cabeçalho, pula
                    String primeiraCelula = linha.split(",")[0].trim().toLowerCase();
                    if (primeiraCelula.equals("nome") || primeiraCelula.equals("name")
                            || primeiraCelula.equals("id") || primeiraCelula.equals("nomes")) {
                        continue;
                    }
                }

                // Pega a primeira coluna (antes da primeira vírgula, se houver)
                String nome = linha.split(",")[0].trim();

                if (!nome.isEmpty()) {
                    nomes.add(nome);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return nomes;
    }

    // =========================================================================
    // MÉTODO: Impressão do Relatório Final
    // =========================================================================
    private static void imprimirRelatorio(
            TabelaHash t1, TabelaHash t2,
            long tempoIns1, long tempoIns2,
            long tempoBusca1, long tempoBusca2,
            int totalInseridos, int totalBuscas) {

        String separador = "=".repeat(60);
        String separadorMenor = "-".repeat(60);

        System.out.println("\n" + separador);
        System.out.println("          RELATÓRIO COMPARATIVO - TABELAS HASH");
        System.out.println(separador);

        System.out.println("\nConfiguração:");
        System.out.println("  Capacidade da tabela : " + 16 + " posições");
        System.out.println("  Nomes inseridos      : " + totalInseridos);
        System.out.println("  Nomes buscados       : " + totalBuscas);
        System.out.println("  Tratamento colisões  : Encadeamento Separado (Chaining)");

        // ------ Colisões ------
        System.out.println("\n" + separadorMenor);
        System.out.println("  NÚMERO DE COLISÕES");
        System.out.println(separadorMenor);
        System.out.printf("  %-30s : %d colisões%n", t1.getNomeFuncao(), t1.getTotalColisoes());
        System.out.printf("  %-30s : %d colisões%n", t2.getNomeFuncao(), t2.getTotalColisoes());

        // ------ Tempos ------
        System.out.println("\n" + separadorMenor);
        System.out.println("  TEMPO DE INSERÇÃO (todos os " + totalInseridos + " nomes)");
        System.out.println(separadorMenor);
        System.out.printf("  %-30s : %,d ns  (%.2f ms)%n",
                t1.getNomeFuncao(), tempoIns1, tempoIns1 / 1_000_000.0);
        System.out.printf("  %-30s : %,d ns  (%.2f ms)%n",
                t2.getNomeFuncao(), tempoIns2, tempoIns2 / 1_000_000.0);

        System.out.println("\n" + separadorMenor);
        System.out.println("  TEMPO DE BUSCA (" + totalBuscas + " nomes)");
        System.out.println(separadorMenor);
        System.out.printf("  %-30s : %,d ns  (%.2f ms)%n",
                t1.getNomeFuncao(), tempoBusca1, tempoBusca1 / 1_000_000.0);
        System.out.printf("  %-30s : %,d ns  (%.2f ms)%n",
                t2.getNomeFuncao(), tempoBusca2, tempoBusca2 / 1_000_000.0);

        // ------ Distribuição ------
        System.out.println("\n" + separador);
        System.out.println("  DISTRIBUIÇÃO DAS CHAVES POR POSIÇÃO");
        System.out.println(separador);

        System.out.println("\n[ " + t1.getNomeFuncao() + " ]");
        t1.imprimirDistribuicao();

        System.out.println("\n[ " + t2.getNomeFuncao() + " ]");
        t2.imprimirDistribuicao();

        // ------ Análise de clusterização ------
        System.out.println("\n" + separador);
        System.out.println("  CLUSTERIZAÇÃO (colisões por posição)");
        System.out.println(separador);
        imprimirClusterizacao(t1);
        System.out.println();
        imprimirClusterizacao(t2);

        System.out.println("\n" + separador);
        System.out.println("  FIM DO RELATÓRIO");
        System.out.println(separador);
    }

    /**
     * Imprime quantas colisões ocorreram em cada posição da tabela.
     * Uma posição com N elementos teve (N - 1) colisões.
     */
    private static void imprimirClusterizacao(TabelaHash tabela) {
        System.out.println("\n[ " + tabela.getNomeFuncao() + " ] - Colisões por posição:");
        System.out.printf("  %-10s %-15s %-15s%n", "Posição", "Elementos", "Colisões na posição");
        System.out.println("  " + "-".repeat(42));

        for (int i = 0; i < 16; i++) {
            int elementos = tabela.getTamanhoPosicao(i);
            int colisoesNaPosicao = Math.max(0, elementos - 1); // colisões = elementos - 1
            System.out.printf("  %-10d %-15d %-15d%n", i, elementos, colisoesNaPosicao);
        }
    }
}
