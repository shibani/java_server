package com.server;

import java.util.Hashtable;

public class ResponseHeaderBuilder {

    private int responseCode;
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static Hashtable responseStatusCodes;
    private String method;

    ResponseHeaderBuilder(int responseCode, String method){
        this.responseCode = responseCode;
        this.method = method;
        buildResponseStatusCodes();
    }

    public String getHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HTTP_VERSION);
        stringBuilder.append(" ");
        stringBuilder.append(responseStatusCodes.get(responseCode));

        //Adding the Allow line IFF method = OPTIONS
        if (this.method.equals("OPTIONS")) {
            ResponseHeaderLineBuilder headerLineBuilder = new ResponseHeaderLineBuilder("Allow", "GET, HEAD, POST, OPTIONS, PUT");
            stringBuilder.append("\r\n");
            stringBuilder.append(headerLineBuilder.getLine());
            stringBuilder.append("\r\n\r\n");
        }

        return stringBuilder.toString();
    }

    private void buildResponseStatusCodes() {
        responseStatusCodes = new Hashtable();
        responseStatusCodes.put(200, "200 OK");
        responseStatusCodes.put(404, "404 Not Found");
        responseStatusCodes.put(405, "405 Method Not Allowed");
    }
}
