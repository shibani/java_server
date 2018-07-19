package com.server;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ContentLengthHandlerTest {

    @Test
    public void createLineForImageJpegReturnsExpectedLine(){
        String path = "/image.jpeg";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-image-contents");
        String testDir = resourcesDirectory.getAbsolutePath();
        ContentLengthHandler contentLengthHandler = new ContentLengthHandler(testDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String result = contentLengthHandler.createLine(requestParams, new ResponseParams(200));

        assertEquals("Content-Length: 157751", result);
    }

    @Test
    public void createLineForImagePngReturnsExpectedLine(){
        String path = "/image.png";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-image-contents");
        String testDir = resourcesDirectory.getAbsolutePath();
        ContentLengthHandler contentLengthHandler = new ContentLengthHandler(testDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String result = contentLengthHandler.createLine(requestParams, new ResponseParams(200));

        assertEquals("Content-Length: 108763", result);
    }

    @Test
    public void createLineForImageGifReturnsExpectedLine(){
        String path = "/image.gif";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-image-contents");
        String testDir = resourcesDirectory.getAbsolutePath();
        ContentLengthHandler contentLengthHandler = new ContentLengthHandler(testDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String result = contentLengthHandler.createLine(requestParams, new ResponseParams(200));

        assertEquals("Content-Length: 81892", result);
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo.jpeg";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-image-contents");
        String testDir = resourcesDirectory.getAbsolutePath();
        ContentLengthHandler contentLengthHandler = new ContentLengthHandler(testDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String result = contentLengthHandler.createLine(requestParams, new ResponseParams(200));

        assertEquals("", result);
    }

}