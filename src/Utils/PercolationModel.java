package Utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class PercolationModel {
    int L;
    int[][] knotGrid;
    List<List<Pair<Integer>>> ribGrid;
    int[][] clusterGrid;
    int clustersCount;
    double actualConcentration;
    Random rand;
    Map<Integer, Integer> clustersSizes;

    /**
     * Конструктор модели перколяции
     * 
     * @param L
     */
    public PercolationModel(int L) {
        this.L = L;
        this.knotGrid = new int[L][L];
        this.ribGrid = initList(L);
        this.clusterGrid = new int[L][L];
        actualConcentration = -1;
        this.clustersCount = 0;
        rand = new Random();
        clustersSizes = new HashMap<>();
    }

    /**
     * Получение размера сетки
     * 
     * @return узловая сетка
     */
    public int[][] GetKnotGrid() {
        return knotGrid;
    }

    /**
     * Получение сетки связей
     * 
     * @return сетка связей
     */
    public List<List<Pair<Integer>>> GetRibGrid() {
        return ribGrid;
    }

    /**
     * Получение сетки кластеров
     * 
     * @return сетка кластеров
     */
    public int[][] GetClusterGrid() {
        return clusterGrid;
    }

    /**
     * Получение фактической концентрации
     * 
     * @return фактическая концентрация
     */
    public double GetActualConcentration() {
        if (actualConcentration == -1) {
            calcConcentration();
        }
        return actualConcentration;
    }

    /**
     * Получение числа кластеров каждого размера
     * @return число кластеров каждого размера
     */
    public Map<Integer, Integer> GetClustersSizes() {
        return clustersSizes;
    }

    /**
     * Генерация узловой сетки
     * 
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
     * 
     * @return фактическая концентрация
     */
    private void calcConcentration() {
        int occupiedCount = 0;
        int totalCount = L * L;

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (knotGrid[i][j] == 1) {
                    occupiedCount++;
                }
            }
        }

        actualConcentration = (double)occupiedCount / totalCount;
    }

    /**
     * Вывод узловой сетки в консоль
     */
    public void printKnotGrid() {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
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
     * 
     * @param L      размер сетки
     * @param p_bond концентрация связей
     * @return сетка связей
     */
    public List<List<Pair<Integer>>> genRibGrid(int L, double p_bond) {
        List<List<Pair<Integer>>> ribGrid = initList(L);

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                int rightNeighbor = j < L - 1 && rand.nextDouble() < p_bond ? 1 : -1;
                int bottomNeighbor = i < L - 1 && rand.nextDouble() < p_bond ? 1 : -1;

                ribGrid.get(i).get(j).setX(rightNeighbor);
                ribGrid.get(i).get(j).setY(bottomNeighbor);
            }
        }

        return ribGrid;
    }

    /**
     * Вывод сетки связей в консоль
     * 
     * @param grid сетка связей
     * @param L    размер сетки
     */
    public void printConnectionsGrid(List<List<Pair<Integer>>> grid, int L) {

        for (int i = 0; i < L; i++) {
            String curLine = "", underLine = "";
            for (int j = 0; j < L; j++) {
                curLine += j < L - 1 && grid.get(i).get(j).getX() == 1 ? "■---" : "■   ";
                underLine += i < L - 1 && grid.get(i).get(j).getY() == 1 ? "|   " : "    ";
            }
            System.out.println(curLine + "\n" + underLine);
        }
    }

    /**
     * Генерация сетки связей на основе узловой сетки
     * 
     * @param p_bond концентрация связей
     */
    public void genRibOnKnotsGrid(double p_bond) {
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (knotGrid[i][j] == 1) {
                    int rightNeighbor = (j < L - 1 && knotGrid[i][j + 1] == 1 && rand.nextDouble() < p_bond) ? 1 : -1;
                    int bottomNeighbor = (i < L - 1 && knotGrid[i + 1][j] == 1 && rand.nextDouble() < p_bond) ? 1 : -1;

                    ribGrid.get(i).get(j).setX(rightNeighbor);
                    ribGrid.get(i).get(j).setY(bottomNeighbor);
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
                    curLine += j < L - 1 && ribGrid.get(i).get(j).getX() == 1 ? "■---" : "■   ";
                    underLine += i < L - 1 && ribGrid.get(i).get(j).getY() == 1 ? "|   " : "    ";
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
        compressClusterIds();
    }

    /**
     * Подсчет количества узлов в сетке
     * 
     * @return количество узлов
     */
    private int getCountOfKnots() {
        int count = 0;
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (knotGrid[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Маркировка кластеров в сетке
     * 
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

    // Сжатие идентификаторов кластеров для упрощения отображения
    private void compressClusterIds() {
        int clusterNum = 1;
        Map<Integer, Integer> clusterIdsMap = new HashMap<>();
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (clusterGrid[i][j] > 0) {
                    if (clusterIdsMap.containsKey(clusterGrid[i][j])) {
                        clusterGrid[i][j] = clusterIdsMap.get(clusterGrid[i][j]);
                    } else {
                        clusterIdsMap.put(clusterGrid[i][j], clusterNum);
                        clusterGrid[i][j] = clusterNum++;
                    }
                    clustersSizes.put(clusterGrid[i][j], clustersSizes.getOrDefault(clusterGrid[i][j], 0) + 1);
                }
            }
        }
        this.clustersCount = clusterNum - 1;
    }

    /**
     * Вывод сетки кластеров в консоль
     */
    public void printClusterGrid() {
        // ANSI-коды для сброса цвета
        final String RESET = "\u001B[0m";

        String[] colors = new String[this.clustersCount + 1];
        for (int i = 1; i <= clustersCount; i++) {
            colors[i] = generateAnsiColor();
        }

        int maxLen = 0;
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                int len = String.valueOf(clusterGrid[i][j]).length();
                if (len > maxLen) {
                    maxLen = len;
                }
            }
        }

        int columnWidth = String.valueOf(maxLen).length() + 1;
        String formatSpecifier = "%" + columnWidth + "d";

        System.out.println("Сетка с метками кластеров:");
        for (int[] row : clusterGrid) {
            for (int val : row) {
                if (val == 0) {
                    System.out.printf("%" + columnWidth + "s ", ".");
                } else {
                    String color = colors[val];
                    System.out.printf("%s" + formatSpecifier + "%s ", color, val, RESET);
                }
            }
            System.out.println();
        }
    }

    /**
     * Генерирует случайный цвет с разнообразными тоном, насыщенностью и яркостью
     * и возвращает его в виде ANSI-кода для терминала.
     * @return Строка с ANSI-кодом цвета.
     */
    private static String generateAnsiColor() {
        java.util.Random rand = new java.util.Random();
        
        // Генерируем случайный тон от 0.0 до 1.0
        float hue = rand.nextFloat();
        
        // Генерируем насыщенность в диапазоне от 0.5 до 1.0
        // Это позволит получать как яркие, так и более пастельные, но все еще насыщенные цвета
        float saturation = 0.5f + rand.nextFloat() * 0.5f;
        
        // Генерируем яркость в диапазоне от 0.6 до 1.0
        // Избегаем слишком темных цветов, которые могут быть плохо видны на темном фоне
        float brightness = 0.6f + rand.nextFloat() * 0.4f;
        
        // Создаем цвет в модели RGB
        java.awt.Color color = java.awt.Color.getHSBColor(hue, saturation, brightness);
        
        // Конвертируем RGB в ANSI-код 24-bit True Color
        return String.format("\u001B[38;2;%d;%d;%dm",
                color.getRed(), color.getGreen(), color.getBlue());
    }


    /**
     * Проверка наличия перколяционного кластера
     * 
     * @return наличие перколяционного кластера
     */
    public boolean hasPercolationCluster() {
        return getPercolationClusters().size() > 0;
    }

    /**
     * Получение списка кластеров перколяции
     * @return список кластеров перколяции
     */
    public List<Integer> getPercolationClusters() {
        List<Integer> lst1 = getVerticalPercolationClusters();
        List<Integer> lst2 = getHorizontalPercolationClusters();
        Set<Integer> unionList = new HashSet<>(lst1);
        unionList.addAll(lst2);

        return new ArrayList<>(unionList);
    }

    /**
     * Получение списка вертикальных кластеров перколяции
     * @return список кластеров перколяции
     */
    private List<Integer> getVerticalPercolationClusters() {
        List<Integer> lst1 = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            if (clusterGrid[i][0] > 0) {
                lst1.add(clusterGrid[0][i]);
            }
        }

        List<Integer> lst2 = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            if (clusterGrid[L-1][i] > 0) {
                lst2.add(clusterGrid[L - 1][i]);
            }
        }

        lst1.retainAll(lst2);

        return lst1;
    }

    /**
     * Получение списка горизонтальных кластеров перколяции
     * @return список кластеров перколяции
     */
    private List<Integer> getHorizontalPercolationClusters() {
        List<Integer> lst1 = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            if (clusterGrid[i][0] > 0) {
                lst1.add(clusterGrid[i][0]);
            }
        }

        List<Integer> lst2 = new ArrayList<>();
        for (int i = 0; i < L; i++) {
            if (clusterGrid[i][L-1] > 0) {
                lst2.add(clusterGrid[i][L-1]);
            }
        }

        lst1.retainAll(lst2);

        return lst1;
    }

    private List<List<Pair<Integer>>> initList(int L) {
        List<List<Pair<Integer>>> list = new ArrayList<>(L);
        for (int i = 0; i < L; i++) {
            List<Pair<Integer>> row = new ArrayList<>(L);
            for (int j = 0; j < L; j++) {
                row.add(new Pair<>(-1, -1));
            }
            list.add(row);
        }
        return list;
    }
}
