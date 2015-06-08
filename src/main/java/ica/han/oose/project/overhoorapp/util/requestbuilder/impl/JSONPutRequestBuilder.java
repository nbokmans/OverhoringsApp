package ica.han.oose.project.overhoorapp.util.requestbuilder.impl;


import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import ica.han.oose.project.overhoorapp.exceptions.UnauthorizedRequestException;
import ica.han.oose.project.overhoorapp.util.requestbuilder.AbstractRequestBuilder;

/**
 * Allows the user to send a PUT request with JSON data to the server.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 13-5-2015
 */
public class JSONPutRequestBuilder extends AbstractRequestBuilder {

    /**
     * Contains the content (in valid JSON format).
     */
    private String content = "";

    /**
     * Creates a new JSONPutRequestBuilder.
     * Allows the user to send a post request with JSON data to the server.
     *
     * @param url The url to send the JSON data to.
     */
    public JSONPutRequestBuilder(String url) {
        super(url);
    }

    @Override
    public HttpURLConnection buildConnection() {
        try {
            final URL url = new URL(getUrl());
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false); //Belangrijk! ANDERS GEEN HEADER FIELDS
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-XSRF-TOKEN", getToken());
            return connection;
        } catch (IOException e) {
            Log.e("JPutRequestBuilder", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void submit() throws UnauthorizedRequestException {
        final HttpURLConnection connection = buildConnection();
        if (connection != null) {
            try {
                connection.getOutputStream().write(content.getBytes("UTF-8"));
                if (connection.getResponseCode() == HttpStatus.SC_UNAUTHORIZED) {
                    throw new UnauthorizedRequestException("Deze request in niet geauthenticeerd!");
                } else {
                    read();
                }
            } catch (IOException e) {
                Log.e("JSONPutRequestBuilde", e.getMessage(), e);
            }
        }
    }

    /**
     * Sets the JSON content of this request.
     *
     * @param content the content in valid JSON format.
     */
    public void setContent(final String content) {
        if (this.content.equals(content)) {
            return;
        }
        this.content = content;
    }
}
