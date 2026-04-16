package gui;

import java.awt.Color;
import java.awt.Font;

public class Theme {
    // Brand Colors
    public static final Color BACKGROUND_COLOR = Color.decode("#F9FAFB"); // Very Light Gray
    public static final Color SURFACE_COLOR = Color.decode("#FFFFFF");    // Pure White
    public static final Color PRIMARY_COLOR = Color.decode("#4F46E5");    // Indigo 600
    public static final Color PRIMARY_COLOR_LIGHT = Color.decode("#746ee4ff");    // Indigo 600
    public static final Color DANGER_COLOR = Color.decode("#EF4444");     // Red 500
    public static final Color SUCCESS_COLOR = Color.decode("#10B981");    // Emerald 500
    public static final Color NEUTRAL_COMPONENT_COLOR = Color.decode("#F3F4F6"); // Cancel button color

    // Text & Border Colors
    public static final Color TEXT_COLOR = Color.decode("#111827");       // Almost Black
    public static final Color SECONDARY_TEXT_COLOR = Color.decode("#6B7280"); // Gray 500
    public static final Color BORDER_COLOR = Color.decode("#E5E7EB");     // Gray 200

    // Typography
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 24);
}
