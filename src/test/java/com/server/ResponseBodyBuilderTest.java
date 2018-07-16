package com.server;

import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.*;

public class ResponseBodyBuilderTest {

    @Test
    public void getBodyReturnsImATeapotIf(){
        String path = "/coffee";
        String method = "GET";
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String body = responseBodyBuilder.getBody(requestParams);

        String expected = "I'm a teapot";

        assertEquals(expected, body);
    }

    @Test
    public void getBodyReturnsMmmmChocolateIfRequestIncludesCookie(){
        String path = "/eat_cookie";
        String method = "GET";
        Hashtable<String, String> cookies = new Hashtable<>();
        cookies.put("type", "chocolate");

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setCookies(cookies).build();

        String body = responseBodyBuilder.getBody(requestParams);
        String expected = "mmmm chocolate";

        assertEquals(expected, body);
    }

}