package uk.co.pekim.site.test.services;

@SuppressWarnings("serial")
public class TemplateException extends RuntimeException {
    public TemplateException(String message, Exception cause) {
        super(message, cause);
    }
}
