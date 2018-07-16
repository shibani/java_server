package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocationHandlerTest {

    @Test
    public void createLineReturnsANewLocationLine(){
        String path = "/redirect";
        String method = "GET";
        LocationHandler locationHandler = new LocationHandler();
        String result = locationHandler.createLine(path, method);

        assertEquals("Location: /", result);
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        LocationHandler locationHandler = new LocationHandler();
        String result = locationHandler.createLine(path, method);

        assertEquals("", result);
    }
}