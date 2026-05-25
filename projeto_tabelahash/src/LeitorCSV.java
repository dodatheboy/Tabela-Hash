import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorCSV {

    public static String[] lerNomes(String caminhoArquivo) {
        MinhaLista<String> nomes = new MinhaLista<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = leitor.readLine()) != null) {
                linha = linha.trim();

                if (linha.isEmpty()) continue;

                if (primeiraLinha) {
                    primeiraLinha = false;
                    if (linha.equalsIgnoreCase("nome") || linha.equalsIgnoreCase("name")) {
                        continue; // é cabeçalho, pula
                    }
                }

                if (linha.startsWith("\"") && linha.endsWith("\"")) {
                    linha = linha.substring(1, linha.length() - 1);
                }

                nomes.adicionar(linha);
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            System.err.println("Verifique se o arquivo existe no caminho: " + caminhoArquivo);
            System.exit(1);
        }

        String[] arrayNomes = new String[nomes.tamanho()];
        for (int i = 0; i < nomes.tamanho(); i++) {
            arrayNomes[i] = nomes.obter(i);
        }

        return arrayNomes;
    }
}
