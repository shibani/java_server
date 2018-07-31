package com.server;

import com.server.handlers.SetCookieHandler;
import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.*;

public class SetCookieHandlerTest {
    @Test
    public void createLineReturnsANewSetCookieLine(){
        String path = "/cookie";
        String method = "GET";
        Hashtable<String, String> queryComponent = new Hashtable<>();
        queryComponent.put("type", "chocolate");

        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setQueryComponent(queryComponent).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        SetCookieHandler setCookieHandler = new SetCookieHandler();
        String result = setCookieHandler.createLine(requestParams, responseParams);

        assertEquals("Set-Cookie: type=chocolate", result);
    }

    @Test
    public void createLineReturnsAnEmptyStringWhenPathIsNotFound(){
        String path = "/foo";
        String method = "GET";
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        SetCookieHandler setCookieHandler = new SetCookieHandler();
        String result = setCookieHandler.createLine(requestParams, responseParams);

        assertEquals("", result);
    }
}