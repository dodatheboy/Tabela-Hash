public class TabelaHashSoma extends TabelaHash {

    public TabelaHashSoma(int capacidade) {
        super(capacidade);
    }

    @Override
    public int funcaoHash(String chave) {
        int soma = 0;
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i);   // charAt(i) retorna o valor Unicode do caractere
        }
        return soma % capacidade;
    }

    @Override
    public String getNomeFuncao() {
        return "Soma dos Caracteres (soma ASCII % capacidade)";
    }
}
