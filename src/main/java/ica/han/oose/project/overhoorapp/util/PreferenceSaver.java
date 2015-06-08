package ica.han.oose.project.overhoorapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by coen on 22-5-2015.
 */

public class PreferenceSaver {
    /**
     * SAVE EXAMPLE
     * PreferenceSaver.saveToPrefs(this, "username", user);
     * READ EXAMPLE
     * String loggedInUserName = PreferenceSaver.getFromPrefs(this, "username", null);
     */
    public static final String PREFS_LOGIN_USERNAME_KEY = "__USERNAME__";
    public static final String PREFS_LOGIN_PASSWORD_KEY = "__PASSWORD__";
    private static ArrayList<Context> usedContext = new ArrayList<Context>();

    /**
     * Called to save supplied value in shared preferences against given key.
     *
     * @param context Context of caller activity
     * @param key     Key of value to save against
     * @param value   Value to save
     */
    public static void saveToPrefs(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

        if (!usedContext.contains(context)) {
            usedContext.add(context);
        }
    }

    /**
     * Called to retrieve required value from shared preferences, identified by given key.
     * Default value will be returned of no value found or error occurred.
     *
     * @param context      Context of caller activity
     * @param key          Key to find value against
     * @param defaultValue Value to return if no data found against given key
     * @return Return the value found against given key, default if not found or any error occurs
     */
    public static String getFromPrefs(Context context, String key, String defaultValue) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return sharedPrefs.getString(key, defaultValue);
        } catch (Exception e) {
            Log.e("PreferenceSaver", e.getMessage(), e);
            return defaultValue;
        }
    }

    public void deleteAllPrefs() {
        for (Context context : usedContext) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            final SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
        }


    }


}

/*SAVE EXAMPLE
* PreferenceSaver.saveToPrefs(this, "username", user);
*/

/* READ EXAMPLE
*  String loggedInUserName = PreferenceSaver.getFromPrefs(this, "username", null);
*/