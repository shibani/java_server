package com.server;

public class ResponseHeaderLineBuilder {

    private String key;
    private String value;

    ResponseHeaderLineBuilder(String newKey, String newValue){
        this.key = newKey;
        this.value = newValue;
    }

    public String getLine() {
        return this.key + ": " + this.value + "\r\n";
    }
}
