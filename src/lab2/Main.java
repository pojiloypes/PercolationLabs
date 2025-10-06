package lab2;

import lab1.KnotPercolationModel;
import lab2.Task3.PercolationModel2D;

public class Main {

    /**
     * Задание 1. Перколяционная модель связей
     */
    public static void Task1() {
        int L;
        double p_bond;
        System.out.print("Введите размер сетки L: ");
        L = Integer.parseInt(System.console().readLine());
        System.out.print("Введите концентрацию связей p_bond (0 .. 1): ");   
        p_bond = Double.parseDouble(System.console().readLine());

        int[][] grid = RibPercolationModel.generateRibGrid(L, p_bond);
        RibPercolationModel.printConnectionsGrid(grid, L);
    }

    /**
     * Задание 2. Перколяционная модель связей на основе узловой сетки
     */
    public static void Task2() {
        int L;
        double p_site, p_bond;
        System.out.print("Введите размер сетки L: ");
        L = Integer.parseInt(System.console().readLine());
         System.out.print("Введите концентрацию узлов p_bond (0 .. 1): ");   
        p_site = Double.parseDouble(System.console().readLine());
        System.out.print("Введите концентрацию связей p_bond (0 .. 1): ");   
        p_bond = Double.parseDouble(System.console().readLine());

        int[][] grid = KnotPercolationModel.generateGrid(L, p_site);
        int[][] grid2 = RibPercolationModel.generateFullGrid(grid, L, p_bond);
        RibPercolationModel.printFullGrid(grid, grid2, L);
    }

    /**
     * Задание 3. Перколяционная модель в 2D с окрудностями
     */
    public static void Task3() {
        int L;
        double p;
        int r, blurredBoundary = 0;
        System.out.print("Введите размер сетки L: ");
        L = Integer.parseInt(System.console().readLine());
        System.out.print("Введите концентрацию точек p (0 .. 1): ");   
        p = Double.parseDouble(System.console().readLine());
        System.out.print("Введите радиус r: ");   
        r = Integer.parseInt(System.console().readLine());
        System.out.print("Введите размытость границы (целое число, 0 - резкая граница): ");
        blurredBoundary = Integer.parseInt(System.console().readLine());

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
