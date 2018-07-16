package com.server;

import java.util.Hashtable;

public class StatusHandler {
    private Hashtable statusHashtable;
    private static final String HTTP_VERSION = "HTTP/1.1";
    private RequestRouter requestRouter;


    StatusHandler(RequestRouter requestRouter) {
        this.statusHashtable = new Hashtable();
        this.requestRouter = requestRouter;
        statusHashtable.put(200, "200 OK");
        statusHashtable.put(302, "302 Found");
        statusHashtable.put(404, "404 Not Found");
        statusHashtable.put(405, "405 Method Not Allowed");
    }

    public String createLine(String path, String method){
        int responseCode = this.requestRouter.getResponseCode(path, method);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HTTP_VERSION);
        stringBuilder.append(" ");
        stringBuilder.append(statusHashtable.get(responseCode));

        return stringBuilder.toString();
    }
}
