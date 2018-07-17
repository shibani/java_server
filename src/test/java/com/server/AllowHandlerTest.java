package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class AllowHandlerTest {
    @Test
    public void createLineReturnsANewAllowLine(){
        String path = "/method_options";
        String method = "OPTIONS";
        AllowHandler allowHandler = new AllowHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String result = allowHandler.createLine(requestParams);

        assertEquals("Allow: GET, PUT, OPTIONS, POST, HEAD", result);
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        AllowHandler allowHandler = new AllowHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String result = allowHandler.createLine(requestParams);

        assertEquals("", result);
    }
}