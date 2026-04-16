package gui;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class ToastNotification extends JWindow {
    private String message;
    private final int TOAST_WIDTH = 350;
    private final int TOAST_HEIGHT = 60;
    private float opacity = 1.0f;
    private Timer fadeOutTimer;

    public ToastNotification(String message) {
        this.message = message;
        setSize(TOAST_WIDTH, TOAST_HEIGHT);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        
        // Premium non-transparent panel
        JPanel content = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Deep Indigo Background (Matches Theme.PRIMARY_COLOR but slightly richer)
                g2.setColor(new Color(79, 70, 229)); 
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));

                // Subtle inner glow/border
                g2.setColor(new Color(255, 255, 255, 60));
                g2.setStroke(new java.awt.BasicStroke(1));
                g2.draw(new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4, 28, 28));
                
                g2.dispose();
            }
        };
        content.setOpaque(false);
        setContentPane(content);
        // Important: Set window background to match parent or be transparent only at the corners
        setBackground(new Color(0,0,0,0)); 

        JLabel messageLabel = new JLabel("✨ " + message, SwingConstants.CENTER);
        messageLabel.setFont(Theme.FONT_BOLD.deriveFont(15f));
        messageLabel.setForeground(Color.WHITE);
        content.add(messageLabel, BorderLayout.CENTER);

        // Position: Top-Center
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = ge.getMaximumWindowBounds();
        int x = bounds.x + (bounds.width - getWidth()) / 2;
        int y = bounds.y + 60; 
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

    // Painting is now handled by the custom content pane


    public static void showToast(String message) {
        ToastNotification toast = new ToastNotification(message);
        toast.setVisible(true);
    }
}
