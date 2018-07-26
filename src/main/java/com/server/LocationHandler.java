package com.server;

import java.util.Hashtable;

public class LocationHandler implements IResponseHeaderHandler {
    private Hashtable locationHashtable;
    private static final String LOCATION_KEY = "Location";

    LocationHandler(){
        this.locationHashtable = new Hashtable();
        locationHashtable.put("/redirect", "/");
    }

    public String createLine(RequestParams requestParams, ResponseParams responseParams){
        String locationHeader = "";
        if(responseParams.getLocationHeader() != null){
            locationHeader = responseParams.getLocationHeader();
        } else {
            locationHeader = (String)this.locationHashtable.get(requestParams.getPath());
        }
        return locationHeader == null ? "" : LOCATION_KEY + ": " + locationHeader;
    }
}

