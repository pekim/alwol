package uk.co.pekim.site.test.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import uk.co.pekim.site.test.model.User;
import uk.co.pekim.site.test.services.UserManagement;

public class VerifyNewUser {
    @Inject
    private UserManagement userManagement;

    @Property(read=true, write=false)
    private User user;
    
    public void onActivate(String id) {
        user = userManagement.getUserAwaitingConfirmation(id);
        if (user != null) {
            userManagement.confirmUser(user);
        } else {
            user = null;
        }
    }
}
