package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContentTypeHandlerTest {

    @Test
    public void createLineReturnsANewContentTypeLine(){
        String path = "/image.jpeg";
        String method = "GET";
        ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
        RequestParams requestParams = new RequestParams(path, method);
        String result = contentTypeHandler.createLine(requestParams);

        assertEquals("Content-Type: image/jpeg", result);
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
        RequestParams requestParams = new RequestParams(path, method);
        String result = contentTypeHandler.createLine(requestParams);

        assertEquals("", result);
    }

}