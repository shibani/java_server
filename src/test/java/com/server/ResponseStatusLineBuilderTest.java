package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseStatusLineBuilderTest {

    @Test
    public void getHeaderReturnsHTTP200Ok() {
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilder(200);

        assertEquals("HTTP/1.1 200 OK", responseStatusLineBuilder.getHeader());
    }

    @Test
    public void getHeaderReturnsHTTP404NotFound() {
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilder(404);

        assertEquals("HTTP/1.1 404 Not Found", responseStatusLineBuilder.getHeader());
    }

    @Test
    public void getHeaderReturnsHTTP405MethodNotAllowed() {
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilder(405);

        assertEquals("HTTP/1.1 405 Method Not Allowed", responseStatusLineBuilder.getHeader());
    }
}