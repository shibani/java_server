package com.server;

public class ResponseParams {

    private int responseCode;

    public ResponseParams(int responseCode){
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

}
