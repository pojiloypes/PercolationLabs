package lab2;

import _CustomDataStructures.ParamReader;
import lab1.KnotPercolationModel;

public class Main {

    /**
     * Задание 1. Перколяционная модель связей
     */
    public static void Task1() {
        System.out.println("Задание 1. Перколяционная модель связей");

        int L = ParamReader.readL();
        double p_bond = ParamReader.readP_bond();

        int[][] grid = RibPercolationModel.generateRibGrid(L, p_bond);
        RibPercolationModel.printConnectionsGrid(grid, L);
    }

    /**
     * Задание 2. Перколяционная модель связей на основе узловой сетки
     */
    public static void Task2() {
        System.out.println("\nЗадание 2. Перколяционная модель связей на основе узловой сетки");

        int L = ParamReader.readL();
        double p_site = ParamReader.readP_site();
        double p_bond = ParamReader.readP_bond();

        int[][] grid = KnotPercolationModel.generateGrid(L, p_site);
        int[][] grid2 = RibPercolationModel.generateFullGrid(grid, L, p_bond);
        RibPercolationModel.printFullGrid(grid, grid2, L);
    }

    /**
     * Задание 3. Перколяционная модель в 2D с окрудностями
     */
    public static void Task3() {
        System.out.println("\nЗадание 3. Перколяционная модель в 2D с окрудностями");
        
        int L = ParamReader.readL();
        double p = ParamReader.readP_bond();
        int r = ParamReader.readR();
        int blurredBoundary = ParamReader.readBlurredBoundary();

        var points = PercolationModel2D.generatePoints(L, p, r, blurredBoundary);
        PercolationModel2D.printPoints(points);
        PercolationModel2D.drawPoints(points, L, r, blurredBoundary);
    }

    public static void main(String[] args) {
        Task1();
        Task2();
        Task3();
    }
}
