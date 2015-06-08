package ica.han.oose.project.overhoorapp.util.requestbuilder.impl;


import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import ica.han.oose.project.overhoorapp.util.requestbuilder.AbstractRequestBuilder;

/**
 * Allows the user to send a GET request to the server.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 1-5-2015
 */
public class GetRequestBuilder extends AbstractRequestBuilder {

    /**
     * Creates a new GetRequestBuilder instance.
     *
     * @param url The URL to send the request to.
     */
    public GetRequestBuilder(String url) {
        super(url);
    }

    @Override
    public HttpURLConnection buildConnection() {
        try {
            final URL url = new URL(getUrl());
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false); //Belangrijk! ANDERS GEEN HEADER FIELDS
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10 * 1000);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
            connection.setRequestProperty("X-XSRF-TOKEN", getToken());
            connection.setDoOutput(false);
            return connection;
        } catch (IOException e) {
            Log.e("GetRequestBuilder", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void submit() {
        connection = buildConnection();
        try {
            read();
        } catch (Exception e) {
            Log.e("GetRequestBuilder", e.getMessage(), e);
        }
    }
}
