package _CustomDataStructures;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PercolationModel {
    int L;
    int[][] knotGrid;
    int[][] ribGrid;
    int[][] clusterGrid;
    double actualConcentration;
    Random rand;

    /**
     * Конструктор модели перколяции
     * @param L
     */
    public PercolationModel(int L) {
        this.L = L;
        this.knotGrid = new int[L][L];
        this.ribGrid = new int[L][L];
        this.clusterGrid = new int[L][L];
        actualConcentration = -1;
        rand = new Random();
    }

    /**
     * Получение размера сетки
     * @return узловая сетка
     */
    public int[][] GetKnotGrid() {
        return knotGrid;
    }

    /**
     * Получение сетки связей
     * @return сетка связей
     */
    public int[][] GetRibGrid() {
        return ribGrid;
    }

    /**
     * Получение сетки кластеров
     * @return сетка кластеров
     */
    public int[][] GetClusterGrid() {
        return clusterGrid;
    }

    /**
     * Получение фактической концентрации
     * @return фактическая концентрация
     */
    public double GetActualConcentration() {
        if (actualConcentration == -1) {
            calcConcentration();
        }
        return actualConcentration;
    }

    /**
     * Генерация узловой сетки
     * @param p концентрация узлов
     */
    public void genKnotGrid(double p) {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                this.knotGrid[i][j] = rand.nextDouble() < p ? 1 : 0;
            }
        }
    }

    /**
     * Вычисление фактической концентрации
     * @return фактическая концентрация
     */
    private void calcConcentration() {
        int occupiedCount = 0;
        int totalCount = L*L;

        for (int i=0; i<L; i++) {
            for (int j=0; j<L; j++) {
                if (knotGrid[i][j] == 1) {
                    occupiedCount++;
                }
            }
        }

        actualConcentration =  occupiedCount / totalCount;
    }

    /**
     * Вывод узловой сетки в консоль
     */
    public void printKnotGrid() {
        for (int i=0; i<L; i++) {
            for (int j=0; j<L; j++) {
                if (knotGrid[i][j] == 1) {
                    System.out.print("■ ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Генерация сетки связей
     * @param L размер сетки
     * @param p_bond концентрация связей
     * @return сетка связей
     */
    public int[][] genRibGrid(int L, double p_bond) {
        int[][] ribGrid = new int[L][L];
        for (int i = 0; i < L - 1; i++) {
            for (int j = 0; j < L - 1; j++) {
                ribGrid[i][j + 1] = rand.nextDouble() < p_bond ? 1 : 0;
                ribGrid[i + 1][j] = rand.nextDouble() < p_bond ? 1 : 0;
            }
        }

        return ribGrid;
    }

    /**
     * Вывод сетки связей в консоль
     * @param grid сетка связей
     * @param L размер сетки
     */
    public void printConnectionsGrid(int[][] grid, int L) {

        for (int i = 0; i < L; i++) {
            String curLine = "", underLine = "";
            for (int j = 0; j < L; j++) {
                curLine += j < L - 1 && grid[i][j + 1] == 1 ? "■---" : "■   ";
                underLine += i < L - 1 && grid[i + 1][j] == 1 ? "|   " : "    ";
            }
            System.out.println(curLine + "\n" + underLine);
        }
    }

    /**
     * Генерация сетки связей на основе узловой сетки
     * @param p_bond концентрация связей
     */
    public void genRibOnKnotsGrid(double p_bond) {
        for (int i = 0; i < L - 1; i++) {
            for (int j = 0; j < L - 1; j++) {
                if (knotGrid[i][j] == 1) {
                    ribGrid[i][j + 1] = knotGrid[i][j + 1] == 1 && rand.nextDouble() < p_bond ? 1 : 0;
                    ribGrid[i + 1][j] = knotGrid[i + 1][j] == 1 && rand.nextDouble() < p_bond ? 1 : 0;
                }
            }
        }
    }

    /**
     * Вывод полной сетки (узлы + связи)
     */
    public void printFullGrid() {
        for (int i = 0; i < L; i++) {
            String curLine = "", underLine = "";
            for (int j = 0; j < L; j++) {
                if (knotGrid[i][j] == 1) {
                    curLine += j < L - 1 && ribGrid[i][j + 1] == 1 ? "■---" : "■   ";
                    underLine += i < L - 1 && ribGrid[i + 1][j] == 1 ? "|   " : "    ";
                } else {
                    curLine += "    ";
                    underLine += "    ";
                }

            }
            System.out.println(curLine + "\n" + underLine);
        }
    }

    /**
     * Вычисление кластеров на узловой сетке
     */
    public void calculateClusters() {
        int clusterId = 1;
        int knotsCnt = getCountOfKnots();
        DisjointSet ds = new DisjointSet(knotsCnt + 1);

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (knotGrid[i][j] == 1) {
                    clusterGrid[i][j] = clusterId++;
                    if (i > 0 && knotGrid[i - 1][j] == 1) {
                        ds.union(clusterGrid[i][j], clusterGrid[i - 1][j]);
                    }
                    if (j > 0 && knotGrid[i][j - 1] == 1) {
                        ds.union(clusterGrid[i][j], clusterGrid[i][j - 1]);
                    }
                }

            }
        }

        markClusters(ds);
    }

    /**
     * Подсчет количества узлов в сетке
     * @return количество узлов
     */
    private int getCountOfKnots() {
        int count = 0;
        for (int i=0; i<L; i++) {
            for (int j=0; j<L; j++) {
                if (knotGrid[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Маркировка кластеров в сетке
     * @param ds множество непересекающихся множеств
     */
    private void markClusters(DisjointSet ds) {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (clusterGrid[i][j] != 0) {
                    clusterGrid[i][j] = ds.find(clusterGrid[i][j]);
                }
            }
        }
    }

    /**
     * Вывод сетки кластеров в консоль
     */
    public void printClusterGrid() {
        int maxLen = 0;
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                int len = String.valueOf(clusterGrid[i][j]).length();
                if (len > maxLen) {
                    maxLen = len;
                }
            }
        }

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (clusterGrid[i][j] != 0) {
                    System.out.printf("%" + (maxLen + 1) + "d", clusterGrid[i][j]);
                } else {
                    System.out.printf("%" + (maxLen + 1) + "s", ".");
                }
            }
            System.out.println();
        }
    }

    /**
     * Проверка наличия вертикального перколяционного кластера
     * @return наличие вертикального перколяционного кластера
     */
    private boolean hasVerticalPercolationCluster() {
        Set<Integer> set = new HashSet<>();
        for (int i=0; i<L; i++) {
            if (clusterGrid[0][i] > 0) {
                set.add(clusterGrid[0][i]);
            }
        }

        for (int i=0; i<L; i++) {
            if (set.contains(clusterGrid[L-1][i])) {
                return true;
            }
        }

        return false;
    }

    /**
     * Проверка наличия горизонтального перколяционного кластера
     * @return наличие горизонтального перколяционного кластера
     */
    private boolean hasHorizaontalPercolationCluster() {
        Set<Integer> set = new HashSet<>();
        for (int i=0; i<L; i++) {
            if (clusterGrid[i][0] > 0) {
                set.add(clusterGrid[i][0]);
            } 
        }

        for (int i=0; i<L; i++) {
            if (set.contains(clusterGrid[i][L-1])) {
                return true;
            }
        }

        return false;
    }

    /**
     * Проверка наличия перколяционного кластера
     * @return наличие перколяционного кластера
     */
    public boolean hasPercolationCluster() {
        return hasHorizaontalPercolationCluster() || hasVerticalPercolationCluster();
    }


}
