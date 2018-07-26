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

        return combine(header, body);
    }

    private byte[] combine(byte[] firstArray, byte[] secondArray) throws IOException {
        byte[] result;
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            outputStream.write(firstArray);
            outputStream.write(secondArray);
            result = outputStream.toByteArray();
            outputStream.flush();
        }
        finally {
            outputStream.close();
        }
        return result;
    }
}
