package com.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseParamsTest {

    @Test
    public void getResponseCodeReturnsAResponseCode(){
        ResponseParams responseParams = new ResponseParamsBuilder().setResponseCode(200).build();

        assertEquals(200, responseParams.getResponseCode());
    }

}