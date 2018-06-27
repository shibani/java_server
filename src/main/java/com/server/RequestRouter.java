package com.server;

import java.util.Arrays;
import java.util.List;

public class RequestRouter {

    private final List<String> whiteListedPaths = Arrays.asList(
            "/",
            "/form",
            "/put-target"
    );

    private int result;

    RequestRouter(String requestPath){
        if(whiteListedPaths.contains(requestPath)){
            this.result = 200;
        } else {
            this.result = 404;
        }
    }

    public int getResult(){
        return result;
    }
}
