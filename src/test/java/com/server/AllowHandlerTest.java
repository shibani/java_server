package com.server;

import com.server.handlers.AllowHandler;
import org.junit.Test;

import static org.junit.Assert.*;

public class AllowHandlerTest {
    @Test
    public void createLineReturnsANewAllowLine(){
        String path = "/method_options";
        String method = "OPTIONS";
        AllowHandler allowHandler = new AllowHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String result = allowHandler.createLine(requestParams, responseParams);

        assertEquals("Allow: GET, PUT, OPTIONS, POST, HEAD", result);
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        AllowHandler allowHandler = new AllowHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String result = allowHandler.createLine(requestParams, responseParams);

        assertEquals("", result);
    }
}