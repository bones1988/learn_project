package com.epam.esm.token;

import java.util.Objects;

/**
 * Token class
 */
public class Token {
    private String token; //NOSONAR

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token input token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @param o another object
     * @return boolean if objects equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token1 = (Token) o;
        return Objects.equals(token, token1.token);
    }

    /**
     * @return hash code of token
     */
    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
