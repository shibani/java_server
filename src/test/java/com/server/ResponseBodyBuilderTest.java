package com.server;

import org.junit.Test;

import java.util.Hashtable;
import java.io.File;

import static org.junit.Assert.*;

public class ResponseBodyBuilderTest {

    @Test
    public void getBodyReturnsImATeapotIfPathIsCoffee(){
        String path = "/coffee";
        String method = "GET";
        String publicDir = "/foo";
        RequestRouter rr = new RequestRouter();

        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr, publicDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String body = responseBodyBuilder.getBody(requestParams);

        String expected = "I'm a teapot";

        assertEquals(expected, body);
    }

    @Test
    public void getBodyReturnsMmmmChocolateIfRequestIncludesCookie() {
        String path = "/eat_cookie";
        String method = "GET";
        String publicDir = "/foo";
        Hashtable<String, String> cookies = new Hashtable<>();
        cookies.put("type", "chocolate");

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr, publicDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setCookies(cookies).build();

        String body = responseBodyBuilder.getBody(requestParams);
        String expected = "mmmm chocolate";

        assertEquals(expected, body);
    }

    @Test
    public void getBodyReturnsListOfFilesIfDIrectoryHasContents(){
        String path = "/";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-listing");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr, testDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String body = responseBodyBuilder.getBody(requestParams);

        assertTrue(body.contains("foo.txt"));
        assertTrue(body.contains("bar.txt"));
    }

    @Test
    public void getBodyReturnsEmptyStringIfDIrectoryIsEmpty(){
        String path = "/";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-empty");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr, testDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String body = responseBodyBuilder.getBody(requestParams);

        String expected = "";

        assertEquals(expected, body);
    }

}