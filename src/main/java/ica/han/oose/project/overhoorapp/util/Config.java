package ica.han.oose.project.overhoorapp.util;

import com.owlike.genson.Genson;

public class Config {


    public static final String BASE_URL = "http://warm-island-9973.herokuapp.com";

    //public static final String BASE_URL = "http://195.169.162.216:9000";

    public static String username = "roy_vankeysteren@hotmail.com";
    public static String password = "qwerty12";

    public static final int TOKEN_TIMEOUT_MILLISECONDS = 3600000; //1 hour = 3.600.000 milliseconds.

    public static final Genson GENSON = new Genson();


    private Config() {

    }
}
