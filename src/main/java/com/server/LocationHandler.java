package com.server;

import java.util.Hashtable;

public class LocationHandler {
    private Hashtable locationHashtable;
    private static final String LOCATION_KEY = "Location";

    LocationHandler(){
        this.locationHashtable = new Hashtable();
        locationHashtable.put("/redirect", "/");
    }

    public String createLine(String path, String method){
        String value = (String)this.locationHashtable.get(path);
        return value == null ? "" : LOCATION_KEY + ": " + value;
    }
}

