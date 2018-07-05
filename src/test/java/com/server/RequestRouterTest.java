package com.server;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RequestRouterTest {

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

    @Test
    public void getAllowedMethodsReturnsAnArrayOfAllowedMethods(){
        String path = "/method_options";
        String method = "OPTIONS";
        RequestRouter requestRouter = new RequestRouter();
        String[] actual = requestRouter.getAllowedMethods(path);
        String[] expected = new String[] {"GET", "HEAD", "POST", "OPTIONS", "PUT"};
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected, actual);
    }
}