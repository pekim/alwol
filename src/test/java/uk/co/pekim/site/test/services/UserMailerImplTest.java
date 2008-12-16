package uk.co.pekim.site.test.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static uk.co.pekim.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.db4o.ObjectContainer;
import com.db4o.ext.ExtDb4o;
import com.db4o.ext.MemoryFile;

public class UserMailerImplTest {
    private UserMailerImpl userMailer;
    private Mailer mailer;
    private TemplateEngine templateEngine;

    @Before
    public void setup() {
        mailer = new Mailer() {
            public String textContent;
            private String htmlContent;

            public void sendEmail(String to, String subject, String textContent, String htmlContent) {
                this.textContent = textContent;
                this.htmlContent = htmlContent;
            }
        };
        templateEngine = new TemplateEngineFreemarker();
        userMailer = new UserMailerImpl(mailer, templateEngine);
    }
    
    @Test
    public void simple() {
//        User user = new User("username", "password", "a@b.c");
//        userMailer.sendRegistrationConfirmationEmail(user);
        
//        assertEquals("ss", mailer.textContent);
    }
}
