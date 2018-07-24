package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContentTypeHandlerTest {

    @Test
    public void createLineReturnsANewContentTypeLineForImageJpeg(){
        String path = "/image.jpeg";
        String method = "GET";
        ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String result = contentTypeHandler.createLine(requestParams, responseParams);

        assertTrue(result.contains("Content-Type: image/jpeg"));
    }

    @Test
    public void createLineReturnsANewContentTypeLineForImagePng(){
        String path = "/image.png";
        String method = "GET";
        ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String result = contentTypeHandler.createLine(requestParams, responseParams);

        assertTrue(result.contains("Content-Type: image/png"));
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String result = contentTypeHandler.createLine(requestParams, responseParams);

        assertEquals("", result);
    }

}