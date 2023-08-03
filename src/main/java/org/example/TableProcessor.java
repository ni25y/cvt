package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
class TableProcessor implements DataProcessor {
    private String input;
    private JSONArray output;

    @Override
    public void processInput() {
        JSONObject tableOutput = new JSONObject(); // Create new tableOutput object for each match
        tableOutput.put("type", "table");
        tableOutput.put("value", table_matcher.group(1));
        input = input.substring(table_matcher.end());
        table_matcher.reset(input);
        //System.out.println(tableOutput + "input is" + input);
        output.add(tableOutput);
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