package com.server;

import java.util.*;
import java.util.Hashtable;

public class RequestRouter {

    private Hashtable createHashtable(){
        Hashtable routeTable = new Hashtable();

        Hashtable rootHash = new Hashtable();
        rootHash.put("GET", 200);
        rootHash.put("HEAD", 200);

        Hashtable formHash = new Hashtable();
        formHash.put("POST", 200);

        Hashtable putTargetHash = new Hashtable();
        putTargetHash.put("PUT", 200);

        routeTable.put("/", rootHash);
        routeTable.put("/form", formHash);
        routeTable.put("/put-target", putTargetHash);

        return routeTable;
    }

    private boolean checkPath(String requestPath){

        return createHashtable().containsKey(requestPath);
    }

    private boolean checkMethod(String requestPath, String method){
        Hashtable methodTable = (Hashtable) createHashtable().get(requestPath);
        return methodTable.containsKey(method);
    }

    private int findCode(String requestPath, String method){
        Hashtable methodTable = (Hashtable) createHashtable().get(requestPath);
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



}
