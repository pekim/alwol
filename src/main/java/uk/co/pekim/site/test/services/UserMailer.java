package uk.co.pekim.site.test.services;

import org.apache.tapestry5.ComponentResources;

import uk.co.pekim.site.test.model.User;

public interface UserMailer {
    public void sendRegistrationConfirmationEmail(User user, ComponentResources componentResources);
}
