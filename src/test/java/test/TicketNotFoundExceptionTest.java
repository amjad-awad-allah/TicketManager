package test;

import exceptions.TicketNotFoundException;
import org.junit.Assert;
import org.junit.Test;

public class TicketNotFoundExceptionTest {

    @Test
    public void testConstructor() {
        String message = "Ticket not found";
        TicketNotFoundException exception = new TicketNotFoundException(message);
        Assert.assertEquals(message, exception.getMessage());
    }
}
