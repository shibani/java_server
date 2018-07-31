package com.server;

import com.server.handlers.PartialContentHandler;
import org.junit.Test;

import static org.junit.Assert.*;

public class PartialContentHandlerTest {

    @Test
    public void createLineReturnsANewPartialContentLineFor206() {
        String path = "foo";
        String method = "BAR";
        PartialContentHandler partialContentHandler = new PartialContentHandler();
        RequestParams requestParams = new RequestParamsBuilder()
                .setPath(path)
                .setMethod(method)
                .build();
        ResponseParams responseParams = new ResponseParamsBuilder()
                .setResponseCode(206)
                .setContentLength(500)
                .setContentRange(100, 200)
                .build();

        String expected = "Content-Range: bytes 100-200/500";
        String result = partialContentHandler.createLine(requestParams, responseParams);

        assertEquals(expected, result);
    }

    @Test
    public void createLineReturnsANewPartialContentLineFor416() {
        String path = "foo";
        String method = "BAR";
        PartialContentHandler partialContentHandler = new PartialContentHandler();
        RequestParams requestParams = new RequestParamsBuilder()
                .setPath(path)
                .setMethod(method)
                .build();
        ResponseParams responseParams = new ResponseParamsBuilder()
                .setResponseCode(416)
                .setContentLength(500)
                .build();

        String expected = "Content-Range: bytes */500";
        String result = partialContentHandler.createLine(requestParams, responseParams);

        assertEquals(expected, result);
    }

    @Test
    public void createLineReturnsAnEmptyString() {
        String path = "foo";
        String method = "BAR";
        PartialContentHandler partialContentHandler = new PartialContentHandler();
        RequestParams requestParams = new RequestParamsBuilder()
                .setPath(path)
                .setMethod(method)
                .build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String expected = "";
        String result = partialContentHandler.createLine(requestParams, responseParams);

        assertEquals(expected, result);
    }
}