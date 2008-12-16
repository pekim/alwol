package uk.co.pekim.site.test.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import uk.co.pekim.tapestry.db4o.Db4oObjectContainer;

import com.db4o.ext.SystemInfo;


/**
 * List all users.
 */
public class DatabaseInfo {
    @Inject
    private Db4oObjectContainer objectContainer;
    
    @SuppressWarnings("unused")
    @Property
    private long freespaceSize;

    @SuppressWarnings("unused")
    @Property
    private int freespaceEntryCount;

    @SuppressWarnings("unused")
    @Property
    private long totalSize;

    void setupRender() {
        SystemInfo systemInfo = objectContainer.ext().systemInfo();
        freespaceSize = systemInfo.freespaceSize();
        freespaceEntryCount = systemInfo.freespaceEntryCount();
        totalSize = systemInfo.totalSize();
    }
}
