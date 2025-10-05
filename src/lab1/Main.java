package lab1;

public class Main {

    /*
     * Задание 1. Моделирование узловой сетки
     */
    public static void task1(KnotPercolationModel model) {
        int L;
        double p;
        int testsCount;
        System.out.print("Введите размер сетки L: ");
        L = Integer.parseInt(System.console().readLine());
        System.out.print("Введите концентрацию p (0 .. 1): ");   
        p = Double.parseDouble(System.console().readLine());
        System.out.print("Введите количество тестов: ");
        testsCount = Integer.parseInt(System.console().readLine());

        model.gridModeling(L, p);
        model.testModel(L, p, testsCount);
    }

    /*
     * Задание 2. Вычисление критерия Пирсона
     */
    public static void task2(KnotPercolationModel model) {
        int L;
        double p;
        System.out.print("Введите размер сетки L: ");
        L = Integer.parseInt(System.console().readLine());
        System.out.print("Введите концентрацию p (0 .. 1): ");   
        p = Double.parseDouble(System.console().readLine());

        System.out.println("Хи^2 для 1 испытания: " + model.getPirson(L, p, 1));
        System.out.println("Хи^2 для 100 испытания: " + model.getPirson(L, p, 100));
    }

    /*
     * Задание 2. Вычисление критерия Пирсона для большого количества испытаний
     */
    public static void task2BigTest(KnotPercolationModel model) {
       int L = 1000;
       for(float p=0.1f; p < 1; p+=0.1) {
        System.out.println("\n Результаты для p = " + p);
        System.out.println("Хи^2 для 1 испытания: " + model.getPirson(L, p, 1));
        System.out.println("Хи^2 для 100 испытания: " + model.getPirson(L, p, 100));
       }
    }

    public static void main(String[] args) {
        KnotPercolationModel model = new KnotPercolationModel();
        task1(model);
        // task2(model);
        // task2BigTest(model);
    }
}

