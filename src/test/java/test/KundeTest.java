package test;

import org.junit.Assert;
import org.junit.Test;

import models.Kunde;

public class KundeTest {

    @Test
    public void testConstructor() {
        Kunde kunde = new Kunde(1, "Kunde Name", "kunde@test.com");
        Assert.assertEquals(1, kunde.getId());
        Assert.assertEquals("Kunde Name", kunde.getName());
        Assert.assertEquals("kunde@test.com", kunde.getEmail());
    }

    @Test
    public void testToString() {
        Kunde kunde = new Kunde(1, "Kunde Name", "kunde@test.com");
        Assert.assertEquals("Kunde Name", kunde.toString());
    }
}