package models;

import java.util.Date;
import java.util.List;

import Api.ApiImporter;
import repositories.PersistenceManager;
import repositories.Repository;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("🚀 Starting Ticket Management System");

        // Create repositories
        Repository<Ticket> ticketRepo = new Repository<>();
        Repository<Kunde> kundeRepo = new Repository<>();

        // Load previous data if exists
        ticketRepo.items = PersistenceManager.load("tickets.dat");
        kundeRepo.items = PersistenceManager.load("kunden.dat");

        System.out.println("📊 Loaded tickets count: " + ticketRepo.size());
        System.out.println("📊 Loaded customers count: " + kundeRepo.size());

        // Always import customers from API for testing
        System.out.println("\n🌐 Testing API Import...");
        List<Kunde> importedKunden = ApiImporter.importKundenFromApi();

        if (importedKunden.isEmpty()) {
            System.err.println("❌ No customers imported from API");
        } else {
            System.out.println("✅ Successfully imported " + importedKunden.size() + " customers from API");

            // Clear existing customers and add imported ones
            kundeRepo.clear();
            for (Kunde kunde : importedKunden) {
                kundeRepo.add(kunde);
            }
        }

        System.out.println("💾 Total customers available: " + kundeRepo.size());

        // Create sample data if ticket repository is empty
        if (ticketRepo.size() == 0) {
            System.out.println("\n🔧 Creating sample data...");

            // Get first customer from repository (or create if empty)
            Kunde customer;
            if (kundeRepo.size() > 0) {
                customer = kundeRepo.getAll().get(0);
            } else {
                customer = new Kunde(1, "Ahmed Mohamed", "ahmed@email.com");
            }

            Admin manager = new Admin(2, "System Manager", "manager@email.com");

            // Create sample ticket
            Ticket ticket1 = new Ticket(100, "Login Problem",
                    "Cannot login to the system", Priority.HIGH,
                    new Date(), Status.Open, customer, manager);

            // Add ticket to repository
            ticketRepo.add(ticket1);
            System.out.println("✅ New ticket created");
        }

        // Display all tickets
        System.out.println("\n📋 Ticket List:");
        ticketRepo.printAll();

        // Display all customers
        System.out.println("\n👥 Customer List:");
        kundeRepo.printAll();

        // Save data on exit
        PersistenceManager.save("tickets.dat", ticketRepo.getAll());
        PersistenceManager.save("kunden.dat", kundeRepo.getAll());
        System.out.println("\n💾 Data saved successfully!");

        System.out.println("✅ Program finished successfully!");
    }
}
