package uk.co.pekim.site.test.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Loginout {
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
