package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

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

    public MainFrame(Repository<Ticket> ticketRepo, Repository<Kunde> kundeRepo) {
        this.ticketRepo = ticketRepo;
        this.kundeRepo = kundeRepo;

        // Set Nimbus look and feel for modern appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Fallback to default
        }

        setTitle("Ticket Manager");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveData());
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> loadData());
        fileMenu.add(loadItem);

        JMenuItem importItem = new JMenuItem("Import Customers");
        importItem.addActionListener(e -> importCustomers());
        fileMenu.add(importItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Top panel with search and create button
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        JButton createButton = new JButton("Create Ticket");
        createButton.setBackground(new Color(34, 139, 34));
        createButton.setForeground(Color.WHITE);
        createButton.setFont(new Font("Arial", Font.BOLD, 12));

        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            tableModel.applyFilter(query);
        });

        createButton.addActionListener(e -> createTicket());

        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(createButton);

        // Table
        tableModel = new TicketTableModel(ticketRepo.getAll());
        ticketTable = new JTable(tableModel);
        ticketTable.setFont(new Font("Arial", Font.PLAIN, 12));
        ticketTable.setRowHeight(25);
        ticketTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        ticketTable.getTableHeader().setBackground(new Color(70, 130, 180));
        ticketTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Tickets"));

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createTicket() {
        CreateTicketDialog dialog = new CreateTicketDialog(this, kundeRepo.getAll());
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            Ticket ticket = dialog.getTicket();
            try {
                ticketRepo.add(ticket);
                tableModel.updateData(ticketRepo.getAll());
                // Reapply current search filter
                String currentFilter = searchField.getText();
                tableModel.applyFilter(currentFilter);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error creating ticket: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveData() {
        try {
            repositories.PersistenceManager.save("tickets.dat", ticketRepo.getAll());
            repositories.PersistenceManager.save("kunden.dat", kundeRepo.getAll());
            JOptionPane.showMessageDialog(this, "Data saved successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Imported " + imported.size() + " customers.", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error importing customers: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}