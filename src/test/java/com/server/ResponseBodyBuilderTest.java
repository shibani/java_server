package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseBodyBuilderTest {

    @Test
    public void getBodyReturnsImATeapotIf(){
        String path = "/coffee";
        String method = "GET";
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        String body = responseBodyBuilder.getBody(path, method);

        String expected = "I'm a teapot";

        assertEquals(expected, body);
    }

}