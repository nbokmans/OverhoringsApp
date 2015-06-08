package ica.han.oose.project.overhoorapp.account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates given strings.
 *
 * @author Wilko Zonnenberg
 * @version 1.0
 * @since 22-4-2015
 */

public class AccountValidator {


    /**
     * @param firstName      firstName of the user
     * @param lastName       LastName of the user
     * @param email          Email of the user.
     * @param password       Password user filled in in register form.
     * @param passwordRepeat Password user filled in to confirm password.
     * @return <tt>true</tt> if the credentials are valid <tt>false</tt> otherwise.
     */
    public boolean validateCredentials(String firstName, String lastName, String email, String password, String passwordRepeat) {
        return validatePassword(password, passwordRepeat) && validateName(firstName) && validateName(lastName) && validateEmail(email);
    }


    /**
     * validates both register passwords:
     * -Equal
     * -not empty
     *
     * @param password       Password user filled in in register form.
     * @param passwordRepeat Repeated password user filled in in register form.
     * @return <tt>true</tt> if the password is valid, <tt>false</tt> otherwise.
     */
    public boolean validatePassword(String password, String passwordRepeat) {
        return password.length() > 5 && password.equals(passwordRepeat);
    }

    /**
     * Checks if a name has no invalid characters. Characters allowed: aA-zZ 0-9.
     *
     * @param name name to check if valid.
     * @return <tt>true</tt> if the name is valid, <tt>false</tt> if invalid.
     */
    public boolean validateName(String name) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        return !m.find() && !name.isEmpty();
    }

    /**
     * checks if an email is valid.
     *
     * @param email email to check.
     * @return <tt>true</tt> if email is valid, <tt>false</tt> if email is invalid.
     */
    public boolean validateEmail(String email) {
        Pattern ptr = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return ptr.matcher(email).matches();
    }
}
