package lab2;

import java.util.Random;

/*
 * Перколяционная модель связей
 */
public class RibPercolationModel {
    private static Random rand = new Random();

    /**
     * Генерация сетки связей
     * @param L размер сетки
     * @param p_bond концентрация связей
     * @return сетка связей
     */
    public static int[][] generateRibGrid(int L, double p_bond) {
        int[][] grid = new int[L][L];

        for (int i = 0; i < L - 1; i++) {
            for (int j = 0; j < L - 1; j++) {
                grid[i][j + 1] = rand.nextDouble() < p_bond ? 1 : 0;
                grid[i + 1][j] = rand.nextDouble() < p_bond ? 1 : 0;
            }
        }
        return grid;
    }

    /**
     * Генерация полной сетки связей на основе узловой сетки
     * @param knotGrid ущловая сетка
     * @param L размер сетки
     * @param p_bond концентрация связей
     * @return
     */
    public static int[][] generateFullGrid(int[][] knotGrid, int L, double p_bond) {
        int[][] grid = new int[L][L];

        for (int i = 0; i < L - 1; i++) {
            for (int j = 0; j < L - 1; j++) {
                if (knotGrid[i][j] == 1) {
                    grid[i][j + 1] = knotGrid[i][j + 1] == 1 && rand.nextDouble() < p_bond ? 1 : 0;
                    grid[i + 1][j] = knotGrid[i + 1][j] == 1 && rand.nextDouble() < p_bond ? 1 : 0;
                }
            }
        }

        return grid;
    }

    /**
     * Вывод сетки связей в консоль
     * @param grid сетка связей
     * @param L размер сетки
     */
    public static void printConnectionsGrid(int[][] grid, int L) {

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
     * Вывод полной сетки узлов и связей в консоль
     * @param knots узловая сетка
     * @param connections сетка связей
     * @param L размер сетки
     */
    public static void printFullGrid(int[][] knots, int[][] connections, int L) {
        for (int i = 0; i < L; i++) {
            String curLine = "", underLine = "";
            for (int j = 0; j < L; j++) {
                if (knots[i][j] == 1) {
                    curLine += j < L - 1 && connections[i][j + 1] == 1 ? "■---" : "■   ";
                    underLine += i < L - 1 && connections[i + 1][j] == 1 ? "|   " : "    ";
                } else {
                    curLine += "    ";
                    underLine += "    ";
                }

            }
            System.out.println(curLine + "\n" + underLine);
        }
    }
}
