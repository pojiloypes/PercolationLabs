package lab2;

import javax.swing.*;

import Utils.*;

import java.awt.*;
import java.util.List;

public class ScaledCirclesView extends JPanel {
    private final List<Pair<Double>> points;
    private final int L;
    private final int radius;
    private final BoundaryType boundaryType;
    private static final int WINDOW_SIZE = 800;
    private final Color circleColor = new Color(70, 130, 180); 

    public ScaledCirclesView(List<Pair<Double>> points, int L, int radius, BoundaryType boundaryType) {
        this.points = points;
        this.L = L;
        this.radius = radius;
        this.boundaryType = boundaryType;
        setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- УБИРАЕМ ОТСТУПЫ ---
        // Область рисования теперь совпадает с размером окна
        int drawAreaSize = WINDOW_SIZE;
        double scale = (double) drawAreaSize / L;

        // --- УСТАНАВЛИВАЕМ МАСКУ ОБРЕЗКИ НА ВСЕ ОКНО ---
        Shape oldClip = g2.getClip();
        g2.setClip(new Rectangle(0, 0, drawAreaSize, drawAreaSize));

        // --- Окружности ---
        for (Pair<Double> p : points) {
            double centerX = p.getX();
            double centerY = p.getY();
            double rScaled = radius * scale;

            if (boundaryType == BoundaryType.OPEN) {
                drawSingleCircle(g2, centerX, centerY, rScaled, scale);
            } else { // PERIODIC
                drawPeriodicCopies(g2, centerX, centerY, rScaled, scale);
            }
        }
        
        // --- ВОЗВРАЩАЕМ ОБРЕЗКУ В ИСХОДНОЕ СОСТОЯНИЕ ---
        g2.setClip(oldClip);
    }
    
    /**
     * Рисует одну окружность. Убрали borderOffset из расчетов.
     */
    private void drawSingleCircle(Graphics2D g2, double cx, double cy, double r, double scale) {
        int x = (int) Math.round(cx * scale - r);
        int y = (int) Math.round(cy * scale - r);
        int d = (int) Math.round(2 * r);

        g2.setColor(this.circleColor);
        g2.fillOval(x, y, d, d);
        
        g2.setColor(this.circleColor.darker());
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawOval(x, y, d, d);
    }

    /**
     * Рисует окружность и ее видимые копии для периодических границ.
     */
    private void drawPeriodicCopies(Graphics2D g2, double cx, double cy, double r, double scale) {
        boolean crossesLeft = (cx - r / scale < 0);
        boolean crossesRight = (cx + r / scale > L);
        boolean crossesTop = (cy - r / scale < 0);
        boolean crossesBottom = (cy + r / scale > L);

        drawSingleCircle(g2, cx, cy, r, scale);

        if (crossesLeft) drawSingleCircle(g2, cx + L, cy, r, scale);
        if (crossesRight) drawSingleCircle(g2, cx - L, cy, r, scale);
        if (crossesTop) drawSingleCircle(g2, cx, cy + L, r, scale);
        if (crossesBottom) drawSingleCircle(g2, cx, cy - L, r, scale);
        
        if (crossesLeft && crossesTop) drawSingleCircle(g2, cx + L, cy + L, r, scale);
        if (crossesRight && crossesTop) drawSingleCircle(g2, cx - L, cy + L, r, scale);
        if (crossesLeft && crossesBottom) drawSingleCircle(g2, cx + L, cy - L, r, scale);
        if (crossesRight && crossesBottom) drawSingleCircle(g2, cx - L, cy - L, r, scale);
    }

    public static void showWindow(List<Pair<Double>> points, int L, int radius, BoundaryType boundaryType) {
        JFrame frame = new JFrame("Scaled Circles Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new ScaledCirclesView(points, L, radius, boundaryType));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}