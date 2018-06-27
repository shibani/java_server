package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestRouterTest {

    @Test
    public void anyWhiteListedPathResultsIn200(){

        RequestRouter requestRouter = new RequestRouter("/form");

        assertEquals(200, requestRouter.getResult());

    }

    @Test
    public void anyNonWhiteListedPathResultsIn404(){

        RequestRouter requestRouter = new RequestRouter("/foobar");

        assertEquals(404, requestRouter.getResult());

    }
}