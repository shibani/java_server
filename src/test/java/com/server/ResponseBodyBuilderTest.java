package com.server;

import org.junit.Test;
import java.io.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

import static org.junit.Assert.*;

public class ResponseBodyBuilderTest {

    @Test
    public void getBodyReturnsImATeapotIfPathIsCoffee() throws IOException {
        String path = "/coffee";
        String method = "GET";

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

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
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setCookies(cookies).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

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
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

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
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setQueryComponent(queryComponents).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

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
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

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
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

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
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

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
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(200).build();

        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);
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
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(200).build();

        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);
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
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(200).build();

        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);
        int expected = 81892;

        assertEquals(expected, body.length);
    }

    @Test
    public void getBodyReturnsPartialContentsFromTheEndOfTheFileIfStartIsNegativeOne() throws IOException {
        String path = "/partial_content.txt";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-partial-content");
        String testDir = resourcesDirectory.getAbsolutePath();
        Hashtable<String, Integer> rangeTable = new Hashtable<>();
        rangeTable.put("start", -1);
        rangeTable.put("stop", 5);

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setDirectory(testDir).setMethod(method).setRange(rangeTable).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String body = new String(responseBodyBuilder.getBody(requestParams, responseParams));

        assertEquals("206.\n", body);
        assertEquals(206, responseBodyBuilder.responseParams.getResponseCode());
        assertEquals(77, responseBodyBuilder.responseParams.getContentLength());
    }

    @Test
    public void getBodyReturnsPartialContentOfFile() throws IOException {
        String path = "/partial_content.txt";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-partial-content");
        String testDir = resourcesDirectory.getAbsolutePath();
        Hashtable<String, Integer> rangeTable = new Hashtable<>();
        rangeTable.put("start", 0);
        rangeTable.put("stop", 4);

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setDirectory(testDir).setMethod(method).setRange(rangeTable).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String body = new String(responseBodyBuilder.getBody(requestParams, responseParams));

        assertEquals("This ", body);
        assertEquals(206, responseBodyBuilder.responseParams.getResponseCode());
        assertEquals(77, responseBodyBuilder.responseParams.getContentLength());
    }

    @Test
    public void getBodyReturnsPartialContentsFromStartToEOFWhenStopIsNegativeOne() throws IOException {
        String path = "/partial_content.txt";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-partial-content");
        String testDir = resourcesDirectory.getAbsolutePath();
        Hashtable<String, Integer> rangeTable = new Hashtable<>();
        rangeTable.put("start", 10);
        rangeTable.put("stop", -1);

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setDirectory(testDir).setMethod(method).setRange(rangeTable).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String body = new String(responseBodyBuilder.getBody(requestParams, responseParams));

        assertEquals("file that contains text to read part of in order to fulfill a 206.\n", body);
        assertEquals(206, responseBodyBuilder.responseParams.getResponseCode());
        assertEquals(77, responseBodyBuilder.responseParams.getContentLength());
    }

    @Test
    public void getBodyReturnsAnEmptyStringWithRangeIsUnsatisfiable() throws IOException {
        String path = "/partial_content.txt";
        String method = "GET";
        File resourcesDirectory = new File("src/test/resources/test-partial-content");
        String testDir = resourcesDirectory.getAbsolutePath();
        Hashtable<String, Integer> rangeTable = new Hashtable<>();
        rangeTable.put("start", 10);
        rangeTable.put("stop", 10000);

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setDirectory(testDir).setMethod(method).setRange(rangeTable).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        String body = new String(responseBodyBuilder.getBody(requestParams, responseParams));

        assertEquals("", body);
        assertEquals(416, responseBodyBuilder.responseParams.getResponseCode());
        assertEquals(77, responseBodyBuilder.responseParams.getContentLength());
    }

    @Test
    public void getBodyCanCreateAndPostToAFile() throws IOException {
        String path = "/cat-form";
        String method = "POST";
        String bodyContent = "data=fatcat";
        String resourceName = "/data";

        File resourcesDirectory = new File("src/test/resources/test-listing");
        String testDir = resourcesDirectory.getAbsolutePath();

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).setBody(bodyContent).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);

        final File folder = new File(requestParams.getDirectory() + requestParams.getPath());

        String filenames = getDirectoryListingString(folder);

        String filePath = requestParams.getDirectory() + requestParams.getPath() + resourceName;

        String content = getFileContents(filePath);

        final File dataFile = new File(requestParams.getDirectory() + requestParams.getPath() + resourceName);

        assertTrue(filenames.contains("data"));
        assertEquals(201, responseBodyBuilder.responseParams.getResponseCode());
        assertEquals("/cat-form/data", responseBodyBuilder.responseParams.getLocationHeader());
        assertEquals("data=fatcat", content);
        dataFile.delete();
    }

    @Test
    public void getBodyCanReadFromAndWriteToAFile() throws IOException {
        String path = "/cat-form/data";
        String method = "GET";
        String bodyContent = "data=fatcat";
        String resourceName = "";
        File resourcesDirectory = new File("src/test/resources/test-listing");
        String testDir = resourcesDirectory.getAbsolutePath();

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).setBody(bodyContent).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        createFile(requestParams, resourceName);
        final File dataFile = new File(requestParams.getDirectory() + requestParams.getPath() + resourceName);
        writeToFile("data=fatcat", dataFile);

        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);

        String filePath = requestParams.getDirectory() + requestParams.getPath();

        String content = getFileContents(filePath);

        assertEquals("data=fatcat", content);
        dataFile.delete();
    }

    @Test
    public void getBodyCanUpdateAFile() throws IOException {
        String path = "/cat-form/data";
        String method = "PUT";
        String bodyContent = "data=heathcliff";
        String resourceName = "";
        File resourcesDirectory = new File("src/test/resources/test-listing");
        String testDir = resourcesDirectory.getAbsolutePath();

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).setBody(bodyContent).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        createFile(requestParams, resourceName);
        final File dataFile = new File(requestParams.getDirectory() + requestParams.getPath() + resourceName);
        writeToFile("data=fatcat", dataFile);

        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);

        String filePath = requestParams.getDirectory() + requestParams.getPath();

        String content = getFileContents(filePath);

        assertEquals(200, responseBodyBuilder.responseParams.getResponseCode());
        assertEquals("data=heathcliff", content);
        dataFile.delete();
    }

    @Test
    public void getBodyCanDeleteAFile() throws IOException {
        String path = "/cat-form/data";
        String method = "DELETE";
        String resourceName = "";
        File resourcesDirectory = new File("src/test/resources/test-listing");
        String testDir = resourcesDirectory.getAbsolutePath();

        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).setDirectory(testDir).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();

        createFile(requestParams, resourceName);

        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);

        final File folder = new File(requestParams.getDirectory() + requestParams.getPath());

        String filenames = getDirectoryListingString(folder);

        assertFalse(filenames.contains("data"));
        assertEquals(200, responseBodyBuilder.responseParams.getResponseCode());
    }

    @Test
    public void getBodyForLogsWithoutCredentialsReturnsEmptyBody() throws IOException {
        String path = "/logs";
        String method = "GET";
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path).setMethod(method).build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();
        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);

        assertEquals(0, body.length);
    }

    @Test
    public void getBodyForLogsWithWrongCredentialsReturnsEmptyBody() throws IOException {
        String path = "/logs";
        String method = "GET";
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path)
                                                                .setMethod(method)
                                                                .setAuthorizationCredentials("fakecreds")
                                                                .build();
        ResponseParams responseParams = new ResponseParamsBuilder().build();
        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);

        assertEquals(0, body.length);
    }

    @Test
    public void getBodyForLogsWithCorrectCredentialsReturnsBodyWithContent() throws IOException {
        String path = "/logs";
        String method = "GET";

        File resourcesDirectory = new File("src/test/resources/correct-credentials-logs-test");
        String testDir = resourcesDirectory.getAbsolutePath();
        String expectedContent = "GET /log HTTP/1.1\\r\\nPUT /these HTTP/1.1\\r\\nHEAD /requests HTTP/1.1\\r\\n";
        RequestRouter rr = new RequestRouter();
        ResponseBodyBuilder responseBodyBuilder = new ResponseBodyBuilder(rr);
        RequestParams requestParams = new RequestParamsBuilder().setPath(path)
                                                                .setMethod(method)
                                                                .setDirectory(testDir)
                                                                .setAuthorizationCredentials("dGVzdDp0ZXN0aW5n")
                                                                .build();
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(200)
                                                                    .build();

        byte[] body = responseBodyBuilder.getBody(requestParams, responseParams);

        assertEquals(expectedContent, new String(body));
    }

    private File createFile(RequestParams requestParams, String resourceName) throws IOException {
        String filePath = requestParams.getDirectory() + requestParams.getPath() + resourceName;
        File file = new File(filePath);
        file.createNewFile();
        return file;
    }

    private void writeToFile(String str, File file) throws IOException {
        if(file.exists()){
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(str);
            writer.close();
        }
    }

    private String getFileContents(String filePath) throws IOException {
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        RequestReader reader = new RequestReader(bufferedReader);
        return reader.getRequestedFileContents();
    }

    private String getDirectoryListingString(File folder) {
        StringBuilder fileNames = new StringBuilder();
        if (folder.listFiles() != null) {
            for (final File fileEntry : folder.listFiles()) {
                fileNames.append(fileEntry.getName());
                fileNames.append(" ");
            }
        }
        return fileNames.toString();
    }
}
