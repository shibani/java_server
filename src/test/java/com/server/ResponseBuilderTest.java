package com.server;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResponseBuilderTest {

    @Test
    public void getResponseReturnsAHeaderAndBodyIfBothHaveContent() throws IOException {
        String path = "/coffee";
        String method = "GET";
        String publicDir = "/foo";
        RequestRouter rr = new RequestRouter();
        ResponseHeaderBuilder rhb = new ResponseHeaderBuilder(rr);
        ResponseBodyBuilder rbb = new ResponseBodyBuilder(rr);
        ResponseBuilder responseBuilder = new ResponseBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        byte[] response = responseBuilder.getResponse(requestParams);

        String expected = "HTTP/1.1 418 I'm a teapot\r\n\r\nI'm a teapot";

        byte[] expectedBytes = expected.getBytes();

        assertTrue(Arrays.equals(expectedBytes, response));
    }

    @Test
    public void getResponseReturnsOnlyAHeaderIfBodyHasNoContent() throws IOException {
        String path = "/tea";
        String method = "GET";
        String publicDir = "/foo";
        RequestRouter rr = new RequestRouter();
        ResponseHeaderBuilder rhb = new ResponseHeaderBuilder(rr);
        ResponseBodyBuilder rbb = new ResponseBodyBuilder(rr);
        ResponseBuilder responseBuilder = new ResponseBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        byte[] response = responseBuilder.getResponse(requestParams);

        String expected = "HTTP/1.1 200 OK\r\n\r\n";

        byte[] expectedBytes = expected.getBytes();

        assertTrue(Arrays.equals(expectedBytes, response));
    }

    @Test
    public void getResponseReturnsOnlyAHeaderIfRequestingLogsWithoutCredentials() throws IOException {
        String path = "/logs";
        String method = "GET";
        RequestRouter rr = new RequestRouter();
        ResponseBuilder responseBuilder = new ResponseBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String expected = "HTTP/1.1 401 Unauthorized\r\nWWW-Authenticate: Basic\r\n\r\n";

        byte[] response = responseBuilder.getResponse(requestParams);

        assertEquals(expected, new String(response));
    }
}