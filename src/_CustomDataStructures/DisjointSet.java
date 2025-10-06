package _CustomDataStructures;

/**
 * Структура непересекающихся множеств (Union-Find)
 */
public class DisjointSet {
    private int[] parent;
    private int[] rank;

    /**
     * Инициализация структуры
     * @param n количество элементов
     */
    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i; // каждый элемент — свой родитель
            rank[i] = 0;
        }
    }

    /**
     * Найти корневой элемент множества
     * @param x элемент
     * @return корневой элемент
     */
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    /**
     * Объединить два множества
     * @param x номер первого элемента
     * @param y номер второго элемента
     */
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
