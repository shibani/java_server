package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseHeaderBuilderTest {

    @Test
    public void getHeaderReturnsHTTP200Ok() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(200, "");

        assertEquals("HTTP/1.1 200 OK", responseHeaderBuilder.getHeader());
    }

    @Test
    public void getHeaderReturnsHTTP404NotFound() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(404, "");

        assertEquals("HTTP/1.1 404 Not Found", responseHeaderBuilder.getHeader());
    }

    @Test
    public void getHeaderReturnsHTTP405MethodNotAllowed() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(405, "");

        assertEquals("HTTP/1.1 405 Method Not Allowed", responseHeaderBuilder.getHeader());
    }

    @Test
    public void getHeaderReturnsAllowLineForOptionsRequest() {
        String expectedHeader = "HTTP/1.1 200 OK\r\nAllow: GET, HEAD, POST, OPTIONS, PUT\r\n\r\n";
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(200, "OPTIONS");

        assertEquals(expectedHeader, responseHeaderBuilder.getHeader());
    }
}