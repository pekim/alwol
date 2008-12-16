package uk.co.pekim.site.test.services;

import java.util.List;

import org.apache.tapestry5.ComponentResources;

import uk.co.pekim.site.test.model.User;

/**
 * A service that provides access to {@link User}s.
 * 
 * @author Mike D Pilsbury
 */
public interface UserManagement {
    public User addUser(String username, String password, String emailAddress, ComponentResources componentResources);

    public boolean isUser(String string);
    
    public User getUserAwaitingConfirmation(String confirmationKey);

    public void confirmUser(User user) throws UserConfirmationException;

    public User getUser(String username);

    /**
     * All {@link User}s, whatever their state.
     * 
     * @return a list of the users.
     */
    public List<User> allUsers();
}
