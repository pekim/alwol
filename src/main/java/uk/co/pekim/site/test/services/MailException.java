package uk.co.pekim.site.test.services;


@SuppressWarnings("serial")
public class MailException extends RuntimeException {
    public MailException(String message, Exception exception) {
        super(message, exception);
    }
}
