package com.server.handlers;

import com.server.RequestParams;
import com.server.RequestRouter;
import com.server.ResponseParams;

import java.util.Hashtable;

public class StatusHandler implements IResponseHeaderHandler {
    private Hashtable statusHashtable;
    private static final String HTTP_VERSION = "HTTP/1.1";
    private RequestRouter requestRouter;


    public StatusHandler(RequestRouter requestRouter) {
        this.statusHashtable = new Hashtable();
        this.requestRouter = requestRouter;
        statusHashtable.put(200, "200 OK");
        statusHashtable.put(201, "201 Created");
        statusHashtable.put(204, "204 No Content");
        statusHashtable.put(206, "206 Partial Content");
        statusHashtable.put(302, "302 Found");
        statusHashtable.put(401, "401 Unauthorized");
        statusHashtable.put(404, "404 Not Found");
        statusHashtable.put(405, "405 Method Not Allowed");
        statusHashtable.put(409, "409 Conflict");
        statusHashtable.put(416, "416 Range Not Satisfiable");
        statusHashtable.put(418, "418 I'm a teapot");
    }

    public String createLine(RequestParams requestParams, ResponseParams responseParams){
        int responseCode;
        if(responseParams.getResponseCode() != 0){
            responseCode = responseParams.getResponseCode();
        } else {
            responseCode = this.requestRouter.getResponseCode(requestParams);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HTTP_VERSION);
        stringBuilder.append(" ");
        stringBuilder.append(statusHashtable.get(responseCode));

        return stringBuilder.toString();
    }
}
