package com.server;

import java.util.Hashtable;

public class ResponseBodyBuilder {
    private String body = "";
    private Hashtable bodyHashtable;
    private RequestRouter requestRouter;

    ResponseBodyBuilder(RequestRouter requestRouter){
        this.requestRouter = requestRouter;
        this.bodyHashtable = new Hashtable();
        bodyHashtable.put("/coffee", "I'm a teapot");
    }

    public String getBody(RequestParams requestParams) {
        if(this.requestRouter.getResponseCode(requestParams) != 0){
            String value = (String)this.bodyHashtable.get(requestParams.getPath());
            this.body = value;
        }
        return this.body == null ? "" : this.body;
    }
}
