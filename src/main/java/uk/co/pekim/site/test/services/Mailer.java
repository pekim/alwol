package uk.co.pekim.site.test.services;

public interface Mailer {
    public void sendEmail(String to, String subject, String textContent, String htmlContent);
}
