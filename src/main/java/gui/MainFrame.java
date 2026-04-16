package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

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

import Api.ApiImporter;
import models.Kunde;
import models.Ticket;
import repositories.Repository;

public class MainFrame extends JFrame {
    private Repository<Ticket> ticketRepo;
    private Repository<Kunde> kundeRepo;
    private JTable ticketTable;
    private TicketTableModel tableModel;
    private JTextField searchField;
    private DashboardCard totalCard;
    private DashboardCard openCard;
    private DashboardCard highPriorityCard;

    // Professional Color Palette
    private static final Color BACKGROUND_COLOR = Color.decode("#F3F4F6"); // Gray 100
    private static final Color SURFACE_COLOR = Color.decode("#FFFFFF");
    private static final Color PRIMARY_COLOR = Color.decode("#4F46E5"); // Indigo 600
    private static final Color TEXT_COLOR = Color.decode("#111827");
    private static final Color BORDER_COLOR = Color.decode("#E5E7EB");

    public MainFrame(Repository<Ticket> ticketRepo, Repository<Kunde> kundeRepo) {
        this.ticketRepo = ticketRepo;
        this.kundeRepo = kundeRepo;

        // Set modern look and feel for overall app
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("control", BACKGROUND_COLOR);
            UIManager.put("info", SURFACE_COLOR);
            UIManager.put("nimbusBase", PRIMARY_COLOR);
            UIManager.put("nimbusFocus", PRIMARY_COLOR);
            UIManager.put("nimbusLightBackground", SURFACE_COLOR);
            UIManager.put("nimbusSelectedText", SURFACE_COLOR);
            UIManager.put("nimbusSelectionBackground", PRIMARY_COLOR);
            UIManager.put("text", TEXT_COLOR);
        } catch (Exception e) {
            // Fallback to default
        }

