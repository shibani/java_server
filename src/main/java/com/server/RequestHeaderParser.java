package com.server;

public class RequestHeaderParser {

    private String headerString;
    private String verb;
    private String URI;

    RequestHeaderParser(String headerString){
        this.headerString = headerString;
        extractVerb();
        extractURI();
    }

    private void extractVerb() {
        String requestLine = headerString.split("\n")[0];
        String verb = requestLine.split(" ")[0];
        this.verb = verb.trim();
    }

    private void extractURI() {
        String requestLine = headerString.split("\n")[0];
        String route = requestLine.split(" ")[1];
        this.URI = route.trim();
    }

    public String getVerb(){
        return this.verb;
    }

    public String getURI(){
        return this.URI;
    }
}
