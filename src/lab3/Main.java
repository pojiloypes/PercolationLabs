package lab3;

public class Main {

    public static void Task1() {
        int L;
        double p;
        System.out.print("Введите размер сетки L: ");
        L = Integer.parseInt(System.console().readLine());
        System.out.print("Введите концентрацию узлов p (0 .. 1): ");   
        p = Double.parseDouble(System.console().readLine());

        int[][] knotGrid = lab1.KnotPercolationModel.generateGrid(L, p);
        System.out.println("Сгенерированная узловая сетка:");
        lab1.KnotPercolationModel.printGrid(knotGrid, L);
        int[][] clusterGrid = ClusterModel.calculateClusters(L, knotGrid);
        System.out.println("Сетка кластеров:");
        ClusterModel.printClusterGrid(clusterGrid, L);
    }

    public static void main(String[] args) {
        Task1();
    }
}