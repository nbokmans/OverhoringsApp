package ica.han.oose.project.overhoorapp.exceptions;

/**
 * Exception to throw when the JSON post request content is empty.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 1-5-2015
 */
public class EmptyJSONRequestSubmissionException extends RuntimeException {

    public EmptyJSONRequestSubmissionException() {
        super("You must add JSON to the JSONRequestBuilder before submitting it! See setContent.");
    }

}
