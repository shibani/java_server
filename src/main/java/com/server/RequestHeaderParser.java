package com.server;

public class RequestHeaderParser {

    private String headerString;
    private String verb;
    private String route;

    RequestHeaderParser(String headerString){
        this.headerString = headerString;
        extractVerb();
        extractRoute();
    }

    private void extractVerb() {
        String requestLine = headerString.split("\n")[0];
        String verb = requestLine.split(" ")[0];
        this.verb = verb.trim();
    }

    private void extractRoute() {
        String requestLine = headerString.split("\n")[0];
        String route = requestLine.split(" ")[1];
        this.route = route.trim();
    }

    public String getVerb(){
        return this.verb;
    }

    public String getRoute(){
        return this.route;
    }
}
