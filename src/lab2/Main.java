package lab2;

import java.util.List;

import _CustomDataStructures.BoundaryType;
import _CustomDataStructures.Pair;
import _CustomDataStructures.ParamReader;
import _CustomDataStructures.PercolationModel;

public class Main {

    /**
     * Задание 1. Перколяционная модель связей
     */
    public static void Task1() {
        System.out.println("Задание 1. Перколяционная модель связей");

        int L = ParamReader.readL();
        double p_bond = ParamReader.readP_site();

        PercolationModel pm = new PercolationModel(L);

        List<List<Pair<Integer>>> grid = pm.genRibGrid(L, p_bond);
        pm.printConnectionsGrid(grid, L);
    }

    /**
     * Задание 2. Перколяционная модель связей на основе узловой сетки
     */
    public static void Task2() {
        System.out.println("\nЗадание 2. Перколяционная модель связей на основе узловой сетки");

        int L = ParamReader.readL();
        double p_site = ParamReader.readP_bond();
        double p_bond = ParamReader.readP_site();

        PercolationModel pm = new PercolationModel(L);

        pm.genKnotGrid(p_site);
        pm.genRibOnKnotsGrid(p_bond);
        pm.printFullGrid();
    }

    /**
     * Задание 3. Перколяционная модель в 2D с окружностями
     */
    public static void Task3() {
        System.out.println("\nЗадание 3. Перколяционная модель в 2D с окрудностями");
        
        int L = ParamReader.readL();
        double p = ParamReader.readP_bond();
        int r = ParamReader.readR();
        // blurredBoundary больше не запрашивается

        // Выбор типа границ
        System.out.println("Выберите тип границ:");
        System.out.println("1. Открытые (окружности не могут выходить за пределы)");
        System.out.println("2. Периодические (тороидальная поверхность)");
        int boundaryChoice = ParamReader.readInt("Ваш выбор (1 или 2): ");
        
        BoundaryType boundaryType;
        if (boundaryChoice == 2) {
            boundaryType = BoundaryType.PERIODIC;
        } else {
            boundaryType = BoundaryType.OPEN;
        }

        // Обновленные вызовы методов
        var points = PercolationModel2D.generatePoints(L, p, r, boundaryType);
        PercolationModel2D.printPoints(points);
        PercolationModel2D.drawPoints(points, L, r, boundaryType);
    }

    public static void main(String[] args) {
        // Task1();
        // Task2();
        Task3();
    }
}
