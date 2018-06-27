package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestHeaderParserTest {

    @Test
    public void getVerbReturnsGetHTTPVerb() {

        String headerString = "GET / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("GET", requestHeaderParser.getVerb());
    }

    @Test
    public void getVerbReturnsHTTPPostVerb() {

        String headerString = "POST / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("POST", requestHeaderParser.getVerb());
    }

    @Test
    public void getRouteReturnsHTTPRoute() {

        String headerString = "POST / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("/", requestHeaderParser.getRoute());
    }

    @Test
    public void getRouteReturnsFormHTTPRoute() {

        String headerString = "POST /form HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("/form", requestHeaderParser.getRoute());
    }
}