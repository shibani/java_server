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

    public String getBody(String path, String method) {
        if(this.requestRouter.getResponseCode(path, method) != 0){
            String value = (String)this.bodyHashtable.get(path);
            this.body = value;
        }
        return this.body == null ? "" : this.body;
    }
}
