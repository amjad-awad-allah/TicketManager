package test;

import models.Status;
import org.junit.Assert;
import org.junit.Test;

public class StatusTest {

    @Test
    public void testEnumValues() {
        Status[] values = Status.values();
        Assert.assertEquals(3, values.length);
        Assert.assertEquals(Status.Open, values[0]);
        Assert.assertEquals(Status.InProgress, values[1]);
        Assert.assertEquals(Status.Closed, values[2]);
    }

    @Test
    public void testValueOf() {
        Assert.assertEquals(Status.Open, Status.valueOf("Open"));
        Assert.assertEquals(Status.InProgress, Status.valueOf("InProgress"));
        Assert.assertEquals(Status.Closed, Status.valueOf("Closed"));
    }
}