
public class MinhaLista<T> {

    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }

    private No<T> cabeca;
    private int tamanho;

    public MinhaLista() {
        this.cabeca = null;
        this.tamanho = 0;
    }

    public void adicionar(T dado) {
        No<T> novoNo = new No<>(dado);
        if (cabeca == null) {
            cabeca = novoNo;
        } else {
            No<T> atual = cabeca;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novoNo;
        }
        tamanho++;
    }

    public boolean contem(T dado) {
        No<T> atual = cabeca;
        while (atual != null) {
            if (atual.dado.equals(dado)) {
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    public T obter(int indice) {
        No<T> atual = cabeca;
        int contador = 0;
        while (atual != null) {
            if (contador == indice) {
                return atual.dado;
            }
            atual = atual.proximo;
            contador++;
        }
        throw new IndexOutOfBoundsException("Índice fora dos limites: " + indice);
    }


    public int tamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }
}
