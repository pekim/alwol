package uk.co.pekim.site.test.pages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static uk.co.pekim.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.junit.Before;
import org.junit.Test;

import uk.co.pekim.site.test.model.User;
import uk.co.pekim.site.test.services.UserConfirmationException;
import uk.co.pekim.site.test.services.UserException;
import uk.co.pekim.site.test.services.UserManagement;

public class RegisterTest {
    private Register register;
    private TestForm form;
    private UserManagement userManagement;
    
    private TextField usernameField;
    private TextField emailAddressField1;
    private PasswordField passwordField1;
    
    class TestForm extends Form {
        Map<Field, String> errors = new HashMap<Field, String>();
        
        @Override
        public void recordError(Field field, String errorMessage) {
            errors.put(field, errorMessage);
        }

        @Override
        public boolean getHasErrors() {
            return !errors.isEmpty();
        }
    }
    
    class TestUserManagement implements UserManagement {
        List<String> usernames = new ArrayList<String>();
        
        public User addUser(String username, String password, String emailAddress, ComponentResources componentResources) {
            usernames.add(username);
            return null;
        }

        public User confirmUser(String confirmationKey) throws UserException {
            return null;
        }

        public boolean isUser(String username) {
            return usernames.contains(username);
        }

        public User getUser(String username) {
            return null;
        }

        public List<User> allUsers() {
            return null;
        }

        public void confirmUser(User user) throws UserConfirmationException {
            //
        }

        public User getUserAwaitingConfirmation(String confirmationKey) {
            return null;
        }
    }
    
    @Before
    public void setup() {
        register = new Register();

        form = new TestForm();
        userManagement = new TestUserManagement();
        
        usernameField = new TextField();
        emailAddressField1 = new TextField();
        passwordField1 = new PasswordField();

        register.setForm(form);
        register.setUserManagement(userManagement);
        register.setRegistrationSubmitted(new RegistrationSubmitted());

        register.setUsernameField(usernameField);
        register.setPasswordField1(passwordField1);
        register.setEmailAddressField1(emailAddressField1);
    }
    
    @Test
    public void testValidateNoErrors() {
        register.setUsername("username_1");
        register.setEmailAddress1("email");
        register.setEmailAddress2("email");
        register.setPassword1("pw");
        register.setPassword2("pw");
        register.onValidateForm();
        
        assertTrue(form.errors.isEmpty());
    }
    
    @Test
    public void testValidateUnequalEmailAddresses() {
        register.setUsername("username_1");
        register.setEmailAddress1("email");
        register.setEmailAddress2("email+");
        register.setPassword1("pw");
        register.setPassword2("pw");
        register.onValidateForm();
        
        assertEquals(1, form.errors.size());
        assertTrue(form.errors.containsKey(emailAddressField1));
    }
    
    @Test
    public void testValidateUnequalPasswords() {
        register.setUsername("username_1");
        register.setEmailAddress1("email");
        register.setEmailAddress2("email");
        register.setPassword1("pw");
        register.setPassword2("pw+");
        register.onValidateForm();
        
        assertEquals(1, form.errors.size());
        assertTrue(form.errors.containsKey(passwordField1));
    }
    
    @Test
    public void testBadUsername() {
        register.setUsername("username ");
        register.setEmailAddress1("email");
        register.setEmailAddress2("email");
        register.setPassword1("pw");
        register.setPassword2("pw");
        register.onValidateForm();
        
        assertEquals(1, form.errors.size());
        assertTrue(form.errors.containsKey(usernameField));
    }
    
    @Test
    public void testValidateMultipleErrors() {
        register.setUsername("username ");
        register.setEmailAddress1("email");
        register.setEmailAddress2("email+");
        register.setPassword1("pw");
        register.setPassword2("pw+");
        register.onValidateForm();
        
        assertEquals(3, form.errors.size());
    }
    
    @Test
    public void testNewUser() {
        register.setUsername("username_1");
        register.setEmailAddress1("email");
        register.setPassword1("pw");
        register.onSuccess();
        
        assertTrue(userManagement.isUser("username_1"));
    }

    @Test
    public void testValidatePageSwitch() {
        register.setUsername("username");
        
        assertEquals(RegistrationSubmitted.class, register.onSuccess().getClass());
    }
}
