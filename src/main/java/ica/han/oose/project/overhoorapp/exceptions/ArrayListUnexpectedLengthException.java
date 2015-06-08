package ica.han.oose.project.overhoorapp.exceptions;

/**
 * Created by coen on 17-4-2015.
 */
public class ArrayListUnexpectedLengthException extends RuntimeException {
    //Parameterless Constructor
    public ArrayListUnexpectedLengthException() {
    }

    //Constructor that accepts a message
    public ArrayListUnexpectedLengthException(String message) {
        super(message);
    }
}
