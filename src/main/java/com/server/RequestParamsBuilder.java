package com.server;

import java.util.Hashtable;

public class RequestParamsBuilder {
    private String path;
    private String method;
    private Hashtable<String, String> queryComponent;
    private Hashtable<String, String> cookies;
    private String directory;

    public RequestParamsBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public RequestParamsBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public RequestParamsBuilder setQueryComponent(Hashtable<String, String> queryComponent) {
        this.queryComponent = queryComponent;
        return this;
    }

    public RequestParamsBuilder setCookies(Hashtable<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }

    public RequestParamsBuilder setDirectory(String s) {
        this.directory = s;
        return this;
    }

    public RequestParams build() {
        return new RequestParams(path, method, queryComponent, cookies, directory);
    }


}