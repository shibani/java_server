package com.server;

import java.util.Hashtable;

public class RequestParams {

    private String path;
    private String method;
    private Hashtable<String, String> queryComponent;

    public RequestParams(String path, String method) {
        this.path = path;
        this.method = method;
        this.queryComponent = new Hashtable();
    }

    public RequestParams(String path, String method, Hashtable queryComponent) {
        this.path = path;
        this.method = method;
        this.queryComponent = queryComponent;
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
}
