package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class StatusHandlerTest {

    @Test
    public void getHeaderReturnsHTTP200Ok() {
        MockRequestRouter mockRequestRouter = new MockRequestRouter();
        mockRequestRouter.setResponseCode(200);
        StatusHandler statusHandler = new StatusHandler(mockRequestRouter);
        String path = "foo";
        String method = "bar";

        assertEquals("HTTP/1.1 200 OK", statusHandler.createLine(path, method));
    }

    @Test
    public void getHeaderReturnsHTTP302Found() {
        MockRequestRouter mockRequestRouter = new MockRequestRouter();
        mockRequestRouter.setResponseCode(302);
        StatusHandler statusHandler = new StatusHandler(mockRequestRouter);
        String path = "foo";
        String method = "bar";

        assertEquals("HTTP/1.1 302 Found", statusHandler.createLine(path, method));
    }

    @Test
    public void getHeaderReturnsHTTP404NotFound() {
        MockRequestRouter mockRequestRouter = new MockRequestRouter();
        mockRequestRouter.setResponseCode(404);
        StatusHandler statusHandler = new StatusHandler(mockRequestRouter);
        String path = "foo";
        String method = "bar";

        assertEquals("HTTP/1.1 404 Not Found", statusHandler.createLine(path, method));
    }

    @Test
    public void getHeaderReturnsHTTP405MethodNotAllowed() {
        MockRequestRouter mockRequestRouter = new MockRequestRouter();
        mockRequestRouter.setResponseCode(405);
        StatusHandler statusHandler = new StatusHandler(mockRequestRouter);
        String path = "foo";
        String method = "bar";

        assertEquals("HTTP/1.1 405 Method Not Allowed", statusHandler.createLine(path, method));
    }

}