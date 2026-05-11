package model;

/**
 * FUNÇÃO HASH 1: Soma dos valores ASCII dos caracteres
 *
 * Como funciona:
 * → Pega cada letra do nome e soma o seu valor numérico (código ASCII).
 * → Divide essa soma pelo tamanho da tabela (32) e pega o resto (módulo).
 * → O resultado é o índice onde o nome será armazenado.
 *
 * Exemplo com "Ana":
 * → 'A' = 65, 'n' = 110, 'a' = 97
 * → Soma = 65 + 110 + 97 = 272
 * → 272 % 32 = 16 → nome vai para a posição 16
 *
 * Problema desta função: nomes com as mesmas letras (anagramas) caem no mesmo índice.
 * Ex: "Ana" e "Naa" teriam o mesmo hash → mais colisões.
 */
public class TabelaHashSomaASCII extends TabelaHash {

    public TabelaHashSomaASCII() {
        super("Hash 1 - Soma ASCII");
    }

    /**
     * Calcula o índice somando o valor ASCII de cada caractere.
     */
    @Override
    public int funcaoHash(String chave) {
        int soma = 0;

        // Percorre cada caractere da string e soma seu valor numérico
        for (int i = 0; i < chave.length(); i++) {
            soma += chave.charAt(i); // charAt(i) retorna o char; Java converte para int automaticamente
        }

        // Módulo garante que o índice fique entre 0 e 31
        return soma % CAPACIDADE;
    }
}
