package com.server;

public class MockRequestRouter extends RequestRouter{

    private int dummyResult;

    MockRequestRouter(){
        this.dummyResult=404;
    }

    public void setResponseCode(int dummyResult){
        this.dummyResult = dummyResult;
    }

    public int getResponseCode(String requestPath, String method){
        return this.dummyResult;
    }
}
