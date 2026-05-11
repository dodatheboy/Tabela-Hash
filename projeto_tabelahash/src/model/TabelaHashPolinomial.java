package model;

/**
 * FUNÇÃO HASH 2: Hash Polinomial (baseada na função djb2)
 *
 * Como funciona:
 * → A cada caractere, o hash acumulado é multiplicado por uma constante (31)
 *    e somado ao valor ASCII do caractere atual.
 * → Usa Math.abs() para garantir que o valor seja positivo antes do módulo.
 *
 * Exemplo com "Ana":
 * → hash = 0
 * → hash = 0  * 31 + 'A'(65)  = 65
 * → hash = 65 * 31 + 'n'(110) = 2125
 * → hash = 2125 * 31 + 'a'(97)= 65972
 * → Math.abs(65972) % 32 = 4 → nome vai para a posição 4
 *
 * Vantagem: considera a POSIÇÃO de cada letra, então "Ana" e "Naa" geram índices diferentes.
 * Isso distribui melhor os nomes e reduz colisões comparado à Soma ASCII.
 *
 * Por que multiplicar por 31?
 * → É um número primo, o que ajuda a espalhar melhor os valores.
 * → É a mesma constante usada pelo String.hashCode() do Java!
 */
public class TabelaHashPolinomial extends TabelaHash {

    // Constante multiplicadora (primo = melhor distribuição)
    private static final int CONSTANTE = 31;

    public TabelaHashPolinomial() {
        super("Hash 2 - Polinomial (djb2)");
    }

    /**
     * Calcula o índice usando multiplicação polinomial.
     */
    @Override
    public int funcaoHash(String chave) {
        long hash = 0; // long para evitar overflow durante os cálculos

        for (int i = 0; i < chave.length(); i++) {
            hash = hash * CONSTANTE + chave.charAt(i);
        }

        // Math.abs garante valor positivo; % CAPACIDADE limita ao range [0, 31]
        return (int) (Math.abs(hash) % CAPACIDADE);
    }
}
