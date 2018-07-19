package com.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseBuilder {
    private ResponseHeaderBuilder responseHeaderBuilder;
    private ResponseBodyBuilder responseBodyBuilder;
    private ResponseParams responseParams;

    ResponseBuilder(RequestRouter requestRouter){
        this.responseParams = new ResponseParamsBuilder().build();
        this.responseBodyBuilder = new ResponseBodyBuilder(requestRouter);
        this.responseHeaderBuilder = new ResponseHeaderBuilder(requestRouter);
    }

    public byte[] getResponse(RequestParams requestParams) throws IOException {
        byte[] body = this.responseBodyBuilder.getBody(requestParams, responseParams);
        byte[] header = this.responseHeaderBuilder.getHeader(requestParams, this.responseBodyBuilder.responseParams);

        if (body.length == 0) {
            return header;
        }
        else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            baos.write(header);
            baos.write(body);
            byte[] response = baos.toByteArray();
            baos.flush();
            baos.close();
            return response;
        }
    }
}
