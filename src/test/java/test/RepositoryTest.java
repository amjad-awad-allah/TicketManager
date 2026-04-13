package test;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import exceptions.InvalidDataException;
import exceptions.TicketNotFoundExceptione;
import models.Admin;
import models.Kunde;
import models.Priority;
import models.Status;
import models.Ticket;
import repositories.Repository;

public class RepositoryTest {

    @Test
    public void testConstructor() {
        Repository<Ticket> repo = new Repository<>();
        Assert.assertNotNull(repo.items);
        Assert.assertEquals(0, repo.items.size());
    }

    @Test
    public void testAdd() throws InvalidDataException {
        Repository<Ticket> repo = new Repository<>();
        Kunde kunde = new Kunde(1, "Kunde", "kunde@test.com");
        Admin admin = new Admin(2, "Admin", "admin@test.com");
        Ticket ticket = new Ticket(1, "Test Ticket", "Description", Priority.HIGH, new Date(), Status.Open, kunde,
                admin);
        boolean result = repo.add(ticket);
        Assert.assertTrue(result);
        Assert.assertEquals(1, repo.items.size());
        Assert.assertEquals(ticket, repo.items.get(0));
    }

    @Test
    public void testGetAll() throws InvalidDataException {
        Repository<Ticket> repo = new Repository<>();
        Kunde kunde = new Kunde(1, "Kunde", "kunde@test.com");
        Admin admin = new Admin(2, "Admin", "admin@test.com");
        Ticket ticket = new Ticket(1, "Test Ticket", "Description", Priority.HIGH, new Date(), Status.Open, kunde,
                admin);
        repo.add(ticket);
        ArrayList<Ticket> all = repo.getAll();
        Assert.assertEquals(1, all.size());
        Assert.assertEquals(ticket, all.get(0));
        // Test that it's a copy
        all.clear();
        Assert.assertEquals(1, repo.items.size());
    }

    @Test
    public void testGetById() throws InvalidDataException {
        Repository<Ticket> repo = new Repository<>();
        Kunde kunde = new Kunde(1, "Kunde", "kunde@test.com");
        Admin admin = new Admin(2, "Admin", "admin@test.com");
        Ticket ticket = new Ticket(1, "Test Ticket", "Description", Priority.HIGH, new Date(), Status.Open, kunde,
                admin);
        repo.add(ticket);
        Ticket found = repo.getById(1);
        Assert.assertEquals(ticket, found);
    }

    @Test(expected = TicketNotFoundExceptione.class)
    public void testGetByIdNotFoundThrowsException() {
        Repository<Ticket> repo = new Repository<>();
        repo.getById(999);
    }

    @Test
    public void testRemove() throws InvalidDataException {
        Repository<Ticket> repo = new Repository<>();
        Kunde kunde = new Kunde(1, "Kunde", "kunde@test.com");
        Admin admin = new Admin(2, "Admin", "admin@test.com");
        Ticket ticket = new Ticket(1, "Test Ticket", "Description", Priority.HIGH, new Date(), Status.Open, kunde,
                admin);
        repo.add(ticket);
        boolean result = repo.remove(ticket);
        Assert.assertTrue(result);
        Assert.assertEquals(0, repo.items.size());
    }

    @Test
    public void testUpdate() throws InvalidDataException {
        Repository<Ticket> repo = new Repository<>();
        Kunde kunde = new Kunde(1, "Kunde", "kunde@test.com");
        Admin admin = new Admin(2, "Admin", "admin@test.com");
        Ticket ticket = new Ticket(1, "Test Ticket", "Description", Priority.HIGH, new Date(), Status.Open, kunde,
                admin);
        repo.add(ticket);
        Ticket updatedTicket = new Ticket(1, "Updated Ticket", "Updated Description", Priority.LOW, new Date(),
                Status.Closed, kunde, admin);
        boolean result = repo.update(updatedTicket);
        Assert.assertTrue(result);
        Assert.assertEquals("Updated Ticket", repo.items.get(0).getTitel());
    }

    @Test
    public void testClear() throws InvalidDataException {
        Repository<Ticket> repo = new Repository<>();
        Kunde kunde = new Kunde(1, "Kunde", "kunde@test.com");
        Admin admin = new Admin(2, "Admin", "admin@test.com");
        Ticket ticket = new Ticket(1, "Test Ticket", "Description", Priority.HIGH, new Date(), Status.Open, kunde,
                admin);
        repo.add(ticket);
        repo.clear();
        Assert.assertEquals(0, repo.items.size());
    }
}