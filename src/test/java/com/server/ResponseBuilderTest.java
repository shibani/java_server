package com.server;

import java.io.IOException;
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
        String response = responseBuilder.getResponse(requestParams);

        String expected = "HTTP/1.1 418 I'm a teapot\r\n\r\nI'm a teapot";

        assertEquals(expected, response);
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
        String response = responseBuilder.getResponse(requestParams);

        String expected = "HTTP/1.1 200 OK\r\n\r\n";

        assertEquals(expected, response);
    }

}