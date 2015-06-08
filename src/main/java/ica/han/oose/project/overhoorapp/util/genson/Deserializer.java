package ica.han.oose.project.overhoorapp.util.genson;

/**
 * Allows for easy deserialization (the conversion of JSON to Java objects).
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 1-5-2015
 */
public class Deserializer {

    /**
     * Private constructor to prevent instantiation.
     */
    private Deserializer() {
    }

    /**
     * Deserializes JSON into a Java object.
     *
     * @param json       The JSON to deserialize.
     * @param superclass The model to deserialize the JSON into.
     * @return An object that is an instanceof superclass.
     */
    public static Object deserialize(final String json, final Class superclass) {
        return Shared.genson.deserialize(json, superclass);
    }

}
