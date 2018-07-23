package com.server;

import java.io.IOException;

public class ResponseBuilder {
    private ResponseHeaderBuilder responseHeaderBuilder;
    private ResponseBodyBuilder responseBodyBuilder;
    private ResponseParams responseParams;

    ResponseBuilder(RequestRouter requestRouter){
        this.responseParams = new ResponseParams();
        this.responseBodyBuilder = new ResponseBodyBuilder(requestRouter);
        this.responseHeaderBuilder = new ResponseHeaderBuilder(requestRouter);
    }

    public String getResponse(RequestParams requestParams) throws IOException {
        String body = this.responseBodyBuilder.getBody(requestParams, responseParams);
        String header = this.responseHeaderBuilder.getHeader(requestParams, this.responseBodyBuilder.responseParams);

        return body == null ? header : header + body;
    }
}
