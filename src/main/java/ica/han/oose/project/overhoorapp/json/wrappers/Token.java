package ica.han.oose.project.overhoorapp.json.wrappers;

import ica.han.oose.project.overhoorapp.json.models.tokens.read.Tokens;

/**
 * Provides a wrapper class for Tokens.
 *
 * @author Niels
 * @version 1.0
 * @since 1-5-2015
 */
public class Token {

    /**
     * Stores the Tokens instance.
     */
    private Tokens tokenInstance;

    /**
     * Creates a new instance of Token/
     *
     * @param tokenInstance
     */
    public Token(Tokens tokenInstance) {
        this.tokenInstance = tokenInstance;
    }

    /**
     * Returns the Token.
     *
     * @return The Token.
     */
    public String getToken() {
        return tokenInstance.getToken().getToken();
    }
}
