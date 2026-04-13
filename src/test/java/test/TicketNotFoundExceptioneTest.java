package test;

import exceptions.TicketNotFoundExceptione;
import org.junit.Assert;
import org.junit.Test;

public class TicketNotFoundExceptioneTest {

    @Test
    public void testConstructor() {
        String message = "Ticket not found";
        TicketNotFoundExceptione exception = new TicketNotFoundExceptione(message);
        Assert.assertEquals(message, exception.getMessage());
    }
}