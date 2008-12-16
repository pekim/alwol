package uk.co.pekim.site.test.services;

@SuppressWarnings("serial")
public class UserNotFoundException extends UserException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
