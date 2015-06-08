package ica.han.oose.project.overhoorapp.exceptions;

/**
 * Throws exception.
 *
 * @author Wilko Zonnenberg
 * @version 1.0
 * @since 24-4-2015
 */
public class IncorrectCredentialsException extends RuntimeException {
    //Parameterless Constructor
    public IncorrectCredentialsException() {
    }

    //Constructor that accepts a message
    public IncorrectCredentialsException(String message) {
        super(message);
    }

}
