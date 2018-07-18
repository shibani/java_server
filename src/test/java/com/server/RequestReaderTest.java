package com.server;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class RequestReaderTest {

    @Test
    public void getHeaderShouldReturnExpectedHeader() throws IOException {

        String aString = "header\r\n\r\nbody";
        Reader inputString = new StringReader(aString);
        BufferedReader br = new BufferedReader(inputString);

        final RequestReader requestReader = new RequestReader(br);

        assertEquals("header", requestReader.getHeader());
    }

    @Test
    public void getBodyShouldReturnFileContentsOfFile1() throws IOException {

        File resourcesDirectory = new File("src/test/resources/test-file1-contents/file1");
        String file = resourcesDirectory.getAbsolutePath();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        final RequestReader requestReader = new RequestReader(bufferedReader);

        assertEquals("file1 contents", requestReader.getHeader());
    }
}