package com.server;

public class ResponseHeaderBuilder {

    private String header = "";
    private RequestRouter requestRouter;

    ResponseHeaderBuilder(RequestRouter requestRouter){
        this.requestRouter = requestRouter;
    }

    public byte[] getHeader(RequestParams requestParams, ResponseParams responseParams) {
        StatusHandler statusHandler = new StatusHandler(requestRouter);
        String statusLine = statusHandler.createLine(requestParams, responseParams);
        appendLine(statusLine);

        if (requestRouter.getResponseCode(requestParams) < 400){
            AllowHandler allowHandler = new AllowHandler();
            String allowLine = allowHandler.createLine(requestParams, responseParams);
            appendLine(allowLine);

            LocationHandler locationHandler = new LocationHandler();
            String locationLine = locationHandler.createLine(requestParams, responseParams);
            appendLine(locationLine);

            ContentTypeHandler contentTypeHandler = new ContentTypeHandler();
            String contentTypeLine = contentTypeHandler.createLine(requestParams, responseParams);
            appendLine(contentTypeLine);

            ContentLengthHandler contentLengthHandler = new ContentLengthHandler(requestParams.getDirectory());
            String contentLengthLine = contentLengthHandler.createLine(requestParams, new ResponseParams(200));
            appendLine(contentLengthLine);

            SetCookieHandler setCookieHandler = new SetCookieHandler();
            String setCookieHandlerLine = setCookieHandler.createLine(requestParams, responseParams);
            appendLine(setCookieHandlerLine);
        }
        String endOfHeader = "\r\n";

        return (this.header + endOfHeader).getBytes();
    }

    private void appendLine(String line) {
        if(!line.equals("")){
            this.header = this.header + line + "\r\n";
        }
    }
}
