package com.server;

import java.util.Hashtable;

public class RequestParams {

    private String path;
    private String method;
    private Hashtable<String, String> queryComponent;
    private Hashtable<String, String> cookies;
    private String directory;

    public RequestParams(String path, String method, Hashtable<String, String> queryComponent, Hashtable<String, String> cookies, String directory) throws NullPointerException {
        if ((path == null) || (method == null)) {
            throw new NullPointerException();
        }
        this.path = path;
        this.method = method;
        this.queryComponent = queryComponent;
        this.cookies = cookies;
        this.directory = directory;
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

    public String getDirectory() {
        return this.directory;
    }
}
