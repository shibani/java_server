package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocationHandlerTest {

    @Test
    public void createLineReturnsANewLocationLine(){
        String path = "/redirect";
        String method = "GET";
        LocationHandler locationHandler = new LocationHandler();
        RequestParams requestParams = new RequestParams(path, method);
        String result = locationHandler.createLine(requestParams);

        assertEquals("Location: /", result);
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        LocationHandler locationHandler = new LocationHandler();
        RequestParams requestParams = new RequestParams(path, method);
        String result = locationHandler.createLine(requestParams);

        assertEquals("", result);
    }
}