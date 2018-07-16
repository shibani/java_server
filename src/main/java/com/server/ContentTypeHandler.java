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
        return value == null ? "" : CONTENT_TYPE_KEY + ": " + value;
    }
}
