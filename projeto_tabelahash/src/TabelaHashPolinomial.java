public class TabelaHashPolinomial extends TabelaHash {

    private static final int BASE = 31;
    public TabelaHashPolinomial(int capacidade) {
        super(capacidade);
    }

    @Override
    public int funcaoHash(String chave) {
        long hash = 0;

        for (int i = 0; i < chave.length(); i++) {
            hash = hash * BASE + chave.charAt(i);


            hash = hash % capacidade;
        }

        return (int) Math.abs(hash);
    }

    @Override
    public String getNomeFuncao() {
        return "Hash Polinomial (base 31, método de Horner)";
    }
}
