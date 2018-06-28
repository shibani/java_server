package com.server;

public class RequestHeaderParser {

    private String headerString;
    private String method;
    private String path;

    RequestHeaderParser(String headerString){
        this.headerString = headerString;
        extractMethod();
        extractPath();
    }

    private void extractMethod() {
        String requestLine = headerString.split("\n")[0];
        String verb = requestLine.split(" ")[0];
        this.method = verb.trim();
    }

    private void extractPath() {
        String requestLine = headerString.split("\n")[0];
        String route = requestLine.split(" ")[1];
        this.path = route.trim();
    }

    public String getMethod(){
        return this.method;
    }

    public String getPath(){
        return this.path;
    }
}
