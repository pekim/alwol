package uk.co.pekim.site.test.services;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import uk.co.pekim.site.test.model.User;

public class UserTest {
    @Test
    public void goodPasswordSuceeds() {
        User user = new User("", "secret", "");
        assertTrue("password", user.verifyPassword("secret"));
    }

    @Test
    public void badPasswordFails() {
        User user = new User("", "secret", "");
        assertFalse("password", user.verifyPassword("bad"));
    }
}
