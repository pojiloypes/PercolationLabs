package lab1;

import Utils.ParamReader;

public class Main {

    /*
     * Задание 1. Моделирование узловой сетки
     */
    public static void Task1() {
        System.out.println("Задание 1. Моделирование узловой сетки");

        int L = ParamReader.readL();
        double p = ParamReader.readP_bond();
        int testsCount = ParamReader.readTestsCount();

        KnotPercolationModel.gridModeling(L, p);
        KnotPercolationModel.testModel(L, p, testsCount);
    }

    /*
     * Задание 2. Вычисление критерия Пирсона
     */
    public static void Task2() {
        System.out.println("\nЗадание 2. Вычисление критерия Пирсона");

        int L = ParamReader.readL();
        double p = ParamReader.readP_bond();

        System.out.println("Хи^2 для 1 испытания: " + KnotPercolationModel.getPirson(L, p, 1));
        System.out.println("Хи^2 для 100 испытания: " + KnotPercolationModel.getPirson(L, p, 100));
    }

    /*
     * Задание 2. Вычисление критерия Пирсона для большого количества испытаний
     */
    public static void Task2BigTest() {
        System.out.println("\nЗадание 2. Вычисление критерия Пирсона для большого количества испытаний");
        int L = 1000;
        for (float p = 0.1f; p < 1; p += 0.1) {
            System.out.println("\n Результаты для p = " + p);
            System.out.println("Хи^2 для 1 испытания: " + KnotPercolationModel.getPirson(L, p, 1));
            System.out.println("Хи^2 для 100 испытания: " + KnotPercolationModel.getPirson(L, p, 100));
        }
    }

    public static void main(String[] args) {
        Task1();
        Task2();
        Task2BigTest();
    }
}
