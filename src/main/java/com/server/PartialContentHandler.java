package com.server;

public class PartialContentHandler {
    private static final String PARTIAL_CONTENT_KEY = "Content-Range";
    private static final String PARTIAL_CONTENT_UNIT = "bytes";

    public String createLine(RequestParams requestParams, ResponseParams responseParams) {
        if (responseParams.getResponseCode() != 0) {
            int responseCode = responseParams.getResponseCode();
            if (responseCode == 206) {
                int start = responseParams.getContentRange().get("start");
                int stop = responseParams.getContentRange().get("stop");
                int contentLength = responseParams.getContentLength();
                return PARTIAL_CONTENT_KEY + ": " + PARTIAL_CONTENT_UNIT + " " + start + "-" + stop + "/" + contentLength;
            } else if (responseCode == 416) {
                int contentLength = responseParams.getContentLength();
                return PARTIAL_CONTENT_KEY + ": " + PARTIAL_CONTENT_UNIT + " " + "*/" + contentLength;
            }
        }
        return "";
    }
}