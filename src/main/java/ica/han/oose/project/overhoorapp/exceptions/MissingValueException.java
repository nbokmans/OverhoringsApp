package ica.han.oose.project.overhoorapp.exceptions;

/**
 * Throws a missing value exception when trying to use builders to generate JSON.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 19-5-2015
 */
public class MissingValueException extends RuntimeException {

    public MissingValueException() {
        super("Missing values! Can't serialize into JSON.");
    }

    public MissingValueException(String message) {
        super("Missing values: " + message);
    }
}
