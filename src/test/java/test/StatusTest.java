package test;

import models.Status;
import org.junit.Assert;
import org.junit.Test;

public class StatusTest {

    @Test
    public void testEnumValues() {
        Status[] values = Status.values();
        Assert.assertEquals(3, values.length);
        Assert.assertEquals(Status.Offen, values[0]);
        Assert.assertEquals(Status.In_Bearbeitung, values[1]);
        Assert.assertEquals(Status.Geschlossen, values[2]);
    }

    @Test
    public void testValueOf() {
        Assert.assertEquals(Status.Offen, Status.valueOf("Offen"));
        Assert.assertEquals(Status.In_Bearbeitung, Status.valueOf("In_Bearbeitung"));
        Assert.assertEquals(Status.Geschlossen, Status.valueOf("Geschlossen"));
    }
}