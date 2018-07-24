package com.server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Hashtable;

public class RequestHeaderParser {

    private String headerString;
    private RequestParams requestParams;
    private String directory;

    RequestHeaderParser(String headerString, String directory) throws UnsupportedEncodingException {
        this.headerString = headerString;
        this.directory = directory;
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

        if (headerString.contains("Cookie: ")) {
            String cookieLine = "";
            String[] headers = headerString.split("\r\n");
            for ( String headerLine : headers) {
                if (headerLine.contains("Cookie: ")) {
                    cookieLine = headerLine;
                    cookieLine = cookieLine.replace("Cookie: ", "");
                    break;
                }
            }
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
        if (headerString.contains("Range")){
            String rangeLine = "";
            String[] headerLines = headerString.split("\r\n");
            for (String headerLine : headerLines){
                if (headerLine.contains("Range")){
                    rangeLine = headerLine;
                    break;
                }
            }

            rangeLine = rangeLine.replace("Range: bytes=", "");

            int start = -1;
            if (!rangeLine.split("[-]")[0].equals("")){
                start = Integer.parseInt(rangeLine.split("-")[0]);
            }
            rangeTable.put("start", start);

            int stop = -1;
            if (rangeLine.split("[-]").length > 1){
                stop = Integer.parseInt(rangeLine.split("-")[1]);
            }
            rangeTable.put("stop", stop);
        }
        return rangeTable;
    }

    public RequestParams getRequestParams() {
        return this.requestParams;
    }
}
