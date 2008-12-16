package uk.co.pekim.site.test.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

import uk.co.pekim.site.test.services.Mailer;
import uk.co.pekim.tapestry.db4o.Db4oObjectContainer;

public class Login {
    @Property
    private String _firstName;

    @Property
    private String _lastName;

    @Component(id = "names")
    private Form _form;

    @Component(id = "firstName")
    private TextField _firstNameField;

    @Component(id = "lastName")
    private TextField _lastNameField;

    @InjectPage
    private Index home;
    
    @Inject
    private Mailer mailer;
    
    @Inject
    private Db4oObjectContainer objectContainer;

    void onValidateForm() {
        if (_firstName == null || _firstName.trim().equals("")) {
            _form.recordError(_firstNameField, "First Name is required.");
        }
        if (_lastName == null || _lastName.trim().equals("")) {
            _form.recordError(_lastNameField, "Last Name is required.");
        }
    }

    Object onSuccess() {
        objectContainer.commit();
        
        //home.setName(_firstName + " " + _lastName + " : " + mailer.getServer());
        return home;
    }
}
