package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestRouterTest {

    @Test
    public void anyWhiteListedPathReturnsTrue(){

        RequestRouter requestRouter = new RequestRouter();
        boolean result = requestRouter.checkPath("/form");

        assertTrue(result);
    }

    @Test
    public void anyNonWhiteListedPathReturnFalse(){

        RequestRouter requestRouter = new RequestRouter();
        boolean result = requestRouter.checkPath("/foobar");

        assertFalse(result);
    }
}