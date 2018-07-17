package com.server;

import java.io.File;

public class ResponseBodyBuilder {
    private String body = "";
    private String publicDir = "";
    private RequestRouter requestRouter;

    ResponseBodyBuilder(RequestRouter requestRouter, String directory){
        this.requestRouter = requestRouter;
        this.publicDir = directory;
    }

    public String getBody(RequestParams requestParams) {
        if (requestParams.getPath().equals("/coffee")) {
            this.body = coffeeBody(requestParams);
        } else if (requestParams.getPath().equals("/cookie")) {
            this.body = cookieBody(requestParams);
        } else if (requestParams.getPath().equals("/eat_cookie")) {
            this.body = eatCookieBody(requestParams);
        } else if (requestParams.getPath().equals("/")) {
            this.body = directoryListingBody(requestParams.getPath());
        } else if (requestParams.getPath().equals("/parameters")) {
            this.body = parametersBody(requestParams);
        } else {
            this.body = "";
        }
        return this.body;
    }

    private String parametersBody(RequestParams requestParams) {
        String parameterBody = "";
        for (Object key : requestParams.getQueryComponent().keySet()) {
            parameterBody = parameterBody + key.toString() + " = " + requestParams.getQueryComponent().get(key) + "\n";
        }
        return parameterBody.trim();
    }

    private String coffeeBody(RequestParams requestParams){
        return "I'm a teapot";
    }

    private String cookieBody(RequestParams requestParams){
        return "Eat";
    }

    public String eatCookieBody(RequestParams requestParams) {
        return "mmmm " + requestParams.getCookies().get("type");
    }

    private String directoryListingBody(String path){
        String dirPath = this.publicDir + path;
        return getFiles(dirPath);
    }

    private String getFiles(String dirPath){
        StringBuilder fileNames = new StringBuilder();
        final File folder = new File(dirPath);

        if(folder.listFiles() != null){
            for (final File fileEntry : folder.listFiles()) {
                fileNames.append(fileEntry.getName());
                fileNames.append(" ");
            }
            return fileNames.toString();
        } else {
            return "";
        }
    }
}
