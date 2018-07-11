package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class AllowHandlerTest {
    @Test
    public void createLineReturnsANewAllowLine(){
        String path = "/method_options";
        String method = "OPTIONS";
        AllowHandler allowHandler = new AllowHandler();
        String result = allowHandler.createLine(path, method);

        assertEquals("Allow: GET, PUT, OPTIONS, POST, HEAD", result);
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        AllowHandler allowHandler = new AllowHandler();
        String result = allowHandler.createLine(path, method);

        assertEquals("", result);
    }
}