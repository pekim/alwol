package uk.co.pekim.site.test.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.URLDataSource;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Value;
import org.slf4j.Logger;

public class MailerCommonsMail implements Mailer {
    private static final Pattern HTML_PATTERN = Pattern.compile("(<html\\b[^>]*>)", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE);
    private static final Pattern BODY_PATTERN = Pattern.compile("(<body\\b[^>]*>)", Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE);

    @Inject
    @Value("${uk.co.pekim.site.smtp.server}")
    private String server;

    @Inject
    @Value("${uk.co.pekim.site.smtp.username}")
    private String username;

    @Inject
    @Value("${uk.co.pekim.site.smtp.password}")
    private String password;

    @Inject
    @Value("${uk.co.pekim.site.smtp.from.email}")
    private String fromEmail;

    @Inject
    @Value("${uk.co.pekim.site.smtp.from.name}")
    private String fromName;

    /**
     * A convenient small, transparent, gif of Tapestry's.
     */
    @Inject
    @Path("classpath:org/apache/tapestry5/spacer.gif")
    private Asset spacer;

    private final Logger logger;

    public MailerCommonsMail(Logger logger) {
        this.logger = logger;
    }

    public void sendEmail(String to, String subject, String textMessage, String htmlMessage) {
        try {
            HtmlEmail email = new HtmlEmail();

            email.setHostName(server);
            email.setAuthentication(username, password);

            email.addTo(to);
            email.setFrom(fromEmail, fromName);
            email.setSubject(subject);

            htmlMessage = addImgTag(email, htmlMessage);

            email.setTextMsg(textMessage);
            email.setHtmlMsg(htmlMessage);

            email.send();
            logger.info("Mail sent to " + to);
        } catch (EmailException e) {
            throw new MailException("Failed to send mail", e);
        }
    }

    /**
     * Add a tiny, transparent, GIF image to the HTML.
     * 
     * This is necessary because Commons Mail appears to have bug that causes an
     * email with text and HTML content to be rendered as text (not HTML) in
     * most email clients. That is, the HTML content is ignored. See
     * http://issues.apache.org/jira/browse/EMAIL-80 .
     * 
     * @param email
     * 
     * @param html
     *            HTML to add the image to.
     * @return the amended HTML.
     * @throws EmailException
     */
    String addImgTag(HtmlEmail email, String html) throws EmailException {
        String spacerImg = getSpacerImg(email);

        // Add the <img> right after the opening <body> tag, or if there is
        // no <body> tag, then after the <html> tag.
        String modifiedHtml = html;

        Matcher matcher = BODY_PATTERN.matcher(html);
        if (matcher.find()) {
            modifiedHtml = matcher.replaceFirst("$1" + spacerImg);
        } else {
            matcher = HTML_PATTERN.matcher(html);
            if (matcher.find()) {
                modifiedHtml = matcher.replaceFirst("$1" + spacerImg);
            }
        }

        return modifiedHtml;
    }

    private String getSpacerImg(HtmlEmail email) throws EmailException {
        URLDataSource spacerDataSource = new URLDataSource(spacer.getResource().toURL());
        String spacerCid = email.embed(spacerDataSource, "spacer");
        return "<img src='cid:" + spacerCid + "' />";
    }

    public void setSpacer(Asset spacer) {
        this.spacer = spacer;
    }
}
