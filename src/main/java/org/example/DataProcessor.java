package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public interface DataProcessor {
    void processInput();
    JSONArray getOutput();
    void setInput(String input);
}
