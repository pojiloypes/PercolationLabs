package lab2.Task3;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class PercolationModel2D {
    Random rand = new Random();

    /**
     * Граничное условие: окружность целиком внутри квадрата LxL
     * 
     * @param p центр окружности
     * @param L размерность квадрата
     * @param r радиус
     * @return true, если окружность целиком внутри квадрата
     */
    public boolean boundaryCondition1(Point2D p, int L, int r, int blurredBoundary) {
        return (p.getX() - r >= (0-blurredBoundary)) && (p.getX() + r <= (L+blurredBoundary)) &&
                (p.getY() - r >= (0-blurredBoundary)) && (p.getY() + r <= (L+blurredBoundary));
    }

    /**
     * Проверка пересечения с уже существующими окружностями
     * @param points уже существующие окружности
     * @param p новая окружность
     * @param r радиус окружностей
     * @return true, если пересечение есть
     */
    public boolean hasIntersection(List<Point2D> points, Point2D p, double r) {
        for (Point2D other : points) {
            double dx = p.getX() - other.getX();
            double dy = p.getY() - other.getY();
            if (Math.hypot(dx, dy) < 2 * r) {
                return true;
            }
        }
        return false;
    }

    /**
     * Генерация точек
     * @param L размер сетки
     * @param p концентрация точек
     * @param r радиус точек
     * @param blurredBoundary размытость границы (0 - резкая граница)
     * @return список точек
     */
    public List<Point2D> generatePoints(int L, double p, int r, int blurredBoundary) {
        double areaTotal = L * L;
        double areaCircle = Math.PI * r * r;
        int targetCount = (int) Math.round(p * areaTotal / areaCircle);

        List<Point2D> points = new ArrayList<>();
        Random rand = new Random();
        int maxFails = 100; // ограничение на количество неудачных попыток
        int fails = 0;

        while (points.size() < targetCount && fails < maxFails) {
            double x = blurredBoundary == 0 ? r + rand.nextDouble() * (L - 2 * r) : rand.nextDouble() * L;
            double y = blurredBoundary == 0 ? r + rand.nextDouble() * (L - 2 * r) : rand.nextDouble() * L;
            Point2D candidate = new Point2D(x, y);

            if (!boundaryCondition1(candidate, L, r, blurredBoundary)) {
                fails++;
            } else if (!hasIntersection(points, candidate, r)) {
                points.add(candidate);
                fails = 0;
            } else {
                fails++;
            }
        }

        return points;
    }

    /**
     * Вывод точек в консоль
     * @param points список точек
     */
    public void printPoints(List<Point2D> points) {
        for (var point : points) {
            System.out.println("Point: (" + point.getX() + ", " + point.getY() + ")");
        }
    }

    /**
     * Отрисовка точек
     * @param points список точек
     * @param L размер сетки
     * @param r радиус точек
     * @param blurredBoundary размытость границы (0 - резкая граница)
     */
    public void drawPoints(List<Point2D> points, int L, int r, int blurredBoundary) {
       ScaledCirclesView.showWindow(points, L, blurredBoundary, r);
    }
}