package com.server;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class RequestReaderTest {

    @Test
    public void getHeaderShouldReturnRequest() throws IOException {

        String aString = "header\r\nContent-Length:4\r\n\r\nbody";
        Reader inputString = new StringReader(aString);
        BufferedReader br = new BufferedReader(inputString);

        final RequestReader requestReader = new RequestReader(br);
        final String requestString = "header\r\nContent-Length:4\r\n\r\nbody";

        assertEquals(requestString, requestReader.getHeader());
    }

    @Test
    public void getHeaderShouldReturnOnlyHeaderIfRequestHasNoBody() throws IOException {

        String aString = "header\r\n\r\n";
        Reader inputString = new StringReader(aString);
        BufferedReader br = new BufferedReader(inputString);

        final RequestReader requestReader = new RequestReader(br);
        final String requestString = "header\r\nContent-Length:4\r\n\r\nbody";

        assertEquals("header", requestReader.getHeader());
    }

    @Test
    public void getBodyShouldReturnFileContentsOfFile1() throws IOException {

        File resourcesDirectory = new File("src/test/resources/test-file1-contents/file1");
        String file = resourcesDirectory.getAbsolutePath();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String separator = "\r\n";

        final RequestReader requestReader = new RequestReader(bufferedReader);

        assertEquals("file1 contents", requestReader.getRequestedFileContents());
    }
}