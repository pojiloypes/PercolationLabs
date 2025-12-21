package Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PercModel2dView extends JPanel {
    private PercModel2d model;
    private int padding = 20;
    private boolean clusterMode = false;

    public PercModel2dView(PercModel2d model) {
        this.model = model;
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int size = Math.max(0, Math.min(w, h) - 2 * padding);
            int x0 = (w - size) / 2;
            int y0 = (h - size) / 2;

            // Заполнение и граница квадрата
            g2.setColor(Color.WHITE);
            g2.fillRect(x0, y0, size, size);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2f));
            g2.drawRect(x0, y0, size, size);

            // Ограничить отрисовку внутренней областью квадрата (клиппинг)
            Shape oldClip = g2.getClip();
            g2.setClip(new Rectangle(x0, y0, size, size));

            // Нарисовать окружности: простой режим или по кластерам
            if (clusterMode) {
                // убедиться, что кластеры рассчитаны
                model.calcClusterIndices();
                drawClusters(g2, x0, y0, size);
            } else {
                drawSimple(g2, x0, y0, size);
            }

            // Восстановить предыдущий клип
            g2.setClip(oldClip);
        } finally {
            g2.dispose();
        }
    }

    /**
     * Быстро показать модель в окне Swing (в EDT).
     */
    public static void showModel(PercModel2d model, String title) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame(title == null ? "PercModel2d View" : title);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            PercModel2dView panel = new PercModel2dView(model);
            f.getContentPane().add(panel);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }

    public static void showModel(PercModel2d model) {
        showModel(model, null);
    }

    /**
     * Показать модель и сразу отрисовать в режиме кластеров (в EDT).
     */
    public static void showModelWithClusters(PercModel2d model) {
        SwingUtilities.invokeLater(() -> {
            if (model.points == null) model.genPoints();
            model.calcClusterIndices();
            PercModel2dView panel = new PercModel2dView(model);
            JFrame f = new JFrame("PercModel2d - Clusters");
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.getContentPane().add(panel);
            f.pack();
            f.setLocationRelativeTo(null);
            panel.renderClusters();
            f.setVisible(true);
        });
    }

    /**
     * Отрисовать окружности, раскрашенные по кластерам (использует getClusterIndices()).
     * Если кластеров нет, вернётся на одиночную раскраску.
     */
    private void drawClusters(Graphics2D g2, int x0, int y0, int size) {
        List<Pair<Double>> pts = model.points;
        if (pts == null || size <= 0) return;

        double L = model.L;
        double scale = (double) size / L;
        double r = model.radius;
        double permeable = model.permeableLayer;

        // получить кластеры из модели
        java.util.List<java.util.List<Integer>> clusters = model.getClusters();

        if (clusters == null || clusters.isEmpty()) {
            drawSimple(g2, x0, y0, size);
            return;
        }

        int k = clusters.size();

        // Отрисовка по кластерам: каждой группе свой цвет
        for (int ci = 0; ci < k; ci++) {
            java.util.List<Integer> cluster = clusters.get(ci);
            float hue = (k <= 1) ? 0f : (ci / (float) k);
            Color base = Color.getHSBColor(hue, 0.6f, 0.9f);
            Color permeableColor = new Color(base.getRed(), base.getGreen(), base.getBlue(), 100);
            Color fillColor = new Color(base.getRed(), base.getGreen(), base.getBlue(), 180);
            Color outline = base.darker();

            // permeable layer
            g2.setColor(permeableColor);
            for (int idx : cluster) {
                Pair<Double> p = pts.get(idx);
                double cx = p.getX();
                double cy = p.getY();
                double outerR = r + permeable;
                double outerD = 2.0 * outerR * scale;
                double outerX = x0 + (cx - outerR) * scale;
                double outerY = y0 + (cy - outerR) * scale;
                g2.fillOval((int) Math.round(outerX), (int) Math.round(outerY), (int) Math.round(outerD), (int) Math.round(outerD));
            }

            // central fill
            g2.setColor(fillColor);
            for (int idx : cluster) {
                Pair<Double> p = pts.get(idx);
                double cx = p.getX();
                double cy = p.getY();
                double d = 2.0 * r * scale;
                double px = x0 + (cx - r) * scale;
                double py = y0 + (cy - r) * scale;
                g2.fillOval((int) Math.round(px), (int) Math.round(py), (int) Math.round(d), (int) Math.round(d));
            }

            // outline (thin, inside)
            float circleStroke = 1.0f;
            g2.setStroke(new BasicStroke(circleStroke));
            g2.setColor(outline);
            for (int idx : cluster) {
                Pair<Double> p = pts.get(idx);
                double cx = p.getX();
                double cy = p.getY();
                double d = 2.0 * r * scale; // диаметр в пикселях
                double px = x0 + (cx - r) * scale;
                double py = y0 + (cy - r) * scale;
                double inset = circleStroke / 2.0;
                double drawW = Math.max(0.0, d - 2.0 * inset);
                double drawX = px + inset;
                double drawY = py + inset;
                g2.drawOval((int) Math.round(drawX), (int) Math.round(drawY), (int) Math.round(drawW), (int) Math.round(drawW));
            }

            // Draw cluster number in center of each circle (1-based index)
            String label = Integer.toString(ci + 1);
            // choose font size relative to diameter
            double sampleD = 2.0 * r * scale;
            int fontSize = Math.max(10, (int) Math.round(sampleD * 0.4));
            Font origFont = g2.getFont();
            Font labelFont = origFont.deriveFont(Font.BOLD, fontSize);
            g2.setFont(labelFont);
            FontMetrics fm = g2.getFontMetrics(labelFont);
            g2.setColor(Color.BLACK);
            for (int idx : cluster) {
                Pair<Double> p = pts.get(idx);
                double cx = p.getX();
                double cy = p.getY();
                double centerX = x0 + cx * scale;
                double centerY = y0 + cy * scale;
                int textW = fm.stringWidth(label);
                int textH = fm.getAscent() - fm.getDescent();
                int tx = (int) Math.round(centerX - textW / 2.0);
                int ty = (int) Math.round(centerY + textH / 2.0);
                g2.drawString(label, tx, ty);
            }
            g2.setFont(origFont);
        }

        // вернуть стандартный stroke
        g2.setStroke(new BasicStroke(1f));
    }

    /**
     * Простая отрисовка: permeableLayer (оранжевый), центральные окружности (синие) и чёрная обводка.
     */
    private void drawSimple(Graphics2D g2, int x0, int y0, int size) {
        List<Pair<Double>> pts = model.points;
        if (pts == null || size <= 0) return;

        double L = model.L;
        double scale = (double) size / L;
        double r = model.radius;
        double permeable = model.permeableLayer;

        g2.setColor(new Color(255, 165, 0, 100));
        for (Pair<Double> p : pts) {
            double cx = p.getX();
            double cy = p.getY();
            double outerR = r + permeable;
            double outerD = 2.0 * outerR * scale;
            double outerX = x0 + (cx - outerR) * scale;
            double outerY = y0 + (cy - outerR) * scale;
            g2.fillOval((int) Math.round(outerX), (int) Math.round(outerY), (int) Math.round(outerD), (int) Math.round(outerD));
        }

        g2.setColor(new Color(0, 120, 215, 180));
        for (Pair<Double> p : pts) {
            double cx = p.getX();
            double cy = p.getY();
            double d = 2.0 * r * scale;
            double px = x0 + (cx - r) * scale;
            double py = y0 + (cy - r) * scale;
            g2.fillOval((int) Math.round(px), (int) Math.round(py), (int) Math.round(d), (int) Math.round(d));
        }

        g2.setStroke(new BasicStroke(1f));
        g2.setColor(Color.BLACK);
        for (Pair<Double> p : pts) {
            double cx = p.getX();
            double cy = p.getY();
            double d = 2.0 * r * scale; // диаметр в пикселях
            double px = x0 + (cx - r) * scale;
            double py = y0 + (cy - r) * scale;
            g2.drawOval((int) Math.round(px), (int) Math.round(py), (int) Math.round(d), (int) Math.round(d));
        }
    }

    /**
     * Установить режим отображения кластеров и перерисовать.
     */
    public void renderClusters() {
        this.clusterMode = true;
        if (model != null) model.calcClusterIndices();
        repaint();
    }

    /**
     * Установить простой режим отображения (без раскраски по кластерам) и перерисовать.
     */
    public void renderSimple() {
        this.clusterMode = false;
        repaint();
    }
}
