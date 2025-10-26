package _CustomDataStructures;

import java.util.Scanner;

public class ParamReader {
    private static Scanner scanner = new Scanner(System.in);

     public static int readL() {
        return readInt("Введите размер сетки L: ");
    }

    public static double readP_bond() {
        return readDouble("Введите концентрацию узлов p (0 .. 1): ");
    }

    public static double readP_site() {
        return readDouble("Введите концентрацию связей p (0 .. 1): ");
    }

    public static int readR() {
        return readInt("Введите радиус r: ");
    }

    public static int readBlurredBoundary() {
        return readInt("Введите размытость границы (целое число, 0 - резкая граница): ");
    }

    public static int readTestsCount() {
        return readInt("Введите количество тестов: ");
    }

    public static double readPStep() {
        return readDouble("Введите шаг изменения концентрации: ");
    }

    public static double readPMin() {
        return readDouble("Введите минимальное значение концентрации p (0 .. 1): ");
    }

    public static int readTestsCountWithMin(int min) {
        int k = readTestsCount();
        if (k < min) {
            System.out.println("Количество тестов должно быть не меньше 100. Текущее число тестов изменено на " + min);
        }
        return Math.max(k, min);
    }

    /**
     * Чтение целого числа с консоли с обработкой ошибок
     * @param prompt выводимый запрос
     * @return прочитанное int значение
     */
    public static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка ввода. Пожалуйста, введите целое число.");
            scanner.next(); // Очистка некорректного ввода
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    /**
     * Чтение числа с плавающей точкой с консоли с обработкой ошибок
     * @param prompt выводимый запрос
     * @return прочитанное double значение
     */
    public static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Ошибка ввода. Пожалуйста, введите число с плавающей точкой.");
            scanner.next(); // Очистка некорректного ввода
            System.out.print(prompt);
        }
        return scanner.nextDouble();
    }
}