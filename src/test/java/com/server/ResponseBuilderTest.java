package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseBuilderTest {

    @Test
    public void getResponseReturnsAHeaderAndBodyIfBothHaveContent(){
        String path = "/coffee";
        String method = "GET";
        RequestRouter rr = new RequestRouter();
        ResponseHeaderBuilder rhb = new ResponseHeaderBuilder(rr);
        ResponseBodyBuilder rbb = new ResponseBodyBuilder(rr);
        ResponseBuilder responseBuilder = new ResponseBuilder(rhb, rbb);
        RequestParams requestParams = new RequestParams(path, method);
        String response = responseBuilder.getResponse(requestParams);

        String expected = "HTTP/1.1 418 I'm a teapot\r\n\r\nI'm a teapot";

        assertEquals(expected, response);
    }

    @Test
    public void getResponseReturnsOnlyAHeaderIfBodyHasNoContent(){
        String path = "/tea";
        String method = "GET";
        RequestRouter rr = new RequestRouter();
        ResponseHeaderBuilder rhb = new ResponseHeaderBuilder(rr);
        ResponseBodyBuilder rbb = new ResponseBodyBuilder(rr);
        ResponseBuilder responseBuilder = new ResponseBuilder(rhb, rbb);
        RequestParams requestParams = new RequestParams(path, method);
        String response = responseBuilder.getResponse(requestParams);

        String expected = "HTTP/1.1 200 OK\r\n\r\n";

        assertEquals(expected, response);
    }

}