package com.server;

import java.util.Hashtable;

public class ResponseParamsBuilder {

    private int responseCode;
    private int contentLength;
    private Hashtable<String, Integer> contentRange;

    public ResponseParamsBuilder setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public ResponseParamsBuilder setContentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public ResponseParamsBuilder setContentRange(int start, int stop) {
        this.contentRange = new Hashtable<>();
        this.contentRange.put("start", start);
        this.contentRange.put("stop", stop);
        return this;
    }

    public ResponseParams build() {
        return new ResponseParams(responseCode, contentLength, contentRange);
    }
}
