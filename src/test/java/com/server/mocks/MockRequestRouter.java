package com.server.mocks;

import com.server.RequestParams;
import com.server.RequestRouter;

public class MockRequestRouter extends RequestRouter {

    private int dummyResult;

    public MockRequestRouter(){
        this.dummyResult=404;
    }

    public void setResponseCode(int dummyResult){
        this.dummyResult = dummyResult;
    }

    public int getResponseCode(RequestParams requestParams){
        return this.dummyResult;
    }
}
