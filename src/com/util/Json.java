package com.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class Json {
    static public String writeStatus(int status,String message) {
        JSONObject obj = new JSONObject();
        obj.put("status",status);
        obj.put("message",message);
        return obj.toString();
    }
}
