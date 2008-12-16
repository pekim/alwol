package uk.co.pekim.site.test.pages;

import java.util.regex.Pattern;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Value;

import uk.co.pekim.site.test.model.User;
import uk.co.pekim.site.test.services.UserManagement;

/**
 * Register a new user.
 */
public class Register {
    private static final int MINIMUM_USERNAME_LENGTH = 4;
    private static final int MINIMUM_PASSWORD_LENGTH = 4;
    
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z,0-9,_]+$");

    @Inject
    private UserManagement userManagement;

    @InjectPage
    private RegistrationSubmitted registrationSubmitted;

    @Inject
    private ComponentResources componentResources;
    
    @Persist
    @Validate("required,minlength=" + MINIMUM_USERNAME_LENGTH)
    private String username;

    @Persist
    @Validate("required,minlength=" + MINIMUM_PASSWORD_LENGTH)
    private String password1;

    @Persist
    @Validate("required,minlength=" + MINIMUM_PASSWORD_LENGTH)
    private String password2;

    @Persist
    @Validate("required,email")
    private String emailAddress1;

    @Persist
    @Validate("required,email")
    private String emailAddress2;
    
    @SuppressWarnings("unused")
    @Property(read = true, write = false)
    private final int minUsernameLength = MINIMUM_USERNAME_LENGTH;
    
    @SuppressWarnings("unused")
    @Property(read = true, write = false)
    private final int minPasswordLength = MINIMUM_PASSWORD_LENGTH;
    
    @Component(id="registerUser")
    private Form form;

    @Component(id="username")
    private TextField usernameField;

    @Component(id="password1")
    private PasswordField passwordField1;
    
    @Component(id="emailAddress1")
    private TextField emailAddressField1;

    @OnEvent(component="registerUser", value=EventConstants.VALIDATE_FORM)
    void onValidateForm() {
        if (!emailAddress1.equals(emailAddress2)) {
            form.recordError(emailAddressField1, "Email addresses differ.");
        }
        
        if (!password1.equals(password2)) {
            form.recordError(passwordField1, "Passwords differ.");
        }
        
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            form.recordError(usernameField, "Username contains invalid characters.");
        }
        
        if (userManagement.isUser(username)) {
            form.recordError(usernameField, "Username is already in use");
        }
    }

    @OnEvent(component="registerUser", value=EventConstants.SUCCESS)
    RegistrationSubmitted onSuccess() {
        userManagement.addUser(username, password1, emailAddress1, componentResources);

        registrationSubmitted.setUsername(username);
        return registrationSubmitted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmailAddress1() {
        return emailAddress1;
    }

    public void setEmailAddress1(String emailAddress1) {
        this.emailAddress1 = emailAddress1;
    }

    public String getEmailAddress2() {
        return emailAddress2;
    }

    public void setEmailAddress2(String emailAddress2) {
        this.emailAddress2 = emailAddress2;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public void setEmailAddressField1(TextField emailAddressField1) {
        this.emailAddressField1 = emailAddressField1;
    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }

    public void setPasswordField1(PasswordField passwordField1) {
        this.passwordField1 = passwordField1;
    }

    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
    }

    public void setRegistrationSubmitted(RegistrationSubmitted registrationSubmitted) {
        this.registrationSubmitted = registrationSubmitted;
    }
}
