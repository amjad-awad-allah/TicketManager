import java.util.List;

import Api.ApiImporter;
import gui.MainFrame;
import models.Kunde;
import models.Ticket;
import repositories.PersistenceManager;
import repositories.Repository;

public class Main {
    public static void main(String[] args) {
        Repository<Ticket> ticketRepo = new Repository<>();
        Repository<Kunde> kundeRepo = new Repository<>();

        // Load previous data if exists
        try {
            ticketRepo.items = PersistenceManager.load("tickets.dat");
            kundeRepo.items = PersistenceManager.load("kunden.dat");
        } catch (Exception e) {
            // No previous data
        }

        // Import customers from API
        try {
            List<Kunde> importedKunden = ApiImporter.importKundenFromApi();
            if (!importedKunden.isEmpty()) {
                kundeRepo.clear();
                for (Kunde kunde : importedKunden) {
                    kundeRepo.add(kunde);
                }
            }
        } catch (Exception e) {
            System.err.println("Error importing customers: " + e.getMessage());
        }

        // Launch GUI
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame(ticketRepo, kundeRepo).setVisible(true);
        });
    }
}