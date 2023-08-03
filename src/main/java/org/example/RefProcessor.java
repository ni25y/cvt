package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.example.DataProcessor;

class RefProcessor implements DataProcessor {
    private String input;
    private JSONArray output;

    @Override
    public void processInput() {
         JSONArray source = new JSONArray();
        JSONObject refOutput = new JSONObject();
        JSONObject urlLink = new JSONObject();
        JSONObject titleObject = new JSONObject();
        JSONObject submitTime = new JSONObject();

        urlLink.put("url", reportId); // Add the URL to the urlLink object
        titleObject.put("title", title); // Extract the report ID as the title
        submitTime.put("submission time", "a time");

        source.add(urlLink);
        source.add(titleObject);
        source.add(submitTime);
        refOutput.put("type", "ref");
        refOutput.put("value", source);
        //System.out.println(refOutput);
        input = input.substring(ref_matcher.end());
        output.add(refOutput);
        ref_matcher.reset(input);
    }

    @Override
    public JSONArray getOutput() {
        return output;
    }

    @Override
    public void setInput(String input) {
        this.input = input;
    }
}