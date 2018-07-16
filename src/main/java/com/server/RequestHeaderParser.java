package com.server;

public class RequestHeaderParser {

    private String headerString;
    private RequestParams requestParams;

    RequestHeaderParser(String headerString){
        this.headerString = headerString;
        buildRequestLine();
    }

    private void buildRequestLine() {
        requestParams = new RequestParams(extractPath(), extractMethod());
    }

    private String extractMethod() {
        String requestLine = headerString.split("\n")[0];
        String verb = requestLine.split(" ")[0];
        return verb.trim();
    }

    private String extractPath() {
        String requestLine = headerString.split("\n")[0];
        String route = requestLine.split(" ")[1];
        return route.trim();
    }

    public RequestParams getRequestParams() {
        return this.requestParams;
    }
}
