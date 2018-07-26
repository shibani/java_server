package com.server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Hashtable;

import static java.lang.Integer.parseInt;

public class RequestParser {

    private String headerString;
    private RequestParams requestParams;
    private String directory;
    private int contentLength;

        RequestParser(String headerString, String directory) throws UnsupportedEncodingException {
        this.headerString = headerString;
        this.directory = directory;
        this.contentLength = extractContentLength();
        buildRequestParams();
    }

    private void buildRequestParams() throws UnsupportedEncodingException {
        requestParams = new RequestParamsBuilder()
                .setPath(extractPath())
                .setMethod(extractMethod())
                .setDirectory(extractDirectory())
                .setQueryComponent(extractQueryComponent())
                .setCookies(extractCookies())
                .setRange(extractRange())
                .setContentLength(extractContentLength())
                .setBody(extractBody())
                .setAuthorizationCredentials(extractAuthorizationCredentials())
                .build();
    }

    private String extractDirectory(){
        return this.directory.trim();
    }

    private String extractMethod() {
        String requestLine = headerString.split("\n")[0];
        String verb = requestLine.split(" ")[0];
        return verb.trim();
    }

    private String extractPath() {
        String requestLine = headerString.split("\n")[0];
        String uri = requestLine.split(" ")[1];
        String route = uri.split("[?]")[0];
        return route.trim();
    }

    private String extractBody(){
        String bodyString = "";
        if(this.contentLength > 0 ){
            bodyString = headerString.split("\r\n\r\n")[1];
            bodyString = bodyString.trim();
        } else {
            bodyString = "";
        }
        return bodyString;
    }

    private int extractContentLength(){
        int contentLength = 0;
        String targetString = "Content-Length";
        if (headerString.contains(targetString)){
            String contentLine = extractLine(targetString, headerString);
            contentLine = contentLine.split(":")[1];
            contentLength = parseInt(contentLine.trim());
        } else {
            contentLength = 0;
        }
        return contentLength;
    }

    private Hashtable<String, String> extractQueryComponent() throws UnsupportedEncodingException {
        Hashtable<String, String> queryComponent = new Hashtable<>();
        String requestLine = headerString.split("\n")[0];
        String uri = requestLine.split(" ")[1];
        if (uri.contains("?")) {
            String component = uri.split("[?]")[1];
            String[] queries = component.split("[&]");

            for (String keyValuePair : queries) {
                String[] query = keyValuePair.split("[=]");
                String key = query[0];
                String value = URLDecoder.decode(query[1], "UTF-8");
                queryComponent.put(key, value);
            }
        }
        return queryComponent;
    }

    private Hashtable<String, String> extractCookies() {
        Hashtable<String, String> cookieTable = new Hashtable<>();
        String targetString = "Cookie: ";
        if (headerString.contains(targetString)) {

            String cookieLine = extractLine(targetString, headerString);

            cookieLine = cookieLine.replace("Cookie: ", "");
            String[] cookies = cookieLine.split("; ");
            for (String cookieKeyValue : cookies) {
                String key = cookieKeyValue.split("[=]")[0];
                String value = cookieKeyValue.split("[=]")[1];
                cookieTable.put(key, value);
            }
        }
        return cookieTable;
    }

    private Hashtable<String, Integer> extractRange() {
        Hashtable<String, Integer> rangeTable = new Hashtable<>();
        String targetString = "Range";
        if (headerString.contains(targetString)) {

            String rangeLine = extractLine(targetString, headerString);

            rangeLine = rangeLine.replace("Range: bytes=", "");

            int start = -1;
            if (!rangeLine.split("[-]")[0].equals("")){
                start = parseInt(rangeLine.split("-")[0]);
            }
            rangeTable.put("start", start);

            int stop = -1;
            if (rangeLine.split("[-]").length > 1){
                stop = parseInt(rangeLine.split("-")[1]);
            }
            rangeTable.put("stop", stop);
        }
        return rangeTable;
    }

    private String extractLine(String targetString, String header) {
        String targetLine = "";
        String[] headerLines = headerString.split("\r\n");
        for (String headerLine : headerLines) {
            if (headerLine.contains(targetString)) {
                targetLine = headerLine;
                break;
            }
        }
        return targetLine;
    }

    private String extractAuthorizationCredentials() {
        String authorizationCredentials = "";
        String authorizationHeaderLine = "Authorization: Basic";
        if (headerString.contains(authorizationHeaderLine)) {
            String[] headers = headerString.split("\r\n");
            for ( String headerLine : headers) {
                if (headerLine.contains(authorizationHeaderLine)) {
                    authorizationCredentials = headerLine.replace(authorizationHeaderLine + " ", "");
                    break;
                }
            }
        }
        return authorizationCredentials;
    }

    public RequestParams getRequestParams() {
        return this.requestParams;
    }
}
