package ica.han.oose.project.overhoorapp.util;

import junit.framework.TestCase;

import org.junit.Before;
import org.mockito.Mockito;

public class TokenParserTest extends TestCase {

    private TokenParser parser;

    @Before
    public void setUp() {
        parser = Mockito.mock(TokenParser.class);
        Mockito.when(parser.get()).thenReturn("41bc0c8d-f57b-4057-a552-724c1a30db92");
    }

    public void testGet() throws Exception {
        assertEquals("41bc0c8d-f57b-4057-a552-724c1a30db92", parser.get());
    }
}