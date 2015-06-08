package ica.han.oose.project.overhoorapp.util;

import android.util.Log;

import ica.han.oose.project.overhoorapp.exceptions.UnauthorizedRequestException;
import ica.han.oose.project.overhoorapp.json.constants.Post;
import ica.han.oose.project.overhoorapp.json.models.tokens.read.Tokens;
import ica.han.oose.project.overhoorapp.json.wrappers.Token;
import ica.han.oose.project.overhoorapp.util.genson.Deserializer;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.PostRequestBuilder;

/**
 * @author Niels Bokmans
 * @version 1.0
 * @since 17-5-2015
 */
public class TokenParser {

    /**
     * Stores the username
     */
    private String username;

    /**
     * Stores the password
     */
    private String password;

    /**
     * Creates a new TokenParser instance.
     *
     * @param username The username to log in with.
     * @param password The password to log in with.
     */
    public TokenParser(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Fetches a token from the website.
     *
     * @return A token from the website.
     */
    public String get() {
        final PostRequestBuilder builder = new PostRequestBuilder(Post.TOKEN.getUrl());
        builder.addKeyAndValue("username", username);
        builder.addKeyAndValue("password", password);
        try {
            builder.submit();
        } catch (UnauthorizedRequestException e) {
            Log.e("TokenParser", e.getMessage(), e);
        }
        final String result = builder.getResult();
        final Tokens tokenInstance = (Tokens) Deserializer.deserialize(result, Tokens.class);
        final Token token = new Token(tokenInstance);
        return token.getToken();
    }
}
