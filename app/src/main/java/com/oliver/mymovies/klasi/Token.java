package com.oliver.mymovies.klasi;

/**
 * Created by Oliver on 2/20/2018.
 */

public class Token {

    public String request_token;
    public String session_id;

    public Token(String request_token) {
        this.request_token = request_token;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }
}
