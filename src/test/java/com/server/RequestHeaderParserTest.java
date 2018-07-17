package com.server;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import static org.junit.Assert.*;

public class RequestHeaderParserTest {

    @Test
    public void getVerbReturnsGetHTTPVerb() throws UnsupportedEncodingException {

        String headerString = "GET / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("GET", requestHeaderParser.getRequestParams().getMethod());
    }

    @Test
    public void getVerbReturnsHTTPPostVerb() throws UnsupportedEncodingException {

        String headerString = "POST / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("POST", requestHeaderParser.getRequestParams().getMethod());
    }

    @Test
    public void getRouteReturnsHTTPRoute() throws UnsupportedEncodingException {

        String headerString = "POST / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("/", requestHeaderParser.getRequestParams().getPath());
    }

    @Test
    public void getRouteReturnsFormHTTPRoute() throws UnsupportedEncodingException {

        String headerString = "POST /form HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("/form", requestHeaderParser.getRequestParams().getPath());
    }

    @Test
    public void getRouteReturnsPathWithoutQueryComponent() throws UnsupportedEncodingException {

        String headerString = "GET /cookie?type=chocolate HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        assertEquals("/cookie", requestHeaderParser.getRequestParams().getPath());
    }

    @Test
    public void getQueryComponentReturnsAHashTableFromRequestURI() throws UnsupportedEncodingException {

        String headerString = "GET /cookie?type=chocolate&foo=bar HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        Hashtable result = requestHeaderParser.getRequestParams().getQueryComponent();

        assertEquals("chocolate", result.get("type"));
        assertEquals("bar", result.get("foo"));
    }

    @Test
    public void getQueryComponentReturnsADecodedHashTableFromRequestURI() throws UnsupportedEncodingException {

        String headerString = "GET /cookie?foo=%3D%40%23%24%25%5E HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        Hashtable result = requestHeaderParser.getRequestParams().getQueryComponent();

        assertEquals("=@#$%^", result.get("foo"));
    }

    @Test
    public void getQueryComponentReturnsAnEmptyHashTableIfNoQueriesAreInURI() throws UnsupportedEncodingException {

        String headerString = "GET / HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        Hashtable result = requestHeaderParser.getRequestParams().getQueryComponent();

        assertEquals(new Hashtable(), result);
    }

    @Test
    public void getCookiesReturnsAHashTableFromRequestHeaders() throws UnsupportedEncodingException {

        String headerString = "GET /cookie HTTP/1.1\r\nCookie: type=chocolate\r\n\r\n";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        Hashtable result = requestHeaderParser.getRequestParams().getCookies();

        assertEquals("chocolate", result.get("type"));
    }

    @Test
    public void getCookieReturnsAnEmptyHashTableIfNoCookiesInRequestHeaders() throws UnsupportedEncodingException {

        String headerString = "GET /cookie?type=chocolate&foo=bar HTTP/1.1";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString);

        Hashtable result = requestHeaderParser.getRequestParams().getCookies();

        assertEquals(new Hashtable(), result);
    }
}