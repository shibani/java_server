package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseHeaderBuilderTest {

    @Test
    public void getHeaderReturnsHTTP200Ok() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(200);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", responseHeaderBuilder.getHeader());
    }

    @Test
    public void getHeaderReturnsHTTP302Found() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(302);

        assertEquals("HTTP/1.1 302 Found\r\n\r\n", responseHeaderBuilder.getHeader());
    }

    @Test
    public void getHeaderReturnsHTTP404NotFound() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(404);

        assertEquals("HTTP/1.1 404 Not Found\r\n\r\n", responseHeaderBuilder.getHeader());
    }

    @Test
    public void getHeaderReturnsHTTP405MethodNotAllowed() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(405);

        assertEquals("HTTP/1.1 405 Method Not Allowed\r\n\r\n", responseHeaderBuilder.getHeader());
    }

    @Test
    public void addLineAppendsAHeaderLineToTheHeader() {
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(200);

        responseHeaderBuilder.addLine("Allow", "GET");

        assertEquals("HTTP/1.1 200 OK\r\nAllow: GET\r\n\r\n", responseHeaderBuilder.getHeader());

    }
}