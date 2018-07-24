package com.server;

public class ResponseParamsBuilder {

    private int responseCode;

    public ResponseParamsBuilder setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public ResponseParams build() {
        return new ResponseParams(responseCode);
    }
}
