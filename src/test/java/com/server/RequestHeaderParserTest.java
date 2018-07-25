package com.server;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import static org.junit.Assert.*;

public class RequestHeaderParserTest {

    @Test
    public void getVerbReturnsGetHTTPVerb() throws UnsupportedEncodingException {

        String headerString = "GET / HTTP/1.1";
        String directory = "/foo";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        assertEquals("GET", requestHeaderParser.getRequestParams().getMethod());
    }

    @Test
    public void getVerbReturnsHTTPPostVerb() throws UnsupportedEncodingException {

        String headerString = "POST / HTTP/1.1";
        String directory = "/foo";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        assertEquals("POST", requestHeaderParser.getRequestParams().getMethod());
    }

    @Test
    public void getRouteReturnsHTTPRoute() throws UnsupportedEncodingException {

        String headerString = "POST / HTTP/1.1";
        String directory = "/foo";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        assertEquals("/", requestHeaderParser.getRequestParams().getPath());
    }

    @Test
    public void getRouteReturnsFormHTTPRoute() throws UnsupportedEncodingException {

        String headerString = "POST /form HTTP/1.1";
        String directory = "/foo";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        assertEquals("/form", requestHeaderParser.getRequestParams().getPath());
    }

    @Test
    public void getRouteReturnsPathWithoutQueryComponent() throws UnsupportedEncodingException {

        String headerString = "GET /cookie?type=chocolate HTTP/1.1";
        String directory = "/foo";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        assertEquals("/cookie", requestHeaderParser.getRequestParams().getPath());
    }

    @Test
    public void getQueryComponentReturnsAHashTableFromRequestURI() throws UnsupportedEncodingException {

        String headerString = "GET /cookie?type=chocolate&foo=bar HTTP/1.1";
        String directory = "/foo";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        Hashtable result = requestHeaderParser.getRequestParams().getQueryComponent();

        assertEquals("chocolate", result.get("type"));
        assertEquals("bar", result.get("foo"));
    }

    @Test
    public void getQueryComponentReturnsADecodedHashTableFromRequestURI() throws UnsupportedEncodingException {

        String headerString = "GET /cookie?foo=%3D%40%23%24%25%5E HTTP/1.1";
        String directory = "/foo";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        Hashtable result = requestHeaderParser.getRequestParams().getQueryComponent();

        assertEquals("=@#$%^", result.get("foo"));
    }

    @Test
    public void getQueryComponentReturnsAnEmptyHashTableIfNoQueriesAreInURI() throws UnsupportedEncodingException {

        String headerString = "GET / HTTP/1.1";
        String directory = "/foo";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        Hashtable result = requestHeaderParser.getRequestParams().getQueryComponent();

        assertEquals(new Hashtable(), result);
    }

    @Test
    public void getCookiesReturnsAHashTableFromRequestHeaders() throws UnsupportedEncodingException {

        String headerString = "GET /cookie HTTP/1.1\r\nCookie: type=chocolate\r\n\r\n";
        String directory = "/foo";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        Hashtable result = requestHeaderParser.getRequestParams().getCookies();

        assertEquals("chocolate", result.get("type"));
    }

    @Test
    public void getCookieReturnsAnEmptyHashTableIfNoCookiesInRequestHeaders() throws UnsupportedEncodingException {

        String headerString = "GET /cookie?type=chocolate&foo=bar HTTP/1.1";
        String directory = "/foo";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        Hashtable result = requestHeaderParser.getRequestParams().getCookies();

        assertEquals(new Hashtable(), result);
    }
    @Test
    public void requestParamsReturnsRequestParamsWithRangeTable() throws UnsupportedEncodingException {
        String directory = "/public";
        String headerString = "GET /foo HTTP/1.1\r\nRange: bytes=1-2\r\n";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        Hashtable result = requestHeaderParser.getRequestParams().getRange();

        assertEquals(1, result.get("start"));
        assertEquals(2, result.get("stop"));

    }

    @Test
    public void requestParamsReturnsRequestParamsWithDefaultStartRangeTable() throws UnsupportedEncodingException {
        String directory = "/public";
        String headerString = "GET /foo HTTP/1.1\r\nRange: bytes=-2\r\n";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        Hashtable result = requestHeaderParser.getRequestParams().getRange();

        assertEquals(-1, result.get("start"));
        assertEquals(2, result.get("stop"));

    }

    @Test
    public void requestParamsReturnsRequestParamsWithDefaultEndRangeTable() throws UnsupportedEncodingException {
        String directory = "/public";
        String headerString = "GET /foo HTTP/1.1\r\nRange: bytes=2-\r\n";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        Hashtable result = requestHeaderParser.getRequestParams().getRange();

        assertEquals(2, result.get("start"));
        assertEquals(-1, result.get("stop"));

    }

    @Test
    public void requestParamsReturnsRequestParamsWithEmptyRangeTable() throws UnsupportedEncodingException {
        String directory = "/public";
        String headerString = "GET /foo HTTP/1.1\r\n\r\n";

        RequestHeaderParser requestHeaderParser = new RequestHeaderParser(headerString, directory);

        Hashtable result = requestHeaderParser.getRequestParams().getRange();

        assertEquals(new Hashtable(), result);
    }
}