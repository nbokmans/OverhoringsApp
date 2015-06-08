package ica.han.oose.project.overhoorapp.util;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UtilitiesTest extends TestCase {

    private Utilities utilities;

    @Before
    public void setUp() {
        utilities = mock(Utilities.class);
        when(utilities.generateRandomString(any(Integer.class))).thenReturn("ABCDEF");
    }

    @Test
    public void testGenerateRandomString() throws Exception {
        assertEquals("ABCDEF", utilities.generateRandomString(6));
    }
}