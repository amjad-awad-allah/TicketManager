package test;

import org.junit.Assert;
import org.junit.Test;

import models.Admin;

public class AdminTest {

    @Test
    public void testConstructor() {
        Admin admin = new Admin(1, "Admin Name", "admin@test.com");
        Assert.assertEquals(1, admin.getId());
        Assert.assertEquals("Admin Name", admin.getName());
        Assert.assertEquals("admin@test.com", admin.getEmail());
    }

    @Test
    public void testToString() {
        Admin admin = new Admin(1, "Admin Name", "admin@test.com");
        Assert.assertEquals("Admin Name", admin.toString());
    }
}