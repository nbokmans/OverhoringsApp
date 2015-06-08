package ica.han.oose.project.overhoorapp.exceptions;

/**
 * Throws an exception when looking up a summary for a topic object ID.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 4-6-2015
 */
public class SummaryNotFoundException extends RuntimeException {

    public SummaryNotFoundException() {
        super("Summary kon niet gevonden worden!");
    }

    public SummaryNotFoundException(final String topicObjectID) {
        super(String.format("Summary kon niet gevonden worden voor topic id: %s", topicObjectID));
    }
}
