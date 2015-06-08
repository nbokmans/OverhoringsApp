package ica.han.oose.project.overhoorapp.endpoints;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import ica.han.oose.project.overhoorapp.exceptions.UnauthorizedRequestException;
import ica.han.oose.project.overhoorapp.util.requestbuilder.AbstractRequestBuilder;

/**
 * @author Niels
 * @version 1.0
 * @since 1-6-2015
 */
public class Shared {

    /**
     * Submits a builder. Created a method to minimize redundancy.
     *
     * @param builder The builder to submit.
     */
    public static void execute(final AbstractRequestBuilder builder) {
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    builder.submit();
                } catch (UnauthorizedRequestException e) {
                    Log.e("Shared", e.getMessage(), e);
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public static void doInBackground(final AbstractRequestBuilder builder) {
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    builder.submit();
                } catch (UnauthorizedRequestException e) {
                    Log.e("Shared", e.getMessage(), e);
                }
            }
        };
        new Handler(Looper.getMainLooper()).post(r);
    }
}
