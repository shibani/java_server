package com.server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ResponseHeaderLineBuilderTest {

    @Test
    public void ReturnsAStringWithExpectedKey() throws IOException {
        String expected = "Allow: GET, POST, HEAD";
        ResponseHeaderLineBuilder builder = new ResponseHeaderLineBuilder("Allow", "GET, POST, HEAD");

        String actual = builder.getLine();

        assertEquals(expected, actual);
    }
}