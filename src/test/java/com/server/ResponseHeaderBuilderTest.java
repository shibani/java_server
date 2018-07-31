package com.server;

import com.server.mocks.MockRequestRouter;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseHeaderBuilderTest {

    @Test
    public void getHeaderReturnsAStatusLineByDefault() {
        String path = "/foo";
        String method = "GET";
        String directory = "/bar";
        MockRequestRouter mrr = new MockRequestRouter();
        mrr.setResponseCode(200);
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(mrr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(directory).build();
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(200).build();

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", new String(responseHeaderBuilder.getHeader(requestParams,responseParams)));
    }

    @Test
    public void getHeaderReturnsAFormattedHeaderBasedonPathAndMethod() {
        String path = "/method_options";
        String method = "OPTIONS";
        MockRequestRouter mrr = new MockRequestRouter();
        mrr.setResponseCode(200);
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(mrr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(200).build();

        String header = new String(responseHeaderBuilder.getHeader(requestParams, responseParams));

        String expected = "HTTP/1.1 200 OK\r\nAllow: GET, PUT, OPTIONS, POST, HEAD\r\n\r\n";

        assertEquals(expected, header);
    }

    @Test
    public void getHeaderReturnsASingle405StatusLine() {
        String path = "/redirect";
        String method = "DELETE";
        MockRequestRouter mrr = new MockRequestRouter();
        mrr.setResponseCode(405);
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(mrr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String header = new String(responseHeaderBuilder.getHeader(requestParams, responseParams));

        String expected = "HTTP/1.1 405 Method Not Allowed\r\n\r\n";

        assertEquals(expected, header);
    }


    @Test
    public void getHeaderReturnsA401WithAuthenticationChallenge() {
        String path = "/logs";
        String method = "GET";
        RequestRouter rr = new RequestRouter();
        ResponseHeaderBuilder responseHeaderBuilder = new ResponseHeaderBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path)
                                                                .setMethod(method)
                                                                .build();
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(401)
                                                            .build();

        String header = new String(responseHeaderBuilder.getHeader(requestParams, responseParams));

        String expected = "HTTP/1.1 401 Unauthorized\r\nWWW-Authenticate: Basic\r\n\r\n";

        assertEquals(expected, header);
    }
}