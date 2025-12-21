package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class PercModel2d {
    int L;
    double p;
    double radius = 0.5;
    double permeableLayer = 0.2;
    List<Pair<Double>> points;
    List<List<Integer>> clusters;

    private Random rand;

    public PercModel2d(int L, double p) {
        this.L = L;
        this.p = p;

        rand = new Random();
    }

    public List<List<Integer>> getClusters() {
        return this.clusters;
    }

    public Double getActualConcentration() {
        if (points == null || points.isEmpty()) {
            return 0.0;
        }

        double areaTotal = L * L;
        double areaCircle = Math.PI * radius * radius;
        double actualConcentration = (points.size() * areaCircle) / areaTotal;

        return actualConcentration;
    }

    public boolean hasIntersection(Pair<Double> newPoint) {
        for (Pair<Double> point : points) {
            double dx = newPoint.getX() - point.getX();
            double dy = newPoint.getY() - point.getY();
            if (Math.hypot(dx, dy) < 2 * radius) {
                return true;
            }
        }
        return false;
    }

    public void genPoints() {
        double areaTotal = L * L;
        double areaCircle = Math.PI * radius * radius;
        int targetCount = (int) Math.round(p * areaTotal / areaCircle);

        points = new ArrayList<>();
        int maxFails = targetCount * 100;
        int fails = 0;

        while (points.size() < targetCount && fails < maxFails) {
            Pair<Double> candidate;

            double x = rand.nextDouble() * L;
            double y = rand.nextDouble() * L;
            candidate = new Pair<>(x, y);

            if (!hasIntersection(candidate)) {
                points.add(candidate);
                fails = 0;
            } else {
                fails++;
            }
        }

        if (fails >= maxFails) {
            //System.out.println("Внимание: достигнут лимит попыток. Не удалось разместить все окружности.");
        }
    }

    public double pearsonCriterion() {
        if (points == null || points.isEmpty()) {
            throw new IllegalStateException("Точки не сгенерированы");
        }

        int n = points.size();

        double sigma = L / Math.sqrt(12.0);
        double h = 3.5 * sigma * Math.pow(n, -1.0 / 3.0);
        int m = (int) Math.floor(L / h);

        if (m < 2)
            m = 2;

        int cells = m * m;
        int[] counts = new int[cells];

        double cellSize = (double) L / m;

        for (Pair<Double> p : points) {
            int ix = (int) (p.getX() / cellSize);
            int iy = (int) (p.getY() / cellSize);

            if (ix >= m)
                ix = m - 1;
            if (iy >= m)
                iy = m - 1;

            counts[iy * m + ix]++;
        }

        double expected = (double) n / cells;
        double chi2 = 0.0;

        for (int i = 0; i < cells; i++) {
            double diff = counts[i] - expected;
            chi2 += diff * diff / expected;
        }

        return chi2 / cells;
    }

    /**
     * Находит кластеры окружностей по пересечению их permeableLayer.
     * Возвращает список кластеров, каждый кластер — список индексов точек в
     * `points`.
     */
    public void calcClusterIndices() {
        if (points == null || points.isEmpty()) {
            this.clusters = new ArrayList<>();
            return;
        }

        int n = points.size();
        DisjointSet ds = new DisjointSet(n);

        double outerR = radius + permeableLayer;
        double thresh = 2.0 * outerR;
        double thresh2 = thresh * thresh;

        for (int i = 0; i < n; i++) {
            Pair<Double> pi = points.get(i);
            double xi = pi.getX();
            double yi = pi.getY();
            for (int j = i + 1; j < n; j++) {
                Pair<Double> pj = points.get(j);
                double dx = xi - pj.getX();
                double dy = yi - pj.getY();
                double d2 = dx * dx + dy * dy;
                if (d2 <= thresh2) {
                    ds.union(i, j);
                }
            }
        }

        Map<Integer, List<Integer>> groups = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = ds.find(i);
            groups.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
        }

        // Сформировать список кластеров в детерминированном порядке:
        // сортируем кластеры по минимальному индексу внутри кластера,
        // чтобы номера кластеров шли 1,2,3...
        List<List<Integer>> list = new ArrayList<>(groups.values());
        list.sort((a, b) -> {
            int ma = a.stream().mapToInt(Integer::intValue).min().orElse(Integer.MAX_VALUE);
            int mb = b.stream().mapToInt(Integer::intValue).min().orElse(Integer.MAX_VALUE);
            return Integer.compare(ma, mb);
        });

        this.clusters = list;
    }

    /**
     * Находит кластеры и возвращает их как списки точек (координат центров).
     */
    public List<List<Pair<Double>>> getClustersPoints() {
        List<List<Integer>> idx = this.clusters;
        List<List<Pair<Double>>> res = new ArrayList<>();
        for (List<Integer> cluster : idx) {
            List<Pair<Double>> c = new ArrayList<>();
            for (int i : cluster)
                c.add(points.get(i));
            res.add(c);
        }
        return res;
    }

    /**
     * Вывод точек в консоль
     */
    public void printPoints() {
        for (var point : points) {
            System.out.println("Point: (" + point.getX() + ", " + point.getY() + ")");
        }
    }

    public List<Integer> getPercolationCluster() {
        List<Integer> res = new ArrayList<>();

        int n = points.size();
        int m = clusters.size();

        int[] pointToCluster = new int[n];
        for (int i = 0; i < n; i++)
            pointToCluster[i] = -1;

        for (int cid = 0; cid < m; cid++) {
            for (int idx : clusters.get(cid)) {
                if (idx >= 0 && idx < n) {
                    pointToCluster[idx] = cid;
                }
            }
        }

        boolean[] hasTop = new boolean[m];
        boolean[] hasBottom = new boolean[m];

        for (int i = 0; i < n; i++) {
            int cid = pointToCluster[i];
            if (cid < 0)
                continue;
            Pair<Double> p = points.get(i);
            if (p.getY() <= radius + permeableLayer)
                hasTop[cid] = true;
            if (p.getY() >= L - (radius + permeableLayer))
                hasBottom[cid] = true;
        }

        for (int cid = 0; cid < m; cid++) {
            if (hasTop[cid] && hasBottom[cid])
                res.add(cid + 1);
        }

        return res;
    }
}
