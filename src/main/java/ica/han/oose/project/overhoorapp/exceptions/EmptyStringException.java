package ica.han.oose.project.overhoorapp.exceptions;

/**
 * Throws Exception.
 *
 * @author Wilko Zonnenberg
 * @version 1.0
 * @since 22-4-2015
 */
public class EmptyStringException extends RuntimeException {


    //Parameterless Constructor
    public EmptyStringException() {
    }

    //Constructor that accepts a message
    public EmptyStringException(String message) {
        super(message);
    }


}
