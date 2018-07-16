package com.server;

public class ResponseBuilder {
    private ResponseHeaderBuilder responseHeaderBuilder;
    private ResponseBodyBuilder responseBodyBuilder;

    ResponseBuilder(ResponseHeaderBuilder responseHeaderBuilder, ResponseBodyBuilder responseBodyBuilder){
        this.responseHeaderBuilder = responseHeaderBuilder;
        this.responseBodyBuilder = responseBodyBuilder;
    }

    public String getResponse(String path, String method) {
        String header = this.responseHeaderBuilder.getHeader(path, method);
        String body = this.responseBodyBuilder.getBody(path, method);

        return body == null ? header : header + body;
    }
}
