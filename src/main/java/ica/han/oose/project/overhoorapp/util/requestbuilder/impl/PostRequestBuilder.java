package ica.han.oose.project.overhoorapp.util.requestbuilder.impl;


import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import ica.han.oose.project.overhoorapp.exceptions.UnauthorizedRequestException;
import ica.han.oose.project.overhoorapp.util.requestbuilder.AbstractRequestBuilder;

/**
 * Allows the user to send a POST request with data to the server.
 *
 * @author Niels Bokmans
 * @version 1.0
 * @since 1-5-2015
 */
public class PostRequestBuilder extends AbstractRequestBuilder {

    /**
     * Contains all the keys with their respective values.
     * LinkedHashMap because linked hashmaps iterate in the order in which they were created, which
     * is important for sending a post request.
     */
    private LinkedHashMap<String, String> postDataMap = new LinkedHashMap<String, String>();

    /**
     * Creates a new PostRequestBuilder instance to send a POST request.
     *
     * @param url The URL to send the request to.
     */
    public PostRequestBuilder(String url) {
        super(url);
    }

    @Override
    public void submit() throws UnauthorizedRequestException {
        connection = buildConnection();
        if (connection != null) {
            try {
                connection.getOutputStream().write(getPostData(getMap()));
                if (connection.getResponseCode() == HttpStatus.SC_UNAUTHORIZED) {
                    throw new UnauthorizedRequestException("Deze request in niet geauthenticeerd!");
                } else {
                    read();
                }
            } catch (IOException e) {
                Log.e("PostRequestBuilder", e.getMessage(), e);
            }
        }
    }

    @Override
    public HttpURLConnection buildConnection() {
        try {
            final URL url = new URL(getUrl());
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false); //Belangrijk! ANDERS GEEN HEADER FIELDS
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3) Gecko/20100401");
            if (!url.getPath().equals("/token") && !url.getPath().equals("/api/register") && !url.getPath().contains("login")) {
                connection.setRequestProperty("X-XSRF-TOKEN", getToken());
            }
            connection.setDoOutput(true);
            return connection;
        } catch (IOException e) {
            Log.e("PostRequestBuilder", e.getMessage(), e);
        }
        return null;
    }

    /**
     * Adds a key with value to the linked hashmap.
     *
     * @param key   The key to add to the linked hashmap.
     * @param value The value to add to this key in the linked hashmap.
     */
    public void addKeyAndValue(String key, String value) {
        postDataMap.put(key, value);
    }

    /**
     * Returns the linked hashmap.
     *
     * @return the linked hashmap.
     */
    public LinkedHashMap<String, String> getMap() {
        return postDataMap;
    }

    /**
     * Returns a byte array containing all the data that should be sent in the POST request.
     *
     * @param credentialsMap A map containing the data that should be converted to a byte array.
     * @return a byte array containing all the data that should be sent in the POST request.
     */
    private byte[] getPostData(final Map<String, String> credentialsMap) {
        final StringBuilder postDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> credential : credentialsMap.entrySet()) {
            if (postDataBuilder.length() != 0) {
                postDataBuilder.append("&");
            }
            try {
                postDataBuilder.append(URLEncoder.encode(credential.getKey(), "UTF-8")).append("=");
                postDataBuilder.append(URLEncoder.encode(credential.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Log.e("PostRequestBuilder", e.getMessage(), e);
            }
        }
        try {
            return postDataBuilder.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("PostRequestBuilder", e.getMessage(), e);
        }
        return new byte[0];
    }

}
