package test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import exceptions.InvalidDataException;
import exceptions.TicketNotFoundException;
import models.Admin;
import models.Kunde;
import models.Priority;
import models.Status;
import models.Ticket;
import repositories.Repository;

public class TicketTest {

    @Test
    public void testCreateTicketSuccess() throws InvalidDataException {
        Kunde kunde = new Kunde(1, "Test User", "test@test.com");
        Admin admin = new Admin(1, "Admin", "admin@test.com");
        Ticket ticket = new Ticket(1, "Fix Login", "Login is broken", Priority.HIGH, new Date(), Status.Offen, kunde,
                admin);

        Assert.assertEquals("Fix Login", ticket.getTitel());
        Assert.assertEquals(Status.Offen, ticket.getStatus());
    }

    @Test(expected = InvalidDataException.class)
    public void testCreateTicketEmptyTitleThrowsException() throws InvalidDataException {
        Kunde kunde = new Kunde(1, "Test User", "test@test.com");
        Admin admin = new Admin(1, "Admin", "admin@test.com");
        // This should throw InvalidDataException
        Ticket ignored = new Ticket(2, "", "Description", Priority.LOW, new Date(), Status.Offen, kunde, admin);
    }

    @Test
    public void testChangeStatus() throws InvalidDataException {
        Kunde kunde = new Kunde(1, "Test User", "test@test.com");
        Admin admin = new Admin(1, "Admin", "admin@test.com");
        Ticket ticket = new Ticket(1, "Issue", "Desc", Priority.MEDIUM, new Date(), Status.Offen, kunde, admin);

        Assert.assertEquals(Status.Offen, ticket.getStatus());
        ticket.setStatus(Status.In_Bearbeitung);
        Assert.assertEquals(Status.In_Bearbeitung, ticket.getStatus());
    }

    @Test(expected = TicketNotFoundException.class)
    public void testTicketNotFound() {
        Repository<Ticket> repo = new Repository<>();
        repo.getById(999);
    }
}
