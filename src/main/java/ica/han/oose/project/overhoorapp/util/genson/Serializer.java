package ica.han.oose.project.overhoorapp.util.genson;

/**
 * Allows for easy serialization (the conversion of Java objects to JSON).
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 2-6-2015
 */
public class Serializer {

    /**
     * Private constructor to prevent instantiation.
     */
    private Serializer() {

    }

    /**
     * Serializes the object.
     *
     * @param o The object.
     * @return The object in JSON format.
     */
    public static String serialize(final Object o) {
        return Shared.genson.serialize(o);
    }

    public static String serializeSkipNulls(final Object o) {
        return Shared.gensonSkipNulls.serialize(o);
    }
}
