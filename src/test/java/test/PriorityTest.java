package test;

import models.Priority;
import org.junit.Assert;
import org.junit.Test;

public class PriorityTest {

    @Test
    public void testEnumValues() {
        Priority[] values = Priority.values();
        Assert.assertEquals(3, values.length);
        Assert.assertEquals(Priority.LOW, values[0]);
        Assert.assertEquals(Priority.MEDIUM, values[1]);
        Assert.assertEquals(Priority.HIGH, values[2]);
    }

    @Test
    public void testValueOf() {
        Assert.assertEquals(Priority.LOW, Priority.valueOf("LOW"));
        Assert.assertEquals(Priority.MEDIUM, Priority.valueOf("MEDIUM"));
        Assert.assertEquals(Priority.HIGH, Priority.valueOf("HIGH"));
    }
}