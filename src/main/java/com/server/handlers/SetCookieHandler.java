package com.server.handlers;

import com.server.RequestParams;
import com.server.ResponseParams;

import java.util.Hashtable;

public class SetCookieHandler implements IResponseHeaderHandler {
    private Hashtable<String, String> setCookieHashtable;
    private static final String SET_COOKIE_KEY = "Set-Cookie";

    public SetCookieHandler(){
        this.setCookieHashtable = new Hashtable<>();
    }

    public String createLine(RequestParams requestParams, ResponseParams responseParams){
        if (requestParams.getPath().equals("/cookie")) {
            setCookieHashtable.put("/cookie", cookiePathValue(requestParams));
        }

        String value = this.setCookieHashtable.get(requestParams.getPath());
        return value == null ? "" : SET_COOKIE_KEY + ": " + value;
    }

    private String cookiePathValue(RequestParams requestParams) {
        StringBuilder cookieString = new StringBuilder();
        for (Object key : requestParams.getQueryComponent().keySet()) {
            cookieString.append(key.toString());
            cookieString.append("=");
            cookieString.append(requestParams.getQueryComponent().get(key));
        }
        return cookieString.toString();
    }
}
