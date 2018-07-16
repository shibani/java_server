package com.server;

public class ResponseBuilder {
    private ResponseHeaderBuilder responseHeaderBuilder;
    private ResponseBodyBuilder responseBodyBuilder;

    ResponseBuilder(ResponseHeaderBuilder responseHeaderBuilder, ResponseBodyBuilder responseBodyBuilder){
        this.responseHeaderBuilder = responseHeaderBuilder;
        this.responseBodyBuilder = responseBodyBuilder;
    }

    public String getResponse(RequestParams requestParams) {
        String header = this.responseHeaderBuilder.getHeader(requestParams);
        String body = this.responseBodyBuilder.getBody(requestParams);

        return body == null ? header : header + body;
    }
}
