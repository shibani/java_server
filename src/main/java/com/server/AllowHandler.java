package com.server;

import java.util.Hashtable;

public class AllowHandler implements IResponseHeaderHandler {
    private Hashtable allowHashtable;
    private static final String ALLOW_KEY = "Allow";

    AllowHandler(){
        this.allowHashtable = new Hashtable();
        allowHashtable.put("/method_options", "GET, PUT, OPTIONS, POST, HEAD");
        allowHashtable.put("/method_options2", "GET, OPTIONS, HEAD");
    }

    public String createLine(RequestParams requestParams, ResponseParams responseParams){
        String value = (String)this.allowHashtable.get(requestParams.getPath());
        return value == null ? "" : ALLOW_KEY + ": " + value;
    }

}
