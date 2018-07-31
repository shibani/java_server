package com.server.handlers;

import com.server.RequestParams;
import com.server.ResponseParams;

import java.util.Hashtable;

public class ContentTypeHandler implements IResponseHeaderHandler {
    private Hashtable contentTypeHashtable;
    private static final String CONTENT_TYPE_KEY = "Content-Type";

    public ContentTypeHandler(){
        this.contentTypeHashtable = new Hashtable();
        contentTypeHashtable.put("/image.jpeg", "image/jpeg");
        contentTypeHashtable.put("/image.png", "image/png");
        contentTypeHashtable.put("/image.gif", "image/gif");
        contentTypeHashtable.put("/text-file.txt", "text/plain");
    }

    public String createLine(RequestParams requestParams, ResponseParams responseParams){
        String value = (String)this.contentTypeHashtable.get(requestParams.getPath());
        return value == null ? "" : CONTENT_TYPE_KEY + ": " + value;
    }
}
