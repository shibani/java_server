package com.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestRouter {

    private final List<String> whiteListedPaths = Arrays.asList(
            "/",
            "/form",
            "/put-target"
    );

    //RequestRouter(){}

    public boolean checkPath(String requestPath){
        return whiteListedPaths.contains(requestPath);
    }


}
