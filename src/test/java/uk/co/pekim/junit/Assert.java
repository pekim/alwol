package uk.co.pekim.junit;

public class Assert {
    /**
     * Convenience assert equals method that avoids the autoboxing with JUnit one.
     * 
     * @param expected
     * @param actual
     */
    static public void assertEquals(int expected, int actual) {
        org.junit.Assert.assertEquals(new Integer(expected), new Integer(actual));
    }
}
