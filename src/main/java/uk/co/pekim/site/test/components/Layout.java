package uk.co.pekim.site.test.components;

import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

@IncludeStylesheet({
    "css/reset.css",
    "css/site.css",
    "css/layout.css",
    "css/top.css"
    })
public class Layout {
    @Parameter(required = true, defaultPrefix = "literal")
    @Property(read = true, write = false)
    @SuppressWarnings("unused")
    private String title;

    @Parameter(required = false, defaultPrefix = "literal")
    @Property(read = true, write = false)
    @SuppressWarnings("unused")
    private final boolean focusLoginForm = true;

    @SuppressWarnings("unused")
    @Property
    private String username;

    @SuppressWarnings("unused")
    @Property
    private String password;
}
