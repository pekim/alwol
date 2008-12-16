package uk.co.pekim.site.test.services;

import java.util.Map;

public interface TemplateEngine {
    public String processTemplate(String templateName, Map<String, Object> model);
}
