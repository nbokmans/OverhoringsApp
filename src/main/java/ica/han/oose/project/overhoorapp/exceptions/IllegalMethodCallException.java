package ica.han.oose.project.overhoorapp.exceptions;

/**
 * Created by coen on 17-4-2015.
 */
public class IllegalMethodCallException extends RuntimeException {

    public IllegalMethodCallException() {
        super("Cannot call this method when inheriting from NavigationDrawerActivity!");
    }

    public IllegalMethodCallException(String message) {
        super(message);
    }
}
