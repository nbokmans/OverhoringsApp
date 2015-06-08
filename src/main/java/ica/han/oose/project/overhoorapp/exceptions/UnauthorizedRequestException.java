package ica.han.oose.project.overhoorapp.exceptions;

/**
 * Throws exception when the application tries to make a request while not being logged in..
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 18-5-2015
 */
public class UnauthorizedRequestException extends Exception {
    public UnauthorizedRequestException() {
    }

    public UnauthorizedRequestException(String message) {
        super(message);
    }

}
