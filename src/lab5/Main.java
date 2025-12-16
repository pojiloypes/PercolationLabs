package lab5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.Lab5ResultRow;
import Utils.ParamReader;
import Utils.PercolationModel;
import Utils.Printer;

public class Main {
    public static void Task1() {
        int L = 10; //ParamReader.readL();
        double pStart = 0.3; //ParamReader.readPMin();
        double pStep = 0.05;
        int testCnt = 100; //ParamReader.readTestsCountWithMin(100);

        int N = L*L;
        
        List<Lab5ResultRow> results = new ArrayList<>();
        for (double p = pStart; p <= 1.0000001; p += pStep) {
            Map<Integer, Integer> commonClstrsSizes = new HashMap<>();
            int percolationClustersCnt = 0;
            double pSum = 0.0;

            for (int test = 0; test < testCnt; test++) {
                PercolationModel pm = new PercolationModel(L);
                pm.genKnotGrid(p);
                pm.calculateClusters();

                Map<Integer, Integer> clstrsSizes = pm.GetClustersSizes();
                List<Integer> percolationClusters = pm.getPercolationClusters();

                pSum += pm.GetActualConcentration();

                for (int key : clstrsSizes.keySet()) {
                    if (!percolationClusters.contains(key)) {
                        Integer val = clstrsSizes.get(key);
                        commonClstrsSizes.put(val, commonClstrsSizes.getOrDefault(val, 0) + 1);
                    } else {
                        percolationClustersCnt++;
                    }
                }
            }

            Map<Integer, Double> commonClstrsSizesPercent = new HashMap<>();
            for (int key : commonClstrsSizes.keySet()) {
                commonClstrsSizesPercent.put(key, (double) commonClstrsSizes.get(key) / testCnt);
            }

            Map<Integer, Double> nsp = new HashMap<>();
            for (int key : commonClstrsSizesPercent.keySet()) {
                nsp.put(key, (double) commonClstrsSizesPercent.get(key) / N);
            }

            Map<Integer, Double> snsp = new HashMap<>();
            for (int key : nsp.keySet()) {
                snsp.put(key, nsp.get(key) * key);
            }

            double cntOfOccupiedKnots = 0;
            for (int key : snsp.keySet()) {
                cntOfOccupiedKnots += snsp.get(key);
            }

            Map<Integer, Double> ws = new HashMap<>();
            for (int key : snsp.keySet()) {
                ws.put(key, (double) snsp.get(key) / cntOfOccupiedKnots);
            }

            double S = 0.0;
            for (int key : ws.keySet()) {
                S += ws.get(key) * key;
            }

            double P = (double)percolationClustersCnt / N;
            double pRes = (double)pSum / testCnt;
            results.add(new Lab5ResultRow(S, pRes, P));
        }

        try {
            Printer.saveLab5ResultsToTxt(results, "src/lab5/results/lab5_results.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Task1();
    }
}
