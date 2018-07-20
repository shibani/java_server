package com.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseBuilder {
    private ResponseHeaderBuilder responseHeaderBuilder;
    private ResponseBodyBuilder responseBodyBuilder;

    ResponseBuilder(ResponseHeaderBuilder responseHeaderBuilder, ResponseBodyBuilder responseBodyBuilder){
        this.responseHeaderBuilder = responseHeaderBuilder;
        this.responseBodyBuilder = responseBodyBuilder;
    }

    public byte[] getResponse(RequestParams requestParams) throws IOException {
        String header = this.responseHeaderBuilder.getHeader(requestParams);
        byte[] body = this.responseBodyBuilder.getBody(requestParams);

        if (body.length == 0) {
            return header.getBytes();
        }
        else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            baos.write(header.getBytes());
            baos.write(body);
            byte[] response = baos.toByteArray();
            baos.flush();
            baos.close();
            return response;
        }

        //return body == null ? header : header + body;
    }
}
