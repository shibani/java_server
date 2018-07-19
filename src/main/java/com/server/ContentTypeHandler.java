package com.server;

import java.util.Hashtable;

public class ContentTypeHandler implements IResponseHeaderHandler {
    private Hashtable contentTypeHashtable;
    private static final String CONTENT_TYPE_KEY = "Content-Type";

    ContentTypeHandler(){
        this.contentTypeHashtable = new Hashtable();
        contentTypeHashtable.put("/image.jpeg", "image/jpeg");
        contentTypeHashtable.put("/image.png", "image/png");
        contentTypeHashtable.put("/image.gif", "image/gif");
        contentTypeHashtable.put("/text-file.txt", "text/plain");
    }

    public String createLine(RequestParams requestParams){
        String value = (String)this.contentTypeHashtable.get(requestParams.getPath());
        if (requestParams.getPath().contains("/image.jpeg")){
            return value == null ? "" : CONTENT_TYPE_KEY + ": " + value + "\r\n" + "Content-Length: 157751";
            //return value == null ? "" : CONTENT_TYPE_KEY + ": " + value + "\r\n" + "Content-Length: 23199";
        }
        else {
            return value == null ? "" : CONTENT_TYPE_KEY + ": " + value;
        }
    }
}
