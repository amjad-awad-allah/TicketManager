package test;

import models.App;
import org.junit.Test;

public class AppTest {

    @Test
    public void testMain() throws Exception {
        // Test that main method runs without throwing exception
        App.main(new String[] {});
    }
}