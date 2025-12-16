package lab3;

import Utils.ParamReader;
import Utils.PercolationModel;

public class Main {

    /**
     * Задание 1. Поиск кластеров в узловой перколяционной модели
     */
    public static void Task1() {
        System.out.println("Задание 1. Поиск кластеров в узловой перколяционной модели");

        int L = ParamReader.readL();
        double p = ParamReader.readP_site();

        PercolationModel pm = new PercolationModel(L);

        pm.genKnotGrid(p);
        System.out.println("Сгенерированная узловая сетка:");
        pm.printKnotGrid();

        pm.calculateClusters();
        System.out.println("Сетка кластеров:");
        pm.printClusterGrid();
    }

    /**
     * Задание 2. Поиск перколяционных кластеров в узловой перколяционной модели
     */
    public static void Task2() {
        System.out.println("\nЗадание 2. Поиск перколяционных кластеров в узловой перколяционной модели");
        
        int L = ParamReader.readL();
        double p = ParamReader.readP_bond();

        PercolationModel pm = new PercolationModel(L);

        pm.genKnotGrid(p);
        System.out.println("Сгенерированная узловая сетка:");
        pm.printKnotGrid();
        
        pm.calculateClusters();
        System.out.println("Сетка кластеров:");
        pm.printClusterGrid();

        boolean hasCluster = pm.hasPercolationCluster();
        System.out.println("Перколяционный кластер: " + (hasCluster ? "есть" : "нет"));
    }

    public static void main(String[] args) {
        Task1();
        Task2();
    }
}