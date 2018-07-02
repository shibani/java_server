package com.server;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestRouterTest {

//    @Test
//    public void anyWhiteListedPathReturnsTrue(){
//
//        RequestRouter requestRouter = new RequestRouter();
//        boolean result = requestRouter.checkPath("/form");
//
//        assertTrue(result);
//    }
//
//    @Test
//    public void anyNonWhiteListedPathReturnFalse(){
//
//        RequestRouter requestRouter = new RequestRouter();
//        boolean result = requestRouter.checkPath("/foobar");
//
//        assertFalse(result);
//    }

    @Test
    public void getResponseCodeReturnsAResponseCode(){
        String path = "/";
        String method = "GET";
        RequestRouter requestRouter = new RequestRouter();
        int result = requestRouter.getResponseCode(path, method);

        assertEquals(200, result);
    }

    @Test
    public void getResponseCodeReturns404IfPathDoesntExist(){
        String path = "/foobar";
        String method = "HEAD";
        RequestRouter requestRouter = new RequestRouter();
        int result = requestRouter.getResponseCode(path, method);

        assertEquals(404, result);
    }

    @Test
    public void getResponseCodeReturns405IfMethodDoesntExist(){
        String path = "/";
        String method = "DELETE";
        RequestRouter requestRouter = new RequestRouter();
        int result = requestRouter.getResponseCode(path, method);

        assertEquals(405, result);
    }
}