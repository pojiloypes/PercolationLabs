package lab3;

import java.util.HashSet;
import java.util.Set;

import _CustomDataStructures.DisjointSet;
import lab1.KnotPercolationModel;

public class ClusterModel {

    /**
     * Вычисление кластеров на основе узловой сетки
     * 
     * @param L        размер сетки
     * @param knotGrid узловая сетка
     * @return сетка кластеров
     */
    public static int[][] calculateClusters(int L, int[][] knotGrid) {
        int clusterId = 1;
        int knotsCnt = KnotPercolationModel.getCountOfKnots(knotGrid, L);
        int[][] clusterGrid = new int[L][L];
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

        return markClusters(L, clusterGrid, ds);
    }

    /**
     * Печать сетки кластеров
     * 
     * @param clusterGrid сетка кластеров
     * @param L           размер сетки
     */
    public static void printClusterGrid(int[][] clusterGrid, int L) {
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
     * Пометка кластеров в сетке
     * 
     * @param L           размер сетки
     * @param clusterGrid сетка кластеров
     * @param ds          структура непересекающихся множеств
     * @return помеченная сетка кластеров
     */
    private static int[][] markClusters(int L, int[][] clusterGrid, DisjointSet ds) {
        int[][] markedGrid = new int[L][L];

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                if (clusterGrid[i][j] != 0)
                    markedGrid[i][j] = ds.find(clusterGrid[i][j]);
            }
        }

        return markedGrid;
    }

    /**
     * Проверка наличия вертикального перколяционного кластера
     * @param L размер сетки
     * @param clusterGrid сетка кластеров
     * @return true - если кластер есть, иначе false
     */
    public static boolean hasVerticalPercolationCluster(int L, int[][] clusterGrid) {
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
     * @param L размер сетки
     * @param clusterGrid сетка кластеров
     * @return true - если кластер есть, иначе false
     */
    public static boolean hasHorizaontalPercolationCluster(int L, int[][] clusterGrid) {
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
}