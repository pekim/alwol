package uk.co.pekim.site.test.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class User {
    private static final long MAXIMUM_CONFIRMATION_KEY = Long.MAX_VALUE;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final byte[] passwordDigest;

    private boolean confirmed;
    private final String confirmationKey;
    
    public User(String username, String password, String emailAddress) {
        this.username = username;
        this.firstName = null;
        this.lastName = null;
        this.emailAddress = emailAddress;
        this.passwordDigest = generatePasswordDigest(password);

        this.confirmed = false;
        this.confirmationKey = generateConfirmationKey();
    }

    private String generateConfirmationKey() {
        long confirmationNumber = (long) (Math.random() * MAXIMUM_CONFIRMATION_KEY);
        return Long.toHexString(confirmationNumber);
    }

    private byte[] generatePasswordDigest(String password) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(password.getBytes());
            return algorithm.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to digest password", e);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public void confirm() {
        confirmed = true;
    }
    
    public String getConfirmationKey() {
        return confirmationKey;
    }
    
    public boolean verifyPassword(String password) {
        return Arrays.equals(generatePasswordDigest(password), passwordDigest);
    }

    @Override
    public String toString() {
        return "User : " + username;
    }
}
