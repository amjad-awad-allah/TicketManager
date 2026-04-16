package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class ToastNotification extends JWindow {
    private String message;
    private final int TOAST_WIDTH = 300;
    private final int TOAST_HEIGHT = 50;
    private float opacity = 1.0f;
    private Timer fadeOutTimer;

    public ToastNotification(String message) {
        this.message = message;
        setSize(TOAST_WIDTH, TOAST_HEIGHT);
        setBackground(new Color(0, 0, 0, 0)); // transparent background
        setAlwaysOnTop(true);
        setFocusableWindowState(false); // Don't steal focus

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        messageLabel.setForeground(Color.WHITE);
        add(messageLabel);

        // Position bottom right of primary screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = ge.getMaximumWindowBounds();
        int x = bounds.width - TOAST_WIDTH - 20;
        int y = bounds.height - TOAST_HEIGHT - 20;
        setLocation(x, y);

        // Simple fade out mechanism
        fadeOutTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0.0f) {
                    opacity = 0.0f;
                    fadeOutTimer.stop();
                    dispose();
                }
                setOpacity(opacity);
            }
        });
        fadeOutTimer.setInitialDelay(2500); // stay solid for 2.5s before fading
        fadeOutTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(new Color(31, 41, 55, 230)); // Cool dark gray (Tailwind gray-800)
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16));

        g2.dispose();
        super.paint(g); // paints the label over the background
    }

    public static void showToast(String message) {
        ToastNotification toast = new ToastNotification(message);
        toast.setVisible(true);
    }
}
