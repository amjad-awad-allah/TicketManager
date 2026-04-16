package controllers;

import java.util.List;
import javax.swing.SwingUtilities;

import Api.ApiImporter;
import gui.MainFrame;
import models.Kunde;
import models.Ticket;
import repositories.PersistenceManager;
import repositories.Repository;

public class AppController {
    private final Repository<Ticket> ticketRepo;
    private final Repository<Kunde> kundeRepo;
    private MainFrame mainFrame;

    public AppController() {
        this.ticketRepo = new Repository<>();
        this.kundeRepo = new Repository<>();
    }

    public void start() {
        // Try initial loading before UI
        loadData();
        
        // Import initial customers if empty
        if (kundeRepo.getAll().isEmpty()) {
            try {
                importCustomers();
            } catch (Exception e) {
                System.err.println("Failed initial import: " + e.getMessage());
            }
        }

        SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame(this);
            mainFrame.refreshData(); // Ensure UI is updated with pre-loaded data
            mainFrame.setVisible(true);
        });
    }

    public Repository<Ticket> getTicketRepo() {
        return ticketRepo;
    }

    public Repository<Kunde> getKundeRepo() {
        return kundeRepo;
    }

    public void addTicket(Ticket t) throws Exception {
        if (t.getId() <= 0) {
            int maxId = 0;
            for (Ticket existing : ticketRepo.getAll()) {
                if (existing.getId() > maxId) {
                    maxId = existing.getId();
                }
            }
            t.setId(maxId + 1);
        }
        ticketRepo.add(t);
        refreshUI();
    }

    public void removeTicket(Ticket t) throws Exception {
        ticketRepo.remove(t);
        refreshUI();
    }

    public void updateTicket() {
        refreshUI();
    }

    public void saveData() throws Exception {
        PersistenceManager.save("tickets.dat", ticketRepo.getAll());
        PersistenceManager.save("kunden.dat", kundeRepo.getAll());
    }

    public void loadData() {
        try {
            ticketRepo.items = PersistenceManager.load("tickets.dat");
            kundeRepo.items = PersistenceManager.load("kunden.dat");
        } catch (Exception e) {
            // First time run, files don't exist
        }
        if (mainFrame != null) {
            refreshUI();
        }
    }

    public void importCustomers() throws Exception {
        List<Kunde> imported = ApiImporter.importKundenFromApi();
        kundeRepo.clear();
        for (Kunde k : imported) {
            kundeRepo.add(k);
        }
        if (mainFrame != null) {
            refreshUI();
        }
    }

    public void generateRandomTickets() {
        String[] titles = {"Server Down", "Email Issue", "Password Reset", "Software Install", "Network Slow", "Hardware Bug", "VPN Connection", "Printer Error", "Database Timeout", "Access Denied"};
        String[] descriptions = {"Urgent fix needed", "User cannot login", "Forgot password", "Need latest version", "Internet is sluggish", "Mouse not working", "Cannot reach office net", "Paper jam", "Queries are failing", "Need folder permissions"};
        java.util.Random random = new java.util.Random();
        List<Kunde> customers = kundeRepo.getAll();

        if (customers.isEmpty()) {
            gui.ToastNotification.showToast("No customers found! Import customers first.");
            return;
        }

        for (int i = 0; i < 10; i++) {
            String title = titles[random.nextInt(titles.length)] + " #" + (random.nextInt(100) + 1);
            String desc = descriptions[random.nextInt(descriptions.length)];
            models.Priority priority = models.Priority.values()[random.nextInt(models.Priority.values().length)];
            Kunde customer = customers.get(random.nextInt(customers.size()));

            Ticket t = new Ticket(title, desc, priority, customer);
            try {
                addTicket(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clearAllData() {
        ticketRepo.clear();
        // We keep customers (kundeRepo) as they are needed for generating/adding tickets
        refreshUI();
    }

    private void refreshUI() {
        mainFrame.refreshData();
    }
}
