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
        bodyHashtable.put("/cookie", "Eat");
    }

    public String getBody(RequestParams requestParams) {
        if (requestParams.getPath().equals("/eat_cookie")){
           bodyHashtable.put("/eat_cookie", eatCookieBody(requestParams));
        }
        if(this.requestRouter.getResponseCode(requestParams) != 0){
            String value = (String)this.bodyHashtable.get(requestParams.getPath());
            this.body = value;
        }
        return this.body == null ? "" : this.body;
    }

    public String eatCookieBody(RequestParams requestParams) {
       return "mmmm " + requestParams.getCookies().get("type");
    }
}
