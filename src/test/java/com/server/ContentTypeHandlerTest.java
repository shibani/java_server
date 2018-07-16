package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContentTypeHandlerTest {

    @Test
    public void createLineReturnsANewContentTypeLine(){
        String path = "/image.jpeg";
        String method = "GET";
        ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
        String result = contentTypeHandler.createLine(path, method);

        assertEquals("Content-Type: image/jpeg", result);
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
        String result = contentTypeHandler.createLine(path, method);

        assertEquals("", result);
    }

}