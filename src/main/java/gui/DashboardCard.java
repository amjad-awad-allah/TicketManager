package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DashboardCard extends JPanel {

    private final JLabel countLabel;
    private final JLabel titleLabel;
    private final Color badgeColor;

    public DashboardCard(String title, Color badgeColor) {
        this.badgeColor = badgeColor;
        setOpaque(false); // we will draw a rounded rectangle
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        setPreferredSize(new Dimension(200, 100));

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.decode("#6B7280")); // Gray 500

        countLabel = new JLabel("0");
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        countLabel.setForeground(Color.decode("#111827")); // Gray 900

        add(titleLabel, BorderLayout.NORTH);
        add(countLabel, BorderLayout.CENTER);
    }

    public void updateCount(int count) {
        countLabel.setText(String.valueOf(count));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));

        // Draw side badge color
        g2.setColor(badgeColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, 6, getHeight(), 16, 16));
        // Draw over the right part of the side badge to make only left edge rounded
        g2.fillRect(3, 0, 3, getHeight());

        // Draw subtle border around
        g2.setColor(Color.decode("#E5E7EB"));
        g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 16, 16));

        g2.dispose();
    }
}
