package lab2.Task3;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScaledCirclesView extends JPanel {
    private final List<Point2D> points;
    private final int L;
    private final int blurredBoundary;
    private final int radius;

    private static final int WINDOW_SIZE = 1000;

    public ScaledCirclesView(List<Point2D> points, int L, int blurredBoundary, int radius) {
        this.points = points;
        this.L = L;
        this.blurredBoundary = blurredBoundary;
        this.radius = radius;
        setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Общая площадь = L + 2 * blurredBoundary
        double totalSize = L + 2.0 * blurredBoundary;
        double scale = (double) WINDOW_SIZE / totalSize;

        // размеры рамок в пикселях
        int outerSize = (int) Math.round(totalSize * scale);
        int innerSize = (int) Math.round(L * scale);
        int offset = (int) Math.round(blurredBoundary * scale); // отступ от красной рамки до зелёной

        // центрирование красной рамки
        int xOffset = (WINDOW_SIZE - outerSize) / 2;
        int yOffset = (WINDOW_SIZE - outerSize) / 2;

        // --- Красная внешняя граница ---
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(xOffset, yOffset, outerSize, outerSize);

        // --- Зелёная внутренняя граница ---
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(xOffset + offset, yOffset + offset, innerSize, innerSize);

        // --- Окружности (все оранжевые) ---
        Color circleColor = new Color(255, 140, 0); // насыщенный оранжевый

        for (Point2D p : points) {
            double x = p.getX() * scale + xOffset + offset;
            double y = p.getY() * scale + yOffset + offset;
            double rScaled = radius * scale;

            int d = (int) Math.round(2 * rScaled);
            int xDraw = (int) Math.round(x - rScaled);
            int yDraw = (int) Math.round(y - rScaled);

            // заливка окружности
            g2.setColor(circleColor);
            g2.fillOval(xDraw, yDraw, d, d);

            // контур немного темнее
            g2.setColor(circleColor.darker());
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawOval(xDraw, yDraw, d, d);
        }
    }

    public static void showWindow(List<Point2D> points, int L, int blurredBoundary, int radius) {
        JFrame frame = new JFrame("Scaled Circles Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ScaledCirclesView(points, L, blurredBoundary, radius));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
