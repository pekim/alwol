package uk.co.pekim.site.test.pages;

import java.util.Date;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Value;

/**
 * Start page of application test-site.
 */
public class Index {
    @Property
    @Persist
    private int index;
    
    @InjectComponent
    private Zone output;
   
    @Inject
    private Block resetContent;

    @Persist
    private String name;
    
    @InjectComponent
    private ActionLink verify;
    
    @Inject
    private ComponentResources componentResources;
    
    @Inject
    @Value(value="${uk.co.pekim.site.url-prefix}")
    private String urlPrefix;
    
    public Date getCurrentTime() 
	{ 
		return new Date(); 
	}
    
    Object onActionFromIncrement() {
        index++;
        return output.getBody();
    }
    
    Object onActionFromReset() {
        index = 0;
        return resetContent;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getVerificationId() {
        return "QWE344FF3";
    }
    
    public void onActionFromVerify(String id) {
        System.out.println("link : " + urlPrefix + componentResources.createPageLink(VerifyNewUser.class, true, getVerificationId()).toAbsoluteURI());
    }
}
