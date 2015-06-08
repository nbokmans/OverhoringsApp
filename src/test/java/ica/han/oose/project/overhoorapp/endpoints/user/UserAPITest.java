package ica.han.oose.project.overhoorapp.endpoints.user;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import ica.han.oose.project.overhoorapp.util.Utilities;

/**
 * @author Niels Bokmans
 * @version 1.0
 * @since 1-6-2015
 */
public class UserAPITest {

    @Test
    public void testCanRegister() {
        Assert.assertTrue(UserAPI.register("Test", " Registratie", new Utilities().generateRandomString(8) + "@hotmail.com", "qwerty12"));
    }

    @Test
    public void testFindUserInfo() {
        Assert.assertEquals(UserAPI.getUserInfo().toString(), "Roy van Keysteren");
    }

    @Test
    public void testFindUserByUsername() {
        Assert.assertTrue(UserAPI.findUserByUsername("roy").size() > 0);
    }
}
