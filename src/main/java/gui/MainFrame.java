package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import controllers.AppController;
import models.Ticket;

public class MainFrame extends JFrame {
    private final AppController controller;
    private JTable ticketTable;
    private TicketTableModel tableModel;
    private JTextField searchField;
    private DashboardCard totalCard;
    private DashboardCard openCard;
    private DashboardCard highPriorityCard;

    public MainFrame(AppController controller) {
        this.controller = controller;

        // Set modern look and feel for overall app
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("control", Theme.BACKGROUND_COLOR);
            UIManager.put("info", Theme.SURFACE_COLOR);
            UIManager.put("nimbusBase", Theme.PRIMARY_COLOR);
            UIManager.put("nimbusFocus", Theme.PRIMARY_COLOR);
            UIManager.put("nimbusLightBackground", Theme.SURFACE_COLOR);
            UIManager.put("nimbusSelectedText", Theme.SURFACE_COLOR);
            UIManager.put("nimbusSelectionBackground", Theme.PRIMARY_COLOR);
            UIManager.put("text", Theme.TEXT_COLOR);
        } catch (Exception e) {
            // Fallback to default
        }

        setTitle("NextGen Ticket Manager");
        setSize(1100, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BACKGROUND_COLOR);

        initComponents();

        // Save data on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    controller.saveData();
                } catch (Exception ex) {
                    System.err.println("Could not auto-save: " + ex.getMessage());
                }
            }
        });
    }

    private void initComponents() {
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Theme.SURFACE_COLOR);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_COLOR));
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(Theme.FONT_REGULAR);

        JMenuItem saveItem = new JMenuItem("Save Data");
        saveItem.setFont(Theme.FONT_REGULAR);
        saveItem.addActionListener(e -> {
            try {
                controller.saveData();
                ToastNotification.showToast("Data saved successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Reload Data");
        loadItem.setFont(Theme.FONT_REGULAR);
        loadItem.addActionListener(e -> {
            controller.loadData();
            ToastNotification.showToast("Data reloaded.");
        });
        fileMenu.add(loadItem);

        JMenuItem generateRandomItem = new JMenuItem("Generate 10 Random Tickets");
        generateRandomItem.setFont(Theme.FONT_REGULAR);
        generateRandomItem.addActionListener(e -> {
            controller.generateRandomTickets();
            ToastNotification.showToast("10 random tickets generated.");
        });
        fileMenu.add(generateRandomItem);

        JMenuItem clearItem = new JMenuItem("Clear All Data");
        clearItem.setFont(Theme.FONT_REGULAR);
        clearItem.setForeground(Theme.DANGER_COLOR);
        clearItem.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to permanently clear ALL tickets and customers from memory?",
                    "Confirm Reset", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                controller.clearAllData();
                ToastNotification.showToast("All data cleared from memory.");
            }
        });
        fileMenu.add(clearItem);

        fileMenu.addSeparator();

        JMenuItem importItem = new JMenuItem("Import Customers from API");
        importItem.setFont(Theme.FONT_REGULAR);
        importItem.addActionListener(e -> {
            try {
                controller.importCustomers();
                ToastNotification.showToast("Customers imported from API");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "API Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        fileMenu.add(importItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Theme.SURFACE_COLOR);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JLabel titleLabel = new JLabel("Tickets");
        titleLabel.setFont(Theme.FONT_HEADER);
        titleLabel.setForeground(Theme.TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Actions panel inside header
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionsPanel.setBackground(Theme.SURFACE_COLOR);

        searchField = UIFactory.createSearchField("Search Title, ID or Customer...");
        searchField.setColumns(20);
        
        searchField.addActionListener(e -> tableModel.applyFilter(searchField.getText()));
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { search(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { search(); }
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { search(); }
            private void search() {
                tableModel.applyFilter(searchField.getText());
            }
        });

        RoundedButton searchButton = new RoundedButton("Search", 16);
        searchButton.setFont(Theme.FONT_BOLD);
        searchButton.setBackground(Theme.SURFACE_COLOR);
        searchButton.setForeground(Theme.TEXT_COLOR);
        searchButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        searchButton.addActionListener(e -> tableModel.applyFilter(searchField.getText()));

        RoundedButton createButton = new RoundedButton("+ New Ticket", 16);
        createButton.setFont(Theme.FONT_BOLD);
        createButton.setBackground(Theme.PRIMARY_COLOR);
        createButton.setForeground(Theme.SURFACE_COLOR);
        createButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        createButton.addActionListener(e -> createTicket());

        actionsPanel.add(searchField);
        actionsPanel.add(searchButton);
        actionsPanel.add(createButton);
        headerPanel.add(actionsPanel, BorderLayout.EAST);

        // Content Panel Component
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Theme.BACKGROUND_COLOR);
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        // Dashboard Cards Panel
        JPanel dashboardPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        dashboardPanel.setBackground(Theme.BACKGROUND_COLOR);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        totalCard = new DashboardCard("Total Tickets", Theme.PRIMARY_COLOR);
        openCard = new DashboardCard("Open Tickets", Theme.SUCCESS_COLOR);
        highPriorityCard = new DashboardCard("High Priority", Theme.DANGER_COLOR);
        
        dashboardPanel.add(totalCard);
        dashboardPanel.add(openCard);
        dashboardPanel.add(highPriorityCard);

        mainContentPanel.add(dashboardPanel, BorderLayout.NORTH);

        // Table Setup
        tableModel = new TicketTableModel(controller.getTicketRepo().getAll());
        ticketTable = new JTable(tableModel);
        ticketTable.setFont(Theme.FONT_REGULAR);
        ticketTable.setRowHeight(40);
        ticketTable.setShowGrid(false);
        ticketTable.setIntercellSpacing(new Dimension(0, 0));
        ticketTable.setBackground(Theme.SURFACE_COLOR);
        ticketTable.setForeground(Theme.TEXT_COLOR);
        ticketTable.setSelectionBackground(Theme.NEUTRAL_COMPONENT_COLOR);
        ticketTable.setSelectionForeground(Theme.TEXT_COLOR);
        ticketTable.setFillsViewportHeight(true);
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ticketTable.setAutoCreateRowSorter(true); // Enable column sorting
        
        ticketTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = ticketTable.rowAtPoint(e.getPoint());
                int col = ticketTable.columnAtPoint(e.getPoint());
                if (row >= 0 && col != 7) { 
                    viewSelectedTicket(row);
                }
            }
        });

        // Header Styling
        JTableHeader tableHeader = ticketTable.getTableHeader();
        tableHeader.setFont(Theme.FONT_BOLD);
        tableHeader.setBackground(Theme.BACKGROUND_COLOR);
        tableHeader.setForeground(Theme.SECONDARY_TEXT_COLOR);
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_COLOR));
        tableHeader.setPreferredSize(new Dimension(100, 40));
        
        // Custom Table Cell Renderer for padding and flat border
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER_COLOR),
                    BorderFactory.createEmptyBorder(0, 15, 0, 15)
                ));
                return c;
            }
        };
        for(int i=0; i<ticketTable.getColumnCount(); i++) {
            if (i != 7) {
                ticketTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
        }
        
        // Setup Actions column
        TicketActionRendererEditor actionRendererEditor = new TicketActionRendererEditor(this);
        ticketTable.getColumnModel().getColumn(7).setCellRenderer(actionRendererEditor);
        ticketTable.getColumnModel().getColumn(7).setCellEditor(actionRendererEditor);
        ticketTable.getColumnModel().getColumn(7).setPreferredWidth(160);

        JScrollPane tableScrollPane = new JScrollPane(ticketTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(Theme.BORDER_COLOR, 1));
        tableScrollPane.getViewport().setBackground(Theme.SURFACE_COLOR);
        
        mainContentPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
    }
    
    public void refreshData() {
        tableModel.updateData(controller.getTicketRepo().getAll());
        
        int total = 0, open = 0, high = 0;
        for (Ticket t : controller.getTicketRepo().getAll()) {
            total++;
            if (t.getStatus() == models.Status.Open || t.getStatus() == models.Status.InProgress) open++; 
            if (t.getPriority() == models.Priority.HIGH) high++;
        }
        totalCard.updateCount(total);
        openCard.updateCount(open);
        highPriorityCard.updateCount(high);

        // Keep current search filter active
        tableModel.applyFilter(searchField.getText());
    }

    private void createTicket() {
        TicketFormDialog dialog = new TicketFormDialog(this, null, controller.getKundeRepo().getAll(), false);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            try {
                controller.addTicket(dialog.getTicket());
                ToastNotification.showToast("Ticket created.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error creating ticket: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void viewSelectedTicket(int row) {
        int modelRow = ticketTable.convertRowIndexToModel(row);
        Ticket t = tableModel.getTicketAt(modelRow);
        if (t != null) {
            TicketFormDialog dialog = new TicketFormDialog(this, t, controller.getKundeRepo().getAll(), true);
            dialog.setVisible(true);
        }
    }

    public void editTicketFromTable(int row) {
        if (row >= 0) {
            int modelRow = ticketTable.convertRowIndexToModel(row);
            Ticket t = tableModel.getTicketAt(modelRow);
            if (t != null) {
                TicketFormDialog dialog = new TicketFormDialog(this, t, controller.getKundeRepo().getAll(), false);
                dialog.setVisible(true);
                if (dialog.isConfirmed()) {
                    controller.updateTicket();
                    ToastNotification.showToast("Ticket updated.");
                }
            }
        }
    }

    public void deleteTicketFromTable(int row) {
        if (row >= 0) {
            int modelRow = ticketTable.convertRowIndexToModel(row);
            Ticket t = tableModel.getTicketAt(modelRow);
            if (t != null) {
                int response = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete ticket #" + t.getId() + " - " + t.getTitel() + "?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    try {
                        controller.removeTicket(t);
                        ToastNotification.showToast("Ticket deleted.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error deleting ticket: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}