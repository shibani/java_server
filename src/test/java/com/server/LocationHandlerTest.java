package com.server;

import com.server.handlers.LocationHandler;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationHandlerTest {

    @Test
    public void createLineReturnsANewLocationLine(){
        String path = "/redirect";
        String method = "GET";
        LocationHandler locationHandler = new LocationHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();
        String result = locationHandler.createLine(requestParams, responseParams);

        assertEquals("Location: /", result);
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        LocationHandler locationHandler = new LocationHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String result = locationHandler.createLine(requestParams, responseParams);

        assertEquals("", result);
    }

    @Test
    public void createLineReturnsStringFromTheResponseParamsObjectIfFound(){
        String path = "/foo";
        String method = "GET";
        LocationHandler locationHandler = new LocationHandler();
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().setLocationHeader("/foo").build();

        String result = locationHandler.createLine(requestParams, responseParams);

        assertEquals("Location: /foo", result);
    }
}