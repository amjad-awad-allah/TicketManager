package gui;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UIFactory {

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_BOLD);
        label.setForeground(Theme.TEXT_COLOR);
        return label;
    }

    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(Theme.FONT_REGULAR);
        field.setForeground(Theme.TEXT_COLOR);
        field.setBackground(Theme.SURFACE_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return field;
    }

    public static JTextField createSearchField(String hint) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                    g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Theme.SECONDARY_TEXT_COLOR);
                    g2.setFont(getFont().deriveFont(java.awt.Font.ITALIC));
                    int padding = (getHeight() - getFontMetrics(getFont()).getHeight()) / 2 + getFontMetrics(getFont()).getAscent();
                    g2.drawString(hint, getInsets().left, padding - 1);
                    g2.dispose();
                }
            }
        };
        field.setFont(Theme.FONT_REGULAR);
        field.setForeground(Theme.TEXT_COLOR);
        field.setBackground(Theme.SURFACE_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        return field;
    }

    public static JTextArea createTextArea(int rows, int cols) {
        JTextArea area = new JTextArea(rows, cols);
        area.setFont(Theme.FONT_REGULAR);
        area.setForeground(Theme.TEXT_COLOR);
        area.setBackground(Theme.SURFACE_COLOR);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return area;
    }

    public static <T> JComboBox<T> createComboBox(T[] items) {
        JComboBox<T> box = new JComboBox<>(items);
        box.setFont(Theme.FONT_REGULAR);
        box.setForeground(Theme.TEXT_COLOR);
        box.setBackground(Theme.SURFACE_COLOR);
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)));
        return box;
    }
}
