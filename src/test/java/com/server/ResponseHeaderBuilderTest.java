package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseHeaderBuilderTest {

    @Test
    public void getHeaderReturnsAStatusLineByDefault() {
        String path = "/foo";
        String method = "GET";
        MockRequestRouter mrr = new MockRequestRouter();
        mrr.setResponseCode(200);
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(mrr);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", responseHeaderBuilder.getHeader(path, method));
    }

    @Test
    public void getHeaderReturnsAFormattedHeaderBasedonPathAndMethod(){
        String path = "/method_options";
        String method = "OPTIONS";
        MockRequestRouter mrr = new MockRequestRouter();
        mrr.setResponseCode(200);
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(mrr);
        String header = responseHeaderBuilder.getHeader(path, method);

        String expected = "HTTP/1.1 200 OK\r\nAllow: GET, PUT, OPTIONS, POST, HEAD\r\n\r\n";

        assertEquals(expected, header);
    }

    @Test
    public void getHeaderReturnsASingle405StatusLine(){
        String path = "/redirect";
        String method = "DELETE";
        MockRequestRouter mrr = new MockRequestRouter();
        mrr.setResponseCode(405);
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(mrr);
        String header = responseHeaderBuilder.getHeader(path, method);

        String expected = "HTTP/1.1 405 Method Not Allowed\r\n\r\n";

        assertEquals(expected, header);
    }
}