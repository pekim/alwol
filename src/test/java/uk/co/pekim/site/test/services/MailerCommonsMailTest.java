package uk.co.pekim.site.test.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.ClasspathResource;
import org.junit.Before;
import org.junit.Test;

public class MailerCommonsMailTest {
    private MailerCommonsMail mailer;

    @Before
    public void setup() {
        mailer = new MailerCommonsMail(null);
        mailer.setSpacer(new Asset() {
            public Resource getResource() {
                return new ClasspathResource("");
            }

            public String toClientURL() {
                return null;
            }
        });
    }
    
    @Test
    public void noBodyOrHtml() throws EmailException {
        assertNotAdded("blah");
    }

    @Test
    public void body() throws EmailException {
        assertAdded("xx <body> xx");
    }

    @Test
    public void bodyWithSpace() throws EmailException {
        assertAdded("xx <body > xx");
    }

    @Test
    public void html() throws EmailException {
        assertAdded("xx <html> xx");
    }

    @Test
    public void htmlWithSpace() throws EmailException {
        assertAdded("xx <html > xx");
    }

    public void assertAdded(String html) throws EmailException {
        String modifiedHtml = addImgTag(html);
        assertTrue(StringUtils.contains(modifiedHtml, "<img"));
    }

    public void assertNotAdded(String html) throws EmailException {
        String modifiedHtml = addImgTag(html);
        assertFalse(StringUtils.contains(modifiedHtml, "<img"));
    }

    private String addImgTag(String html) throws EmailException {
        return mailer.addImgTag(new HtmlEmail(), html);
    }
}
