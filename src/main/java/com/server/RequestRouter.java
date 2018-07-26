package com.server;

import java.util.Hashtable;

public class RequestRouter {

    private Hashtable routeTable;

    RequestRouter() {
        this.routeTable = new Hashtable();
        createHashtable();
    }

    public int getResponseCode(RequestParams requestParams){
        int responseCode = 404;
        String path = requestParams.getPath();
        String method = requestParams.getMethod();

        if(checkPath(path)){
            if(checkMethod(path, method)){
                responseCode = findCode(path, method);
            } else {
                responseCode = 405;
            }
        }
        return responseCode;
    }

    public String getAllowedMethods(String path) {
        String methods = "";
        if(checkPath(path)) {
            Hashtable hash = (Hashtable)this.routeTable.get(path);
            methods = String.join(", ", hash.keySet());
        }
        return methods;
    }

    private void createHashtable(){
        addRoute("/", "GET", 200);
        addRoute("/" ,"HEAD", 200);
        addRoute("/form" ,"POST", 200);
        addRoute("/put-target" ,"PUT", 200);
        addRoute("/file1" ,"GET", 200);
        addRoute("/text-file.txt" ,"GET", 200);
        addRoute("/method_options" ,"GET", 200);
        addRoute("/method_options" ,"HEAD", 200);
        addRoute("/method_options" ,"POST", 200);
        addRoute("/method_options" ,"OPTIONS", 200);
        addRoute("/method_options" ,"PUT", 200);
        addRoute("/method_options2" ,"GET", 200);
        addRoute("/method_options2" ,"HEAD", 200);
        addRoute("/method_options2" ,"OPTIONS", 200);
        addRoute("/redirect" ,"GET", 302);
        addRoute("/image.jpeg", "GET", 200);
        addRoute("/image.png", "GET", 200);
        addRoute("/image.gif", "GET", 200);
        addRoute("/text-file.txt", "GET", 200);
        addRoute("/coffee", "GET", 418);
        addRoute("/tea", "GET", 200);
        addRoute("/cookie", "GET", 200);
        addRoute("/eat_cookie", "GET", 200);
        addRoute("/parameters", "GET", 200);
        addRoute("/logs", "GET", 401);
    }

    private void addRoute(String path, String method, int statusCode) {
        if (checkPath(path)) {
            Hashtable methodHashTable = (Hashtable) this.routeTable.get(path);
            methodHashTable.put(method, statusCode);
        } else {
            Hashtable hashtable = new Hashtable();
            hashtable.put(method, statusCode);
            this.routeTable.put(path, hashtable);
        }
    }

    private boolean checkPath(String requestPath){
        return this.routeTable.containsKey(requestPath);
    }

    private boolean checkMethod(String requestPath, String method){
        Hashtable methodTable = (Hashtable) this.routeTable.get(requestPath);
        return methodTable.containsKey(method);
    }

    private int findCode(String requestPath, String method){
        Hashtable methodTable = (Hashtable) this.routeTable.get(requestPath);
        return (int) methodTable.get(method);
    }
}
