package org.example;

import com.alibaba.fastjson.JSONObject;

public class Input {
    String input;

    public Input(String input){
        this.input = input;
    }

    public String getInput(){
        return input;
    }

    public int getLength(){
        return input.length();
    }

    public String substring(int start, int end){
        return input.substring(start,end);
    }

    public String getRest(int start){
        return input.substring(start);
    }
}
