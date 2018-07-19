package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContentTypeHandlerTest {

    @Test
    public void createLineReturnsANewContentTypeLine(){
        String path = "/image.jpeg";
        String method = "GET";
        ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String result = contentTypeHandler.createLine(requestParams);

        assertTrue(result.contains("Content-Type: image/jpeg"));
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String result = contentTypeHandler.createLine(requestParams);

        assertEquals("", result);
    }

}