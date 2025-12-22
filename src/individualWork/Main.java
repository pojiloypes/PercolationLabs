package individualWork;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.stream.IntStream;

import Utils.ParamReader;
import Utils.PercModel2d;
import Utils.PercModel2dView;
import Utils.Printer;
import Utils.TestsRow;

public class Main {

    public static void Task1(PercModel2d model) {
        model.genPoints();
        PercModel2dView.showModel(model);
    }

    public static void Task2(PercModel2d model) {
        double value = model.pearsonCriterion();

        if (value < 1.0) {
            System.out.println("Распределение равномерное");
        } else {
            System.out.println("Обнаружена неоднородность");
        }
    }

    public static void Task3(PercModel2d model) {
        model.calcClusterIndices();
        PercModel2dView.showModelWithClusters(model);
    }

    public static void Task4(PercModel2d model) {
        var percolationCluster = model.getPercolationCluster();
        System.out.println("Перколяционный кластер содержит " + percolationCluster.size() + " элементов: ");
        for (int clusterId : percolationCluster) {
            System.out.print(clusterId + ", ");
        }
        System.out.println();
    }

    public static void Task5() {
        int L_1 = 80;
        int L_2 = 90;
        int L_3 = 100;
        double pStart = 0.0;
        double pStep = 0.01;
        int testCnt = 10;

        Printer.saveindividualWorkTask5ResultsToTxt(testPercClstrs(L_1, pStart, pStep, testCnt),
                "src/individualWork/results/task5_1.txt");
        Printer.saveindividualWorkTask5ResultsToTxt(testPercClstrs(L_2, pStart, pStep, testCnt),
                "src/individualWork/results/task5_2.txt");
        Printer.saveindividualWorkTask5ResultsToTxt(testPercClstrs(L_3, pStart, pStep, testCnt),
                "src/individualWork/results/task5_3.txt");
    }

    private static List<TestsRow> testPercClstrs(int L, double pStart, double pStep, int testCnt) {
        List<TestsRow> result = new ArrayList<TestsRow>();

        for (double p = pStart; p <= 1.0000001; p += pStep) {
            final double pVal = p;
            AtomicInteger percClstCnt = new AtomicInteger(0);
            DoubleAdder pSum = new DoubleAdder();

            IntStream.range(0, testCnt).parallel().forEach(test -> {
                PercModel2d pm = new PercModel2d(L, pVal);
                pm.genPoints();
                pm.calcClusterIndices();
                var percolationCluster = pm.getPercolationCluster();
                pSum.add(pm.getActualConcentration());
                percClstCnt.addAndGet(percolationCluster.size());
            });

            result.add(new TestsRow(pSum.doubleValue() / testCnt, (double) percClstCnt.get() / testCnt));

            Printer.printProgressBar(pStart, 1, pStep, pVal);
        }

        return result;
    }

    public static void Test() {
        for (double p = 0.1; p < 1.0091; p += 0.01) {
            PercModel2d model = new PercModel2d(200, p);
            model.genPoints();
            PercModel2dView.showModel(model);
            double value = model.pearsonCriterion();
            System.out.printf("p=%.4f -> Pearson criterion: %.4f%n", p, value);
        }
    }

    public static void main(String[] args) {
        int L = ParamReader.readL();
        double p = ParamReader.readP_bond();
        PercModel2d model = new PercModel2d(L, p);
        Task1(model);
        Task2(model);
        Task3(model);
        Task4(model);
        Task5();
    }
}