        setTitle("NextGen Ticket Manager");
        setSize(1100, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        initComponents();
        loadData();

        // Save data on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
            }
        });
    }

    private void initComponents() {
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(SURFACE_COLOR);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JMenuItem saveItem = new JMenuItem("Save Data");
        saveItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        saveItem.addActionListener(e -> saveData());
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Reload Data");
        loadItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loadItem.addActionListener(e -> loadData());
        fileMenu.add(loadItem);

        fileMenu.addSeparator();

        JMenuItem importItem = new JMenuItem("Import Customers from API");
        importItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        importItem.addActionListener(e -> importCustomers());
        fileMenu.add(importItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(SURFACE_COLOR);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JLabel titleLabel = new JLabel("Tickets");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Actions panel inside header
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actionsPanel.setBackground(SURFACE_COLOR);

        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.putClientProperty("JTextField.placeholderText", "Search tickets...");
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        
        // Add Enter key listener
        searchField.addActionListener(e -> tableModel.applyFilter(searchField.getText()));
        
        // Add live-search (while typing) listener
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

        RoundedButton searchButton = createFlatButton("Search", SURFACE_COLOR, TEXT_COLOR);
        searchButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)
        ));
        
        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            tableModel.applyFilter(query);
        });

        RoundedButton createButton = createFlatButton("+ New Ticket", PRIMARY_COLOR, SURFACE_COLOR);
        createButton.addActionListener(e -> createTicket());

        actionsPanel.add(searchField);
        actionsPanel.add(searchButton);
        actionsPanel.add(createButton);
        headerPanel.add(actionsPanel, BorderLayout.EAST);

        // Content Panel Component
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(BACKGROUND_COLOR);
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        // Dashboard Cards Panel
        JPanel dashboardPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        dashboardPanel.setBackground(BACKGROUND_COLOR);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        totalCard = new DashboardCard("Total Tickets", Color.decode("#6366F1")); // Indigo 500
        openCard = new DashboardCard("Open Tickets", Color.decode("#10B981")); // Emerald 500
        highPriorityCard = new DashboardCard("High Priority", Color.decode("#EF4444")); // Red 500
        
        dashboardPanel.add(totalCard);
        dashboardPanel.add(openCard);
        dashboardPanel.add(highPriorityCard);

        mainContentPanel.add(dashboardPanel, BorderLayout.NORTH);

        // Table Setup
        tableModel = new TicketTableModel(ticketRepo.getAll());
        ticketTable = new JTable(tableModel);
        ticketTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ticketTable.setRowHeight(40);
        ticketTable.setShowGrid(false);
        ticketTable.setIntercellSpacing(new Dimension(0, 0));
        ticketTable.setBackground(SURFACE_COLOR);
        ticketTable.setForeground(TEXT_COLOR);
        ticketTable.setSelectionBackground(Color.decode("#EDF2F7")); // Light grayish blue
        ticketTable.setSelectionForeground(TEXT_COLOR);
        ticketTable.setFillsViewportHeight(true);
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ticketTable.setAutoCreateRowSorter(true); // Enable column sorting
        
        // Listen to selection to open view popup
        ticketTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = ticketTable.rowAtPoint(e.getPoint());
                int col = ticketTable.columnAtPoint(e.getPoint());
                if (row >= 0 && col != 7) { // col 7 is Actions
                    viewSelectedTicket(row);
                }
            }
        });

        // Header Styling
        JTableHeader tableHeader = ticketTable.getTableHeader();
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableHeader.setBackground(Color.decode("#F9FAFB"));
        tableHeader.setForeground(Color.decode("#4B5563")); // Gray 600
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        tableHeader.setPreferredSize(new Dimension(100, 40));
        
        // Custom Table Cell Renderer for padding and flat border
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
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
        tableScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        tableScrollPane.getViewport().setBackground(SURFACE_COLOR);
        
        mainContentPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
    }
    
    private RoundedButton createFlatButton(String text, Color bg, Color fg) {
        RoundedButton button = new RoundedButton(text, 16);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        return button;
    }

    private void createTicket() {
        CreateTicketDialog dialog = new CreateTicketDialog(this, kundeRepo.getAll());
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            Ticket ticket = dialog.getTicket();
            try {
                ticketRepo.add(ticket);
                tableModel.updateData(ticketRepo.getAll());
                String currentFilter = searchField.getText();
                tableModel.applyFilter(currentFilter);
                updateDashboardCards();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error creating ticket: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateDashboardCards() {
        int total = 0;
        int open = 0;
        int high = 0;
        for (Ticket t : ticketRepo.getAll()) {
            total++;
            if (t.getStatus() == models.Status.Open || t.getStatus() == models.Status.InProgress) open++; 
            if (t.getPriority() == models.Priority.HIGH) high++;
        }
        totalCard.updateCount(total);
        openCard.updateCount(open);
        highPriorityCard.updateCount(high);
    }
    
    private void viewSelectedTicket(int row) {
        int modelRow = ticketTable.convertRowIndexToModel(row);
        Ticket t = tableModel.getTicketAt(modelRow);
        if (t != null) {
            EditTicketDialog dialog = new EditTicketDialog(this, t, kundeRepo.getAll(), true);
            dialog.setVisible(true);
        }
    }

    public void editTicketFromTable(int row) {
        if (row >= 0) {
            int modelRow = ticketTable.convertRowIndexToModel(row);
            Ticket t = tableModel.getTicketAt(modelRow);
            if (t != null) {
                EditTicketDialog dialog = new EditTicketDialog(this, t, kundeRepo.getAll(), false);
                dialog.setVisible(true);
                if (dialog.isConfirmed()) {
                    tableModel.updateData(ticketRepo.getAll());
                    updateDashboardCards();
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
                        ticketRepo.remove(t);
                        tableModel.updateData(ticketRepo.getAll());
                        updateDashboardCards();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error deleting ticket: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void saveData() {
        try {
            repositories.PersistenceManager.save("tickets.dat", ticketRepo.getAll());
            repositories.PersistenceManager.save("kunden.dat", kundeRepo.getAll());
            ToastNotification.showToast("Data saved successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData() {
        try {
            ticketRepo.items = repositories.PersistenceManager.load("tickets.dat");
            kundeRepo.items = repositories.PersistenceManager.load("kunden.dat");
            tableModel.updateData(ticketRepo.getAll());
            updateDashboardCards();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importCustomers() {
        try {
            List<Kunde> imported = ApiImporter.importKundenFromApi();
            kundeRepo.clear();
            for (Kunde k : imported) {
                kundeRepo.add(k);
            }
            ToastNotification.showToast("Imported " + imported.size() + " customers successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error importing customers: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}