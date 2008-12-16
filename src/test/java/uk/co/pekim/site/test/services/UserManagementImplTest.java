package uk.co.pekim.site.test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static uk.co.pekim.junit.Assert.assertEquals;

import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.pekim.site.test.model.User;

import com.db4o.ObjectContainer;
import com.db4o.ext.ExtDb4o;
import com.db4o.ext.MemoryFile;

public class UserManagementImplTest {
    private ObjectContainer objectContainer;
    private UserManagement userManagement;
    private UserMailer userMailer;

    class TestUserMailer implements UserMailer {
        public void sendRegistrationConfirmationEmail(User user, ComponentResources componentResources) {
            //
        }
    }
    
    @Before
    public void setup() {
        MemoryFile file = new MemoryFile();
        objectContainer = ExtDb4o.openMemoryFile(file);
        userMailer = new TestUserMailer();
        userManagement = new UserManagementImpl(objectContainer, userMailer);
    }
    
    @After
    public void teardown() {
        objectContainer.close();
    }
    
    @Test
    public void addUser() {
        userManagement.addUser("pekim", "secret", "aaa@bbb.com", null);
        
        List<User> users = objectContainer.query(User.class);
        assertEquals(1, users.size());
        
        User user = users.get(0);
        assertEquals("pekim", user.getUsername());
        assertEquals("aaa@bbb.com", user.getEmailAddress());
        assertTrue("password", user.verifyPassword("secret"));
        assertNotNull(user.getConfirmationKey());
    }
    
    @Test(expected=UserException.class)
    public void addExistingUser() {
        userManagement.addUser("pekim", "secret", "aaa@bbb.com", null);
        userManagement.addUser("pekim", "secret", "aaa@bbb.com", null);
    }
    
    @Test
    public void userNotExists() {
        assertFalse(userManagement.isUser("pekim"));
    }
    
    @Test
    public void userExists() {
        userManagement.addUser("pekim", "secret", "aaa@bbb.com", null);
        assertTrue(userManagement.isUser("pekim"));
    }
    
    @Test
    public void confirmationSuccess() {
        User user = userManagement.addUser("pekim", "secret", "aaa@bbb.com", null);
        User confirmUser = userManagement.getUserAwaitingConfirmation(user.getConfirmationKey());
        userManagement.confirmUser(confirmUser);
        assertTrue(confirmUser.isConfirmed());
    }
    
    @Test
    public void userAwaitingConfirmationBadKey() {
        User confirmUser = userManagement.getUserAwaitingConfirmation("bad");
        assertNull(confirmUser);
    }

    @Test(expected=UserException.class)
    public void confirmationOfConfirmedUser() {
        User user = userManagement.addUser("pekim", "secret", "aaa@bbb.com", null);
        User confirmUser = userManagement.getUserAwaitingConfirmation(user.getConfirmationKey());
        userManagement.confirmUser(confirmUser);
        userManagement.confirmUser(confirmUser);
    }

    @Test
    public void getExistingUser() {
        userManagement.addUser("pekim", "secret", "aaa@bbb.com", null);
        User gotUser = userManagement.getUser("pekim");
        assertEquals("pekim", gotUser.getUsername());
    }

    @Test(expected=UserNotFoundException.class)
    public void getNotExistingUser() {
        userManagement.getUser("pekim");
    }
    
    @Test
    public void allUsers() {
        userManagement.addUser("user1", "secret", "aaa@bbb.com", null);
        userManagement.addUser("user2", "secret", "aaa@bbb.com", null);
        
        List<User> users = userManagement.allUsers();
        assertEquals(2, users.size());
    }
    
//    @Test
//    public void temp() {
//        objectContainer = Db4o.openFile("test.db4o");
//
//        List<Object> objects = objectContainer.query(Object.class);
//        System.out.println(objects);
//        
//        List<User> users = objectContainer.query(User.class);
//        System.out.println(users);
//    }
}
