package ica.han.oose.project.overhoorapp.jsonmodel.wrappers;

import com.owlike.genson.Genson;

import org.junit.Before;
import org.junit.Test;

import ica.han.oose.project.overhoorapp.json.models.tokens.read.Tokens;
import ica.han.oose.project.overhoorapp.json.wrappers.Token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TokenTest {
    private static final Genson GENSON = new Genson();
    private String JSON_TEST_DATA = "{\"token\":{\"token\":\"638da731-1445-4c61-8cfc-d46f7bb950e2\"}}";
    private Token testToken;

    @Before
    public void setUp() {
        final Tokens tokens = GENSON.deserialize(JSON_TEST_DATA, Tokens.class);
        testToken = new Token(tokens);
    }

    @Test
    public void testGetToken() {
        assertEquals("638da731-1445-4c61-8cfc-d46f7bb950e2", testToken.getToken());
    }

    @Test
    public void testTokenIsValidUUID() {
        assertTrue(testToken.getToken().matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}"));
    }
}