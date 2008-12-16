package uk.co.pekim.site.test.services;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TemplateEngineFreemarkerTest {
    private TemplateEngine templateEngine;

    @Before
    public void setup() {
        templateEngine = new TemplateEngineFreemarker();
    }
    
    @Test(expected=TemplateException.class)
    public void templateNotFound() {
        templateEngine.processTemplate("bad", null);
    }
    
    @Test
    public void templateProcess() {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("v1", "value1");
        String processed = templateEngine.processTemplate("test.ftl", model);
        
        assertTrue(StringUtils.contains(processed, "test"));
        assertTrue(StringUtils.contains(processed, "value1"));
    }
}
