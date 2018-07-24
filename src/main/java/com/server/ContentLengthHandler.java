package com.server;

import java.io.File;
import java.nio.file.Files;
import java.util.Hashtable;

public class ContentLengthHandler implements IResponseHeaderHandler {
    private Hashtable contentLengthHashtable;
    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private String publicDir = "";

    ContentLengthHandler(String directory) {
        this.publicDir = directory;
        this.contentLengthHashtable = new Hashtable();
        contentLengthHashtable.put("/image.jpeg", getFileSize("/image.jpeg"));
        contentLengthHashtable.put("/image.png", getFileSize("/image.png"));
        contentLengthHashtable.put("/image.gif", getFileSize("/image.gif"));
    }

    public String createLine(RequestParams requestParams, ResponseParams responseParams) {
        String value = (String)this.contentLengthHashtable.get(requestParams.getPath());
        return value == null ? "" : CONTENT_LENGTH_KEY + ": " + value;
    }

    private String getFileSize(String path) {
        int size;
        try {
            size = (int)Files.size(new File(this.publicDir + path).toPath());
        }
        catch(Exception e){
            size = 0;
        }
        return Integer.toString(size);
    }
}
