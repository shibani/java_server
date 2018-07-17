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
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", responseHeaderBuilder.getHeader(requestParams));
    }

    @Test
    public void getHeaderReturnsAFormattedHeaderBasedonPathAndMethod(){
        String path = "/method_options";
        String method = "OPTIONS";
        MockRequestRouter mrr = new MockRequestRouter();
        mrr.setResponseCode(200);
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(mrr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String header = responseHeaderBuilder.getHeader(requestParams);

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
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String header = responseHeaderBuilder.getHeader(requestParams);

        String expected = "HTTP/1.1 405 Method Not Allowed\r\n\r\n";

        assertEquals(expected, header);
    }
}