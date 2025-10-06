package lab1;

import java.util.Random;

public class KnotPercolationModel {
    private static Random rand = new Random();

    /**
     * Генерация узловой сетки
     * @param L размер сетки
     * @param p концентрация узлов
     * @return узловая сетка
     */
    public static  int[][] generateGrid(int L, double p) {
        int[][] grid = new int[L][L];
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                grid[i][j] = rand.nextDouble() < p ? 1 : 0;
            }
        }
        return grid;
    }

    /**
     * Вычисление фактической концентрации узлов
     * @param grid узловая сетка
     * @param L размер сетки
     * @return фактическая концентрация узлов
     */
    public static double calcConcentration(int[][] grid, int L) {
        int occupiedCount = 0;
        int totalCount = L*L;

        for (int i=0; i<L; i++) {
            for (int j=0; j<L; j++) {
                if (grid[i][j] == 1) {
                    occupiedCount++;
                }
            }
        }

        return (double) occupiedCount / totalCount;
    }

    /**
     * Вывод узловой сетки в консоль
     * @param grid узловая сетка
     * @param L размер сетки
     */
    public static void printGrid(int[][] grid, int L) {
        for (int i=0; i<L; i++) {
            for (int j=0; j<L; j++) {
                if (grid[i][j] == 1) {
                    System.out.print("■ ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Моделирование узловой сетки
     * @param L размер сетки
     * @param p концентрация узлов
     * @return узловая сетка
     */
    static int[][] gridModeling(int L, double p) {
        int[][] grid = generateGrid(L, p);
        System.out.println("Фактическое начение концентрации: " + calcConcentration(grid, L));
        printGrid(grid, L);
        return grid;
    }

    /**
     * Тестирование модели
     * @param L размер сетки
     * @param p концентрация узлов
     * @param testsCount количество тестов
     */
    public static void testModel(int L, double p, int testsCount) {
        double totalConcentration = 0.0;

        for (int i=0; i<testsCount; i++) {
            System.out.println("\nТест №" + (i+1));
            int[][] grid = gridModeling(L, p);
            totalConcentration += calcConcentration(grid, L);
        }

        double averageConcentration = totalConcentration / testsCount;
        System.out.println("\nСредняя фактическое концентрация: " + averageConcentration);
    }

    /**
     * Формула Скотта для оценки оптимального размера блока
     * @param n число элементов
     * @param d число измерений
     * @return оптимальный размер блока
     */
    static int scott(int n, int d) {
        return (int) Math.pow(Math.pow(2*n/3, 1/(d*3)) + 1, d);
    }

    /**
     * Вычисление критерия Пирсона
     * @param L размер сетки
     * @param p концентрация узлов
     * @param testsCount количество тестов
     * @return значение критерия Пирсона
     */
    public static double getPirson(int L, double p, int testsCount) {
        int k = scott(L*L, 2);
        int b = (int)Math.sqrt(L*L/k);
        double xiCommon = 0.0;

        for(int test=0; test<testsCount; test++) {
            int[][] grid = generateGrid(L, p);
            double xi = 0.0;
            for(int i=0; i<L; i+=b) {
                for(int j=0; j<L; j+=b) {
                    int n_i = 0;
                    double E_i = Math.min(L-i, b) * Math.min(L-j, b) * p;
                    for(int bi=i; bi < i+b && bi<L; bi++) {
                        for(int bj=j; bj < j+b && bj<L; bj++) {
                            if (grid[bi][bj] == 1) {
                                n_i++;
                            }
                        }
                    }
                    xi += Math.pow(n_i - E_i, 2) / E_i;
                }
            }
            xiCommon += xi;
        }
        return xiCommon / testsCount;
    }

    /**
     * Подсчет количества узлов в сетке
     * @param grid узловая сетка
     * @param L размер сетки
     * @return количество узлов
     */
    public static int getCountOfKnots(int[][] grid, int L) {
        int count = 0;
        for (int i=0; i<L; i++) {
            for (int j=0; j<L; j++) {
                if (grid[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }
}

