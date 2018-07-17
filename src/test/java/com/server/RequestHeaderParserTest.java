package com.server;

import org.junit.Test;

import java.util.Hashtable;

import static org.junit.Assert.*;

public class RequestHeaderParserTest {

    @Test
    public void getVerbReturnsGetHTTPVerb() {

        String headerString = "GET / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("GET", requestHeaderParser.getRequestParams().getMethod());
    }

    @Test
    public void getVerbReturnsHTTPPostVerb() {

        String headerString = "POST / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("POST", requestHeaderParser.getRequestParams().getMethod());
    }

    @Test
    public void getRouteReturnsHTTPRoute() {

        String headerString = "POST / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("/", requestHeaderParser.getRequestParams().getPath());
    }

    @Test
    public void getRouteReturnsFormHTTPRoute() {

        String headerString = "POST /form HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("/form", requestHeaderParser.getRequestParams().getPath());
    }

    @Test
    public void getRouteReturnsPathWithoutQueryComponent() {

        String headerString = "GET /cookie?type=chocolate HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("/cookie", requestHeaderParser.getRequestParams().getPath());
    }

    @Test
    public void getQueryComponentReturnsAHashTableFromRequestURI() {

        String headerString = "GET /cookie?type=chocolate&foo=bar HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        Hashtable result = requestHeaderParser.getRequestParams().getQueryComponent();

        assertEquals("chocolate", result.get("type"));
        assertEquals("bar", result.get("foo"));
    }

    @Test
    public void getQueryComponentReturnsAnEmptyHashTableIfNoQueriesAreInURI() {

        String headerString = "GET / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        Hashtable result = requestHeaderParser.getRequestParams().getQueryComponent();

        assertEquals(new Hashtable(), result);
    }

    @Test
    public void getCookiesReturnsAHashTableFromRequestHeaders() {

        String headerString = "GET /cookie HTTP/1.1\r\nCookie: type=chocolate\r\n\r\n";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        Hashtable result = requestHeaderParser.getRequestParams().getCookies();

        assertEquals("chocolate", result.get("type"));
    }

    @Test
    public void getCookieReturnsAnEmptyHashTableIfNoCookiesInRequestHeaders() {

        String headerString = "GET /cookie?type=chocolate&foo=bar HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        Hashtable result = requestHeaderParser.getRequestParams().getCookies();

        assertEquals(new Hashtable(), result);
    }
}