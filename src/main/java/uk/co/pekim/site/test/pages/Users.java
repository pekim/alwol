package uk.co.pekim.site.test.pages;

import java.util.List;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;

import uk.co.pekim.site.test.model.User;
import uk.co.pekim.site.test.services.UserManagement;


/**
 * List all users.
 */
public class Users {
    @Inject
    private UserManagement userManagement;
    
    @SuppressWarnings("unused")
    @Property
    private User user;

    @InjectComponent
    private Grid grid;

    private List<User> users;
    
    void setupRender() {
        users = userManagement.allUsers();
         
        if (grid.getSortModel().getSortConstraints().isEmpty()) {
            grid.getSortModel().updateSort("username");
        }
    }

    public List<User> getUsers() {
        return users;
    }
}
