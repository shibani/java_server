package com.server;

public class ResponseHeaderBuilder {

    private String header = "";
    private RequestRouter requestRouter;

    ResponseHeaderBuilder(RequestRouter requestRouter){
        this.requestRouter = requestRouter;
    }

    public String getHeader(RequestParams requestParams, ResponseParams responseParams) {
        StatusHandler statusHandler = new StatusHandler(requestRouter);
        String statusLine = statusHandler.createLine(requestParams);
        appendLine(statusLine);

        if (requestRouter.getResponseCode(requestParams) < 400){
            AllowHandler allowHandler = new AllowHandler();
            String allowLine = allowHandler.createLine(requestParams);
            appendLine(allowLine);

            LocationHandler locationHandler = new LocationHandler();
            String locationLine = locationHandler.createLine(requestParams);
            appendLine(locationLine);

            ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
            String contentTypeLine = contentTypeHandler.createLine(requestParams);
            appendLine(contentTypeLine);

            SetCookieHandler setCookieHandler = new SetCookieHandler();
            String setCookieHandlerLine = setCookieHandler.createLine(requestParams);
            appendLine(setCookieHandlerLine);
        }

        return this.header + "\r\n";
    }

    private void appendLine(String line) {
        if(!line.equals("")){
            this.header = this.header + line + "\r\n";
        }
    }
}
