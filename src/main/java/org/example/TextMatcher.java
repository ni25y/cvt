/**
package org.example;

import java.util.regex.Matcher;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TextMatcher{
    private input;
    public TextMatcher(input){
        this.input = input;
    }
    public JSONArray textMatcher(String text, JSONArray output) {
        JSONObject textOutput = new JSONObject();
        textOutput.put("type", "text");
        textOutput.put("value", text);
        output.add(textOutput);
        return "";
    }
}

**/