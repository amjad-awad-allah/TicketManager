package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TicketActionRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel renderPanel;
    private final JPanel editPanel;
    private final RoundedButton btnEditRender, btnDeleteRender;
    private int currentRow;
    private final MainFrame mainFrame;

    public TicketActionRendererEditor(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        renderPanel = createPanel();
        btnEditRender = createButton("Edit", Color.decode("#4F46E5"));
        btnDeleteRender = createButton("Delete", Color.decode("#EF4444"));
        renderPanel.add(btnEditRender);
        renderPanel.add(btnDeleteRender);
        editPanel = createPanel();
        RoundedButton btnEdit = createButton("Edit", Color.decode("#4F46E5"));
        btnEdit.addActionListener(e -> {
            fireEditingStopped();
            this.mainFrame.editTicketFromTable(currentRow);
        });
        RoundedButton btnDelete = createButton("Delete", Color.decode("#EF4444"));
        btnDelete.addActionListener(e -> {
            fireEditingStopped();
            this.mainFrame.deleteTicketFromTable(currentRow);
        });
        editPanel.add(btnEdit);
        editPanel.add(btnDelete);
    }
    
    private JPanel createPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        p.setOpaque(true);
        // Matte lower border to match MainFrame table cells
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode("#E5E7EB")));
        return p;
    }

    private RoundedButton createButton(String text, Color bg) {
        RoundedButton b = new RoundedButton(text, 12); // radius 12
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        b.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        return b;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        renderPanel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        return renderPanel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        editPanel.setBackground(table.getSelectionBackground());
        return editPanel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
