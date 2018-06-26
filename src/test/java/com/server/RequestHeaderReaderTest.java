package com.server;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

public class RequestHeaderReaderTest {

    @Test
    public void getHeader() throws IOException {

        String aString = "header\r\n\r\nbody";
        Reader inputString = new StringReader(aString);
        BufferedReader br = new BufferedReader(inputString);

        final RequestHeaderReader requestReader = new RequestHeaderReader(br);

        assertEquals("header", requestReader.getHeader());
    }
}