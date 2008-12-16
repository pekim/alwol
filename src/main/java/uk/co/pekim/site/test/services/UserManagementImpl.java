package uk.co.pekim.site.test.services;

import java.util.List;

import org.apache.tapestry5.ComponentResources;

import uk.co.pekim.site.test.model.User;

import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

public class UserManagementImpl implements UserManagement {
    private final ObjectContainer objectContainer;
    private final UserMailer userMailer;

    public UserManagementImpl(ObjectContainer objectContainer, UserMailer userMailer) {
        this.objectContainer = objectContainer;
        this.userMailer = userMailer;
    }
    
    public User addUser(String username, String password, String emailAddress, ComponentResources componentResources) throws UserException {
        if (isUser(username)) {
            throw new UserException("User already exists");
        }
        
        User user = new User(username, password, emailAddress);
        objectContainer.store(user);
        
        userMailer.sendRegistrationConfirmationEmail(user, componentResources);
        
        return user;
    }

    public boolean isUser(final String username) {
        return findUser(username) != null;
    }
    
    public void confirmUser(User user) throws UserConfirmationException {
        if (user.isConfirmed()) {
            throw new UserConfirmationException("User " + user.getUsername() + " is already confirmed");
        }
        
        user.confirm();
        objectContainer.store(user);
    }
    
    @SuppressWarnings("serial")
    public User getUserAwaitingConfirmation(final String confirmationKey) {
        List<User> users = objectContainer.query(new Predicate<User>() {
            @Override
            public boolean match(User user) {
                return user.getConfirmationKey().equals(confirmationKey) &&
                    !user.isConfirmed();
            }
        });
        
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User getUser(String username) throws UserNotFoundException {
        User user = findUser(username);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("No user with username " + username);
        }
    }

    @SuppressWarnings("serial")
    private User findUser(final String username) {
        List<User> users = objectContainer.query(new Predicate<User>() {
            @Override
            public boolean match(User user) {
                return user.getUsername().equals(username);
            }
        });
        
        if (users.isEmpty()) {
            return null;
        }
        
        return users.get(0);
    }

    public List<User> allUsers() {
        return objectContainer.query(User.class);
    }
}
