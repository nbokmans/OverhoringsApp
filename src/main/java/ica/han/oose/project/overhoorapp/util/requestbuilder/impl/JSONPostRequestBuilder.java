package ica.han.oose.project.overhoorapp.util.requestbuilder.impl;

import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import ica.han.oose.project.overhoorapp.exceptions.EmptyJSONRequestSubmissionException;
import ica.han.oose.project.overhoorapp.exceptions.UnauthorizedRequestException;
import ica.han.oose.project.overhoorapp.util.requestbuilder.AbstractRequestBuilder;

/**
 * Allows the user to send a POST request with JSON data to the server.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 1-5-2015
 */
public class JSONPostRequestBuilder extends AbstractRequestBuilder {

    /**
     * Contains the content (in valid JSON format).
     */
    private String content = "";

    /**
     * Creates a new JSONPostRequestBuilder.
     * Allows the user to send a post request with JSON data to the server.
     *
     * @param url The url to send the JSON data to.
     */
    public JSONPostRequestBuilder(String url) {
        super(url);
    }

    @Override
    public HttpURLConnection buildConnection() {
        try {
            final URL url = new URL(getUrl());
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false); //Belangrijk! ANDERS GEEN HEADER FIELDS
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "application/json");
            String token = getToken();
            connection.setRequestProperty("X-XSRF-TOKEN", getToken());
            connection.setDoOutput(true);
            return connection;
        } catch (IOException e) {
            Log.e("JSONPostRequestBuilder", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void submit() throws UnauthorizedRequestException {
        if (content.isEmpty()) {
            throw new EmptyJSONRequestSubmissionException();
        } else {
            connection = buildConnection();
            if (connection != null) {
                try {
                    connection.getOutputStream().write(content.getBytes("UTF-8"));
                    if (connection.getResponseCode() == HttpStatus.SC_UNAUTHORIZED) {
                        throw new UnauthorizedRequestException("Deze request is niet geauthenticeerd! " + connection.getHeaderField("X-XSRF-TOKEN"));
                    } else {
                        read();
                    }
                } catch (IOException e) {
                    Log.e("JSONPostRequestBuilder", e.getMessage(), e);
                }
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
