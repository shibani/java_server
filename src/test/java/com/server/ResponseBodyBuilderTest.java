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

        RequestRouter rr = new RequestRouter();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);
        String expected = "I'm a teapot";

        byte[] expectedBytes = expected.getBytes();

        assertTrue(Arrays.equals(expectedBytes, body));
    }

    @Test
    public void getBodyReturnsMmmmChocolateIfRequestIncludesCookie() throws IOException {
        String path = "/eat_cookie";
        String method = "GET";

        Hashtable<String, String> cookies = new Hashtable<>();
        cookies.put("type", "chocolate");

        RequestRouter rr = new RequestRouter();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setCookies(cookies).build();

        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);

        String expected = "mmmm chocolate";

        byte[] expectedBytes = expected.getBytes();

        assertTrue(Arrays.equals(expectedBytes, body));
    }

    @Test
    public void getBodyReturnsEmptyStringIfDIrectoryIsEmpty() throws IOException {
        String path = "/";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-empty");
        String testDir = resourcesDirectory.getAbsolutePath();

        RequestRouter rr = new RequestRouter();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);

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
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setQueryComponent(queryComponents).build();

        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);

        String expected = "key = value";

        byte[] expectedBytes = expected.getBytes();

        assertTrue(Arrays.equals(expectedBytes, body));
    }

    @Test
    public void getBodyReturnsContentsOfFile1() throws IOException {
        String path = "/file1";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-file1-contents");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        String body = new String(responseBodyBuilder.getBody(requestParams, responseParams));

        String expected = "file1 contents";

        assertEquals(expected, body);
    }

    @Test
    public void getBodyReturnsListOfLinksToFilesIfDIrectoryHasContents() throws IOException {
        String path = "/";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-listing");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        String body = new String(responseBodyBuilder.getBody(requestParams, responseParams));

        assertTrue(body.contains("<a href=\""));
        assertTrue(body.contains("foo.txt"));
        assertTrue(body.contains("bar.txt"));
    }

    @Test
    public void getBodyReturnsDirectoryLinksInListOfLinks() throws IOException {
        String path = "/";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-listing");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        String body = new String(responseBodyBuilder.getBody(requestParams, responseParams));

        assertTrue(body.contains("<a href=\""));
        assertTrue(body.contains("cat-form"));
    }

    @Test
    public void getBodyForImageJpegReturnsByteArrayOfCorrectSize() throws IOException {
        String path = "/image.jpeg";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-image-contents");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        byte[] body = responseBodyBuilder.getBody(requestParams, new ResponseParams(200));
        int expected = 157751;

        assertEquals(expected, body.length);
    }

    @Test
    public void getBodyForImagePngReturnsByteArrayOfCorrectSize() throws IOException {
        String path = "/image.png";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-image-contents");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        byte[] body = responseBodyBuilder.getBody(requestParams, new ResponseParams(200));
        int expected = 108763;

        assertEquals(expected, body.length);
    }

    @Test
    public void getBodyForImageGifReturnsByteArrayOfCorrectSize() throws IOException {
        String path = "/image.gif";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-image-contents");
        String testDir = resourcesDirectory.getAbsolutePath();
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        byte[] body = responseBodyBuilder.getBody(requestParams, new ResponseParams(200));
        int expected = 81892;

        assertEquals(expected, body.length);
    }
}