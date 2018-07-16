package com.server;

public class ResponseHeaderBuilder {

    private String header = "";
    private RequestRouter requestRouter;

    ResponseHeaderBuilder(RequestRouter requestRouter){
        this.requestRouter = requestRouter;
    }

    public String getHeader(String path, String method) {
        StatusHandler statusHandler = new StatusHandler(requestRouter);
        String statusLine = statusHandler.createLine(path, method);
        appendLine(statusLine);

        if (requestRouter.getResponseCode(path, method) < 400){
            AllowHandler allowHandler = new AllowHandler();
            String allowLine = allowHandler.createLine(path, method);
            appendLine(allowLine);

            LocationHandler locationHandler = new LocationHandler();
            String locationLine = locationHandler.createLine(path, method);
            appendLine(locationLine);

            ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
            String contentTypeLine = contentTypeHandler.createLine(path, method);
            appendLine(contentTypeLine);
        }

        return this.header + "\r\n";
    }

    private void appendLine(String line) {
        if(!line.equals("")){
            this.header = this.header + line + "\r\n";
        }
    }
}
