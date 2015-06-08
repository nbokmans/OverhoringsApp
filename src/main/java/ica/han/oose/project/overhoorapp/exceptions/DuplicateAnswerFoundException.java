package ica.han.oose.project.overhoorapp.exceptions;

/**
 * Created by coen on 17-4-2015.
 */
public class DuplicateAnswerFoundException extends RuntimeException {
    //Parameterless Constructor
    public DuplicateAnswerFoundException() {
    }

    //Constructor that accepts a message
    public DuplicateAnswerFoundException(String message) {
        super(message);
    }

}
