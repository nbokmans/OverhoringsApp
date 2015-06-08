package ica.han.oose.project.overhoorapp.account;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountValidatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    AccountValidator validator;

    @Before
    public void setUp() {
        validator = new AccountValidator();
    }

    @Test
    public void testEmptyMail() {
        assertFalse(validator.validateEmail(""));
    }

    @Test
    public void testCorrectMail() {
        assertTrue(validator.validateEmail("jan@hotmail.com"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(validator.validateEmail("klaassen"));
    }

    @Test
    public void testInvalidEmailNothingInFront() {
        assertFalse(validator.validateEmail("@klaassen.nl"));
    }

    @Test
    public void testInvalidEmailNothingBehind() {
        assertFalse(validator.validateEmail("klaassen@.nl"));
    }

    @Test
    public void testInvalidEmailNothingBehindDot() {
        assertFalse(validator.validateEmail("klaassen@pieters."));
    }

    @Test
    public void testValidEmail() {
        assertTrue(validator.validateEmail("klaasen@pieters.nl"));
    }

    @Test
    public void testEmptyName() {
        assertFalse(validator.validateName(""));
    }

    @Test
    public void testInvalidName() {
        assertFalse(validator.validateName("kj$hai"));
    }

    @Test
    public void testEmptyPassword() {
        assertFalse(validator.validatePassword("", "jan"));
    }

    @Test
    public void testEmptyRepeatedPassword() {
        assertFalse(validator.validatePassword("jan", ""));
    }

    @Test
    public void testPassword() {
        assertTrue(validator.validatePassword("cola12", "cola12"));
    }

    @Test
    public void testEmptyPasswords() {
        assertFalse(validator.validatePassword("", ""));
    }

    @Test
    public void testWrongPassword() {
        assertFalse(validator.validatePassword("jansen", "janssen"));
    }

    @Test
    public void testShortPassword() {
        assertFalse(validator.validatePassword("jan", "jan"));
    }


    @Test
    public void testValidName() {
        assertTrue(validator.validateName("testnaam"));
    }

}