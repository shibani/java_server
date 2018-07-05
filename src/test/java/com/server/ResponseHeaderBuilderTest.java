package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseHeaderBuilderTest {

    @Test
    public void getHeaderReturnsHTTP200Ok() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(200, "");

        assertEquals("HTTP/1.1 200 OK\r\n", responseHeaderBuilder.getHeader());
    }

    @Test
    public void getHeaderReturnsHTTP404NotFound() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(404, "");

        assertEquals("HTTP/1.1 404 Not Found\r\n", responseHeaderBuilder.getHeader());
    }

    @Test
    public void getHeaderReturnsHTTP405MethodNotAllowed() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(405, "");

        assertEquals("HTTP/1.1 405 Method Not Allowed\r\n", responseHeaderBuilder.getHeader());
    }
}