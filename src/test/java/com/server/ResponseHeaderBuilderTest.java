package com.server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ResponseHeaderBuilderTest {

    @Test
    public void getHeaderReturnsAStatusLineByDefault() throws IOException {
        String path = "/foo";
        String method = "GET";
        String directory = "/bar";
        MockRequestRouter mrr = new MockRequestRouter();
        mrr.setResponseCode(200);
        ResponseBuilder responseBuilder = new ResponseBuilder(mrr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(directory).build();

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", responseBuilder.getResponse(requestParams));
    }

    @Test
    public void getHeaderReturnsAFormattedHeaderBasedonPathAndMethod() throws IOException {
        String path = "/method_options";
        String method = "OPTIONS";
        MockRequestRouter mrr = new MockRequestRouter();
        mrr.setResponseCode(200);
        ResponseBuilder responseBuilder = new ResponseBuilder(mrr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();

        String response = responseBuilder.getResponse(requestParams);

        String expected = "HTTP/1.1 200 OK\r\nAllow: GET, PUT, OPTIONS, POST, HEAD\r\n\r\n";

        assertEquals(expected, response);
    }

    @Test
    public void getHeaderReturnsASingle405StatusLine() throws IOException {
        String path = "/redirect";
        String method = "DELETE";
        MockRequestRouter mrr = new MockRequestRouter();
        mrr.setResponseCode(405);
        ResponseBuilder responseBuilder = new ResponseBuilder(mrr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String header = responseBuilder.getResponse(requestParams);

        String expected = "HTTP/1.1 405 Method Not Allowed\r\n\r\n";

        assertEquals(expected, header);
    }
}