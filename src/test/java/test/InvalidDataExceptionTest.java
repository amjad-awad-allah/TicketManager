package test;

import exceptions.InvalidDataException;
import org.junit.Assert;
import org.junit.Test;

public class InvalidDataExceptionTest {

    @Test
    public void testConstructor() {
        String message = "Invalid data";
        InvalidDataException exception = new InvalidDataException(message);
        Assert.assertEquals(message, exception.getMessage());
    }
}