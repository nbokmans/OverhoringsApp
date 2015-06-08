package ica.han.oose.project.overhoorapp.util.genson;

import com.owlike.genson.Genson;

/**
 * A class that contains shared objects that should remain constant between files.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 2-6-2015
 */
public class Shared {

    /**
     * The Genson instance.
     */
    public static final Genson genson = new Genson();
    public static final Genson gensonSkipNulls = new Genson.Builder().setSkipNull(true).create();
}
