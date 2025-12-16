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
        int L = ParamReader.readL();
        double pStart = ParamReader.readPMin();
        double pStep = 0.001;
        int testCnt = ParamReader.readTestsCountWithMin(100);

        int N = L*L;
        
         List<Lab5ResultRow> results = new ArrayList<>();

    for (double p = pStart; p <= 1.0000001; p += pStep) {

        double Ssum = 0.0;      // ⟨S⟩
        double Psum = 0.0;      // ⟨P∞⟩
        double pSum = 0.0;      // ⟨p⟩

        for (int test = 0; test < testCnt; test++) {

            PercolationModel pm = new PercolationModel(L);
            pm.genKnotGrid(p);
            pm.calculateClusters();

            Map<Integer, Integer> clstrsSizes = pm.GetClustersSizes();
            List<Integer> percolationClusters = pm.getPercolationClusters();

            pSum += pm.GetActualConcentration();

            // Ns для одного испытания
            Map<Integer, Integer> Ns = new HashMap<>();

            int N_inf = 0; // число узлов в перколяционном кластере

            for (int cid : clstrsSizes.keySet()) {
                int size = clstrsSizes.get(cid);

                if (percolationClusters.contains(cid)) {
                    N_inf += size; // формула (6)
                } else {
                    Ns.put(size, Ns.getOrDefault(size, 0) + 1);
                }
            }

            // считаем S для одного испытания
            double sum_sn = 0.0;
            double sum_s2n = 0.0;

            for (int s : Ns.keySet()) {
                int ns = Ns.get(s);
                sum_sn += s * ns;
                sum_s2n += s * s * ns;
            }

            double S_i = (sum_sn == 0) ? 0.0 : sum_s2n / sum_sn;
            double P_i = (double) N_inf / N;

            Ssum += S_i;
            Psum += P_i;
        }

        double Savg = Ssum / testCnt;
        double Pavg = Psum / testCnt;
        double pAvg = pSum / testCnt;

        results.add(new Lab5ResultRow(Savg, pAvg, Pavg));
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
