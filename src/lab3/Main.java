package lab3;

import _CustomDataStructures.ParamReader;

public class Main {

    /**
     * Задание 1. Поиск кластеров в узловой перколяционной модели
     */
    public static void Task1() {
        System.out.println("Задание 1. Поиск кластеров в узловой перколяционной модели");

        int L = ParamReader.readL();
        double p = ParamReader.readP_site();

        int[][] knotGrid = lab1.KnotPercolationModel.generateGrid(L, p);
        System.out.println("Сгенерированная узловая сетка:");
        lab1.KnotPercolationModel.printGrid(knotGrid, L);

        int[][] clusterGrid = ClusterModel.calculateClusters(L, knotGrid);
        System.out.println("Сетка кластеров:");
        ClusterModel.printClusterGrid(clusterGrid, L);
    }

    /**
     * Задание 2. Поиск перколяционных кластеров в узловой перколяционной модели
     */
    public static void Task2() {
        System.out.println("\nЗадание 2. Поиск перколяционных кластеров в узловой перколяционной модели");
        
        int L = ParamReader.readL();
        double p = ParamReader.readP_bond();

        int[][] knotGrid = lab1.KnotPercolationModel.generateGrid(L, p);
        System.out.println("Сгенерированная узловая сетка:");
        lab1.KnotPercolationModel.printGrid(knotGrid, L);
        
        int[][] clusterGrid = ClusterModel.calculateClusters(L, knotGrid);
        System.out.println("Сетка кластеров:");
        ClusterModel.printClusterGrid(clusterGrid, L);

        boolean hasVertical = ClusterModel.hasVerticalPercolationCluster(L, clusterGrid);
        System.out.println("Вертикальный перколяционный кластер: " + (hasVertical ? "есть" : "нет"));

        boolean hasHorizontal = ClusterModel.hasHorizaontalPercolationCluster(L, clusterGrid);
        System.out.println("Горизонтальный перколяционный кластер: " + (hasHorizontal ? "есть" : "нет"));


    }

    public static void main(String[] args) {
        Task1();
        Task2();
    }
}