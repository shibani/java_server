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

    public String createLine(RequestParams requestParams, ResponseParams responseParams){
        String value = (String)this.contentTypeHashtable.get(requestParams.getPath());
        if (requestParams.getPath().contains("/image.jpeg")){
            return value == null ? "" : CONTENT_TYPE_KEY + ": " + value + "\r\n" + "Content-Length: 157751";
        }
        else if (requestParams.getPath().contains("/image.png")){
            return value == null ? "" : CONTENT_TYPE_KEY + ": " + value + "\r\n" + "Content-Length: 108763";
        }
        else if (requestParams.getPath().contains("/image.gif")){
            return value == null ? "" : CONTENT_TYPE_KEY + ": " + value + "\r\n" + "Content-Length: 81892";
        }
        else {
            return value == null ? "" : CONTENT_TYPE_KEY + ": " + value;
        }
    }
}
