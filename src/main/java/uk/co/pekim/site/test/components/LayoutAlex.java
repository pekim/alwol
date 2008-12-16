package uk.co.pekim.site.test.components;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

@IncludeStylesheet({
    "css/reset.css",
    "css/layout.css"
    })
public class LayoutAlex {
    @Parameter(required = true, defaultPrefix = "literal")
    @Property(read = true, write = false)
    @SuppressWarnings("unused")
    private String title;
}
