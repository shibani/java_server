package com.server;

import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.io.File;

import static org.junit.Assert.*;

public class ResponseBodyBuilderTest {

    @Test
    public void getBodyReturnsImATeapotIfPathIsCoffee() throws IOException {
        String path = "/coffee";
        String method = "GET";
        String publicDir = "/foo";
        RequestRouter rr = new RequestRouter();

        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr, publicDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        byte[] body = responseBodyBuilder.getBody(requestParams);
        String expected = "I'm a teapot";

        byte[] expectedBytes = expected.getBytes();

        assertTrue(Arrays.equals(expectedBytes, body));
    }

    @Test
    public void getBodyReturnsMmmmChocolateIfRequestIncludesCookie() throws IOException {
        String path = "/eat_cookie";
        String method = "GET";
        String publicDir = "/foo";
        Hashtable<String, String> cookies = new Hashtable<>();
        cookies.put("type", "chocolate");

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr, publicDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setCookies(cookies).build();

        byte[] body = responseBodyBuilder.getBody(requestParams);
        String expected = "mmmm chocolate";

        byte[] expectedBytes = expected.getBytes();

        assertTrue(Arrays.equals(expectedBytes, body));
    }

    @Test
    public void getBodyReturnsListOfFilesIfDIrectoryHasContents() throws IOException {
        String path = "/";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-listing");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr, testDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        byte[] body = responseBodyBuilder.getBody(requestParams);

        String text = new String(body, "UTF-8");

        assertTrue(text.contains("foo.txt"));
        assertTrue(text.contains("bar.txt"));
    }

    @Test
    public void getBodyReturnsEmptyStringIfDIrectoryIsEmpty() throws IOException {
        String path = "/";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-empty");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr, testDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        byte[] body = responseBodyBuilder.getBody(requestParams);

        String expected = "";

        byte[] expectedBytes = expected.getBytes();

        assertTrue(Arrays.equals(expectedBytes, body));
    }

    @Test
    public void getBodyReturnsDecodedQueryComponents() throws IOException {
        String path = "/parameters";
        String method = "GET";
        String publicDir = "/foo";
        Hashtable<String, String> queryComponents = new Hashtable<>();
        queryComponents.put("key", "value");
        RequestRouter rr = new RequestRouter();

        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr, publicDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setQueryComponent(queryComponents).build();
        byte[] body = responseBodyBuilder.getBody(requestParams);

        String expected = "key = value";

        byte[] expectedBytes = expected.getBytes();

        assertTrue(Arrays.equals(expectedBytes, body));
    }

    public void getBodyReturnsContentsOfFile1() throws IOException {
        String path = "/file1";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-file1-contents");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr, testDir);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        String body = responseBodyBuilder.getBody(requestParams).toString();

        String expected = "file1 contents";

        assertEquals(expected, body);
    }
}