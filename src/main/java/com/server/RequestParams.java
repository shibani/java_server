package com.server;

import java.util.Hashtable;

public class RequestParams {

    private String path;
    private String method;
    private Hashtable<String, String> queryComponent;
    private Hashtable<String, String> cookies;

    public RequestParams(String path, String method, Hashtable<String, String> queryComponent, Hashtable<String, String> cookies) throws NullPointerException {
        if ((path == null) || (method == null)) {
            throw new NullPointerException();
        }
        this.path = path;
        this.method = method;
        this.queryComponent = queryComponent;
        this.cookies = cookies;
    }

    public String getPath() {
        return this.path;
    }

    public String getMethod() {
        return this.method;
    }

    public Hashtable getQueryComponent() {
        return this.queryComponent;
    }

    public Hashtable getCookies() {
        return this.cookies;
    }
}
