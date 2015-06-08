package ica.han.oose.project.overhoorapp.exceptions;

/**
 * Throws Exception.
 *
 * @author Coen Smid
 * @version 1.0
 * @since 22-4-2015
 */
public class PersistenceAdapterNotSetException extends RuntimeException {


    //Parameterless Constructor
    public PersistenceAdapterNotSetException() {
    }

    //Constructor that accepts a message
    public PersistenceAdapterNotSetException(String message) {
        super(message);
    }


}
