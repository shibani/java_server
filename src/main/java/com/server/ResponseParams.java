package com.server;

import java.util.Hashtable;

public class ResponseParams {

    private int responseCode;
    private int contentLength;
    private Hashtable<String, Integer> contentRange;

    public ResponseParams(int responseCode, int contentLength, Hashtable<String, Integer> contentRange){
        this.responseCode = responseCode;
        this.contentLength = contentLength;
        this.contentRange = contentRange;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public int getContentLength() {
        return this.contentLength;
    }

    public Hashtable<String, Integer> getContentRange() {
        return this.contentRange;
    }

}
