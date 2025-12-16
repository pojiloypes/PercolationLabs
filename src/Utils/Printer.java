package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Printer {
    /**
     * Выводит красивую полоску прогресса в консоль
     * @param current текущий номер теста (от 0)
     * @param total общее количество тестов
     */
    public static void printProgressBar(double pStart, double pEnd, double pStep, double p) {
        int total = (int) ((pEnd - pStart) / pStep) + 1;
        int current = (int) ((p - pStart) / pStep) + 1;

        int barLength = 40; // длина полоски
        double percent = (double) current / total;
        int filled = (int) (barLength * percent);
        StringBuilder bar = new StringBuilder();
        bar.append("Выполнение: [");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) bar.append("█");
            else bar.append("-");
        }
        bar.append("] ");
        bar.append(String.format("%3d%%", (int)(percent * 100)));
        bar.append(" p=" + String.format("%.4f", p));
        System.out.print("\r" + bar);
        if (current == total) System.out.println();
    }

    /**
     * Выводит результаты тестирования в консоль в табличном виде
     * @param result
     */
    public static void printTask1Result(List<TestsRow> result) {
        System.out.println("\n+--------------+-------------+");
        System.out.println("| Концентрация | Вероятность |");
        System.out.println("+--------------+-------------+");
        for (TestsRow row : result) {
            System.out.printf("│ %10.4f   │  %10.4f │\n", row.p, row.pPerc);
        }
        System.out.println("+--------------+-------------+\n");
    }

    /**
     * Сохраняет результаты тестирования в txt файл (без форматирования)
     * @param result список результатов
     * @param filePath путь к файлу
     */
    public static void saveResultsToTxt(List<TestsRow> result, String filePath) {
        java.io.File file = new java.io.File(filePath);
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("Файл создан: " + filePath);
                } else {
                    System.out.println("Не удалось создать файл: " + filePath);
                }
            }
            try (FileWriter writer = new FileWriter(file, false)) {
                // Заголовки для удобного копирования в Excel
                writer.write("Концентрация\tВероятность\n");
                for (TestsRow row : result) {
                    writer.write(String.format("%.6f\t%.6f\n", row.p, row.pPerc));
                }
                System.out.println("\nДанные записаны в файл: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public static void saveLab5ResultsToTxt(List<Lab5ResultRow> result, String filePath) {
        java.io.File file = new java.io.File(filePath);
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("Файл создан: " + filePath);
                } else {
                    System.out.println("Не удалось создать файл: " + filePath);
                }
            }
            try (FileWriter writer = new FileWriter(file, false)) {
                // Заголовки для удобного копирования в Excel
                writer.write("S\t p\t P\n");
                for (Lab5ResultRow row : result) {
                    writer.write(String.format("%.6f\t%.6f\t%.6f\n", row.S, row.p, row.P));
                }
                System.out.println("\nДанные записаны в файл: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
