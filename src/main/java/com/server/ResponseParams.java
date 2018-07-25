package com.server;

import java.util.Hashtable;

public class ResponseParams {

    private int responseCode;
    private int contentLength;
    private Hashtable<String, Integer> contentRange;
    private String locationHeader;

    public ResponseParams(int responseCode, int contentLength, Hashtable<String, Integer> contentRange, String locationHeader) {
        this.responseCode = responseCode;
        this.contentLength = contentLength;
        this.contentRange = contentRange;
        this.locationHeader = locationHeader;
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

    public String getLocationHeader(){
        return this.locationHeader;
    }
}
