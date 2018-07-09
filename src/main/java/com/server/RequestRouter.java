package com.server;

import java.util.*;
import java.util.Hashtable;

public class RequestRouter {

    private Hashtable routeTable;

    RequestRouter() {
        this.routeTable = createHashtable();
    }

    private Hashtable createHashtable(){
        Hashtable routeTable = new Hashtable();

        Hashtable rootHash = new Hashtable();
        rootHash.put("GET", 200);
        rootHash.put("HEAD", 200);

        Hashtable formHash = new Hashtable();
        formHash.put("POST", 200);

        Hashtable putTargetHash = new Hashtable();
        putTargetHash.put("PUT", 200);

        Hashtable fileOneHash = new Hashtable();
        fileOneHash.put("GET", 200);

        Hashtable textFileHash = new Hashtable();
        textFileHash.put("GET", 200);

        Hashtable optionsHash = new Hashtable();
        optionsHash.put("GET", 200);
        optionsHash.put("HEAD", 200);
        optionsHash.put("POST", 200);
        optionsHash.put("OPTIONS", 200);
        optionsHash.put("PUT", 200);

        Hashtable options2Hash = new Hashtable();
        options2Hash.put("GET", 200);
        options2Hash.put("HEAD", 200);
        options2Hash.put("OPTIONS", 200);

        Hashtable redirectPathHash = new Hashtable();
        redirectPathHash.put("GET", 302);

        routeTable.put("/", rootHash);
        routeTable.put("/form", formHash);
        routeTable.put("/put-target", putTargetHash);
        routeTable.put("/file1", fileOneHash);
        routeTable.put("/text-file.txt", textFileHash);
        routeTable.put("/method_options", optionsHash);
        routeTable.put("/method_options2", options2Hash);
        routeTable.put("/redirect", redirectPathHash);

        return routeTable;
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

    public int getResponseCode(String path, String method){
        int responseCode = 404;
        if(checkPath(path)){
            if(checkMethod(path, method)){
                responseCode = findCode(path, method);
            } else {
                responseCode = 405;
            }
        }
        return responseCode;
    }

    public String[] getAllowedMethods(String path) {
        String[] keysArray = new String[] {};
        if(checkPath(path)) {
            Hashtable hash = (Hashtable)this.routeTable.get(path);
            keysArray = (String[]) hash.keySet().toArray(new String[hash.size()]);
        }
        return keysArray;
    }

}
