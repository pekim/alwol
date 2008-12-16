package uk.co.pekim.site.test.pages;

import javax.activation.URLDataSource;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;

import uk.co.pekim.site.test.model.User;
import uk.co.pekim.site.test.services.UserManagement;

/**
 * Register a new user.
 */
public class RegistrationSubmitted {
    @Persist
    private String username;

    @Inject
    private UserManagement userManagement;
    
    private User user;
    
    public void setUsername(String username) {
        this.username = username;
    }

    void onActivate() {
        user = userManagement.getUser(username);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
