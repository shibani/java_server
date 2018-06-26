package com.server;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

public class RequestReaderTest {

    @Test
    public void getHeader() throws IOException {

        String aString = "header\r\n\r\nbody";
        Reader inputString = new StringReader(aString);
        BufferedReader br = new BufferedReader(inputString);

        final RequestReader requestReader = new RequestReader(br);

        assertEquals("header", requestReader.getHeader());
    }

    @Test
    public void getBody() throws IOException {
        String aString = "header\r\n\r\nbody";
        Reader inputString = new StringReader(aString);
        BufferedReader br = new BufferedReader(inputString);

        final RequestReader requestReader = new RequestReader(br);

        assertEquals("body", requestReader.getBody());
    }
}