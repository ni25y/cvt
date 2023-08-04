package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

class Output {
    public String type;
    public List<String> value;


    public JSONObject getTitle(String type, String value) {
        this.type = type;
        JSONObject object = new JSONObject();
        object.put("type", type);
        object.put("value", value);
        return object;
    }

    public JSONObject getRef(String id, String title) {
        JSONArray source = new JSONArray();
        JSONObject urlLink = new JSONObject();
        JSONObject titleObject = new JSONObject();
        JSONObject submitTime = new JSONObject();
        JSONObject object = new JSONObject();
        urlLink.put("url", id); // Add the URL to the urlLink object
        titleObject.put("title", title); // Extract the report ID as the title
        submitTime.put("submission time", "a time");

        source.add(urlLink);
        source.add(titleObject);
        source.add(submitTime);
        object.put("type", "ref");
        object.put("value", source);
        return object;
    }

    public JSONObject getTable(String type, String value){
        this.type = type;
        JSONObject tableOutput = new JSONObject(); // Create new tableOutput object for each match
        tableOutput.put("type", "table");
        tableOutput.put("value",value);
        return tableOutput;
}



}