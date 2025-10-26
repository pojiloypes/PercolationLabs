package lab2;

import java.util.Random;
import _CustomDataStructures.*;
import java.util.ArrayList;
import java.util.List;

public class PercolationModel2D {
    private static Random rand = new Random();

   /**
     * Проверяет, находится ли вся окружность строго внутри границ [0, L] x [0, L].
     * @param p центр окружности
     * @param L размер области
     * @param r радиус окружности
     * @return true, если окружность полностью внутри
     */
    private static boolean isInsideOpenBoundary(Pair<Double> p, int L, double r) {
        return (p.getX() - r >= 0) && (p.getX() + r <= L) &&
               (p.getY() - r >= 0) && (p.getY() + r <= L);
    }

    /**
     * Проверка пересечения для открытых границ.
     */
    public static boolean hasIntersectionOpen(List<Pair<Double>> points, Pair<Double> p, double r) {
        for (Pair<Double> other : points) {
            double dx = p.getX() - other.getX();
            double dy = p.getY() - other.getY();
            if (Math.hypot(dx, dy) < 2 * r) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверка пересечения для периодических границ.
     */
    public static boolean hasIntersectionPeriodic(List<Pair<Double>> points, Pair<Double> p, double r, int L) {
        for (Pair<Double> other : points) {
            double dx = Math.abs(p.getX() - other.getX());
            double dy = Math.abs(p.getY() - other.getY());

            if (dx > L / 2.0) dx = L - dx;
            if (dy > L / 2.0) dy = L - dy;

            if (Math.hypot(dx, dy) < 2 * r) {
                return true;
            }
        }
        return false;
    }

    /**
     * Генерация точек с учетом типа границ.
     */
    public static List<Pair<Double>> generatePoints(int L, double p, int r, BoundaryType boundaryType) {
        double areaTotal = L * L;
        double areaCircle = Math.PI * r * r;
        int targetCount = (int) Math.round(p * areaTotal / areaCircle);

        List<Pair<Double>> points = new ArrayList<>();
        int maxFails = targetCount * 100;
        int fails = 0;

        while (points.size() < targetCount && fails < maxFails) {
            Pair<Double> candidate;

            if (boundaryType == BoundaryType.OPEN) {
                double x = rand.nextDouble() * L;
                double y = rand.nextDouble() * L;
                candidate = new Pair<>(x, y);

                if (isInsideOpenBoundary(candidate, L, r) && !hasIntersectionOpen(points, candidate, r)) {
                    points.add(candidate);
                    fails = 0;
                } else {
                    fails++;
                }
            } else { // PERIODIC
                double x = rand.nextDouble() * L;
                double y = rand.nextDouble() * L;
                candidate = new Pair<>(x, y);
                
                if (!hasIntersectionPeriodic(points, candidate, r, L)) {
                    points.add(candidate);
                    fails = 0;
                } else {
                    fails++;
                }
            }
        }

        if (fails >= maxFails) {
            System.out.println("Внимание: достигнут лимит попыток. Не удалось разместить все окружности.");
        }

        return points;
    }

    /**
     * Вывод точек в консоль
     */
    public static void printPoints(List<Pair<Double>> points) {
        for (var point : points) {
            System.out.println("Point: (" + point.getX() + ", " + point.getY() + ")");
        }
    }

    /**
     * Отрисовка точек
     */
    public static void drawPoints(List<Pair<Double>> points, int L, int r, BoundaryType boundaryType) {
       ScaledCirclesView.showWindow(points, L, r, boundaryType);
    }
}