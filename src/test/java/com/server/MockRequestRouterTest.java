package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class MockRequestRouterTest {

    @Test
    public void checkPathReturns404ByDefault(){

        MockRequestRouter mockRequestRouter = new MockRequestRouter();
        RequestParams requestParams = new RequestParams("", "");

        assertEquals(404, mockRequestRouter.getResponseCode(requestParams));
    }

    @Test
    public void checkPathReturns200WhenSetTo200(){

        MockRequestRouter mockRequestRouter = new MockRequestRouter();

        mockRequestRouter.setResponseCode(200);
        RequestParams requestParams = new RequestParams("", "");

        assertEquals(200, mockRequestRouter.getResponseCode(requestParams));
    }

    @Test
    public void checkPathReturns404WhenSetTo404(){

        MockRequestRouter mockRequestRouter = new MockRequestRouter();

        mockRequestRouter.setResponseCode(404);
        RequestParams requestParams = new RequestParams("", "");

        assertEquals(404, mockRequestRouter.getResponseCode(requestParams));
    }
}