package ica.han.oose.project.overhoorapp.util.requestbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;

import ica.han.oose.project.overhoorapp.exceptions.UnauthorizedRequestException;
import ica.han.oose.project.overhoorapp.util.Config;
import ica.han.oose.project.overhoorapp.util.TokenParser;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.JSONDeleteRequestBuilder;

/**
 * An abstract base class that allows for easy implementations of GET/POST/POST-JSON requests.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 1-5-2015
 */
public abstract class AbstractRequestBuilder {

    /**
     * Stores the HttpURLConnection instance.
     */
    public HttpURLConnection connection;
    /**
     * Stores the TokenParser instance.
     */
    private TokenParser tokenGenerator = new TokenParser(Config.username, Config.password);
    /**
     * Stores the URL to send the request to.
     */
    private String url;
    /**
     * Stores the result of the request.
     * Should only be checked after #submit().
     */
    private String result;
    /**
     * Stores the last generated token.
     */
    private String token;

    /**
     * Creates a new AbstractRequestBuilder instance.
     *
     * @param url The URL to send the request to.
     */
    public AbstractRequestBuilder(final String url) {
        this.url = url;
    }

    /**
     * Creates a HttpURLConnection. Must be overridden with a custom implementation in sub-classes.
     *
     * @return The built HttpURLConnection.
     */
    public abstract HttpURLConnection buildConnection();

    /**
     * Submits the request. Must be overridden with a custom implementation in sub-classes.
     */
    public abstract void submit() throws UnauthorizedRequestException;

    /**
     * Returns the URL to send the request to.
     *
     * @return The URL to send the request to.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the result of the request.
     * Call this after #submit()
     *
     * @return The result of the request.
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the result of the request.
     *
     * @param result The result.
     */
    public void setResult(final String result) {
        this.result = result;
    }

    /**
     * Gets the last token.
     *
     * @return The last token.
     */
    public String getToken() {
        token = tokenGenerator.get();
        return token;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void read() {
        if (this instanceof JSONDeleteRequestBuilder) {
            return;
        }
        final StringBuilder sb = new StringBuilder();
        try {

            final Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            int character;
            while ((character = in.read()) >= 0) {
                sb.append((char) character);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        setResult(sb.toString());
    }

}
