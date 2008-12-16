package uk.co.pekim.site.test.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Value;

import uk.co.pekim.site.test.model.User;
import uk.co.pekim.site.test.pages.VerifyNewUser;

public class UserMailerImpl implements UserMailer {
    private final Mailer mailer;
    private final TemplateEngine templateEngine;

    @Inject
    @Value(value = "${uk.co.pekim.site.url-prefix}")
    private String urlPrefix;
    
    public UserMailerImpl(Mailer mailer, TemplateEngine templateEngine) {
        this.mailer = mailer;
        this.templateEngine = templateEngine;
    }

    public void sendRegistrationConfirmationEmail(User user, ComponentResources componentResources) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", user);
        model.put("comfirmationLink", getConfirmationLink(user, componentResources));

        String textContent = templateEngine.processTemplate("requestRegistrationConfirmation-text.ftl", model);
        String htmlContent = templateEngine.processTemplate("requestRegistrationConfirmation-html.ftl", model);
        mailer.sendEmail(user.getEmailAddress(), "Confirm new user " + user.getUsername(), textContent, htmlContent);
    }

    private String getConfirmationLink(User user, ComponentResources componentResources) {
        String pageLink = componentResources.createPageLink(VerifyNewUser.class, true, user.getConfirmationKey())
                .toAbsoluteURI();
        return urlPrefix + pageLink;
    }
}
