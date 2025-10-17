package lab4;

import java.util.ArrayList;
import java.util.List;

import _CustomDataStructures.ParamReader;
import _CustomDataStructures.PercolationModel;

public class Main {

    /**
     * Задание 1: Вычисление вероятности перколяции кластеров
     */
    public static void Task1() {
        System.out.println("Задание 1: Вычисление вероятности перколяции кластеров");

        int L = ParamReader.readL();
        double pStart = ParamReader.readPMin();
        double pStep = ParamReader.readPStep();
        int testCnt = ParamReader.readTestsCountWithMin(100);

        Printer.printTask1Result(testPercClstrs(L, pStart, pStep, testCnt));
    }

    /**
     * Тестирование вероятности перколяции кластеров
     * @param L размер сетки
     * @param pStart начальная концентрация
     * @param pStep шаг изменения концентрации
     * @param testCnt количество тестов на каждую концентрацию
     * @return список результатов тестирования
     */
    private static List<TestsRow> testPercClstrs(int L, double pStart, double pStep, int testCnt) {
        List<TestsRow> result = new ArrayList<TestsRow>();

        for (double p = pStart; p <= 1.0000001; p += pStep) {
            int percClstCnt = 0;
            double pSum = 0;
            for (int test = 0; test < testCnt; test++) {

                PercolationModel pm = new PercolationModel(L);
                pm.genKnotGrid(p);
                pSum += pm.GetActualConcentration();

                pm.GetClusterGrid();

                if (pm.hasPercolationCluster()) {
                    percClstCnt++;
                }
            }
            result.add(new TestsRow(pSum / testCnt, (double) percClstCnt / testCnt));

            Printer.printProgressBar(pStart, 1, pStep, p);
        }

        return result;
    }

    /**
     * Задание 2: Определение порога перколяции кластеров
     */
    public static void Task2() {
        System.out.println("Задание 2: Определение порога перколяции кластеров");

        int[] lList = new int[] { 100, 200, 500 };
        for (int L : lList) {
            System.out.println("\nL = " + L);
            var res = testPercClstrs(L, 0.5, 0.001, 100);
            Printer.saveResultsToTxt(res, "src/lab4/results/results_L" + L + ".txt");
            System.out.println("Порог перколяции = " + getPercTreshhold(res));
        }
    }

    /**
     * Определение порога перколяции по результатам тестирования
     * @param res
     * @return
     */
    public static double getPercTreshhold(List<TestsRow> res) {
        for(TestsRow row : res) {
            if (row.pPerc == 1) {
                return row.p;
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        Task1();
        Task2();
    }
}