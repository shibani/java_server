package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class MockRequestRouterTest {

    @Test
    public void checkPathReturns404ByDefault(){

        MockRequestRouter mockRequestRouter = new MockRequestRouter();

        assertEquals(404, mockRequestRouter.getResponseCode("", ""));
    }

    @Test
    public void checkPathReturns200WhenSetTo200(){

        MockRequestRouter mockRequestRouter = new MockRequestRouter();

        mockRequestRouter.setResponseCode(200);

        assertEquals(200, mockRequestRouter.getResponseCode("", ""));
    }

    @Test
    public void checkPathReturns404WhenSetTo404(){

        MockRequestRouter mockRequestRouter = new MockRequestRouter();

        mockRequestRouter.setResponseCode(404);

        assertEquals(404, mockRequestRouter.getResponseCode("", ""));
    }
}