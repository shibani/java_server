package com.server;

public class MockRequestRouter extends RequestRouter{

    private boolean dummyResult;

    public void setCheckPathStub(boolean dummyResult){
        this.dummyResult = dummyResult;
    }

    public boolean checkPath(String requestPath){
        return dummyResult;
    }
}
