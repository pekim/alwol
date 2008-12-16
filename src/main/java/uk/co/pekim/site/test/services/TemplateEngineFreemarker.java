package uk.co.pekim.site.test.services;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class TemplateEngineFreemarker implements TemplateEngine {
    private final Configuration configuration;

    public TemplateEngineFreemarker() {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
        configuration.setObjectWrapper(new DefaultObjectWrapper());
    }

    public String processTemplate(String templateName, Map<String, Object> model) {
        try {
            Template template = configuration.getTemplate(templateName);
            StringWriter processedOutput = new StringWriter();
            template.process(model, processedOutput);
            
            return processedOutput.getBuffer().toString();
        } catch (IOException exception) {
            throw new TemplateException("Failed to process template " + templateName, exception);
        } catch (freemarker.template.TemplateException exception) {
            throw new TemplateException("Failed to process template " + templateName, exception);
        }
    }
}
