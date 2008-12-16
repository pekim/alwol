package uk.co.pekim.site.test.services;

@SuppressWarnings("serial")
public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
