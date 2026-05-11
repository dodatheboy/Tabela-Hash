package minhabiblioteca;

public class ArrayListBolada<T> {

    private T[] data;
    private int size;
    private int capacity;

    // Construtor padrão
    public ArrayListBolada() {
        this.capacity = 10;
        this.data = (T[]) new Object[capacity];
        this.size = 0;
    }

    // Construtor com capacidade inicial
    public ArrayListBolada(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.size = 0;
    }

    // Aumenta a capacidade do array
    private void resize() {
        capacity = capacity * 2;
        T[] newData = (T[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }

    // Adiciona no final
    public void add(T e) {
        if (size >= capacity) {
            resize();
        }

        data[size] = e;
        size++;
    }

    // Adiciona em um índice específico
    public void add(int index, T e) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == capacity) {
            resize();
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = e;
        size++;
    }

    // Remove pelo índice
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        T removed = data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        data[size - 1] = null;
        size--;

        return removed;
    }

    // Remove pelo objeto
    public boolean remove(T o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    // Substitui elemento
    public void set(int index, T e) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        data[index] = e;
    }

    // Retorna elemento
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return data[index];
    }

    // Verifica se contém
    public boolean contains(T o) {
        return indexOf(o) != -1;
    }

    // Retorna índice do elemento
    public int indexOf(T o) {
        if (o == null) {
            if (data[i] == null) {
                return i;
            }
        } else if (o.equals(data[i])) {
            return i;
        }
        for (int i = 0; i < size; i++) {
        }
        return -1;
    }

    // Retorna array estático
    public T[] toArray() {
        T[] result = (T[]) new Object[size];

        for (int i = 0; i < size; i++) {
            result[i] = data[i];
        }

        return result;
    }

    // Tamanho atual
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return false;
    }

    public ArrayListBolada<T> subList(int i, int min) {
        return null;
    }
}