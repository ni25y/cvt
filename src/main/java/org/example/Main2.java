package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.Main2.getRef;

/**
 * Copyright：©2023讯兔科技.该代码受知识产权法律保护.如有侵权，讯兔科技保留采用法律手段追究法律责任的权利。
 *
 * @Description: TODO
 * @Author: kevin
 * @Date: 2023/8/4
 **/
public class Main2 {
    public static Input getRef(int ref_start,Input input, Matcher ref_matcher, ArrayList<Object> output ){
        String title = input.substring(0, ref_start);
        String reportId = ref_matcher.group(1);
        if (ref_start > 0) { //if text exists before ref, extract text first
            getText(title, output);
        } // extract ref

        Resp resp2 = new Resp();
        resp2.setType(RespType.REF.getValue());
        RefResp source = new RefResp();
        source.setUrl(reportId);
        source.setTitle(title);
        source.setSubmitTime(LocalDateTime.now());
        resp2.setValue(resp2.getRef(source));
        output.add(resp2.getResp(resp2));
        input = new Input(input.getRest(ref_matcher.end()));
        return input;
    }

    public static Input getTable(int table_start,Input  input,Matcher table_matcher,ArrayList<Object> output){
        String title = input.substring(0, table_start);
        String value = table_matcher.group(1);
        if (table_start > 0) { // if text exists before table, extract text first
            getText(title, output);
        } //then extract table
        Resp resp2 = new Resp();
        resp2.setType(RespType.TABLE.getValue());
        resp2.setValue(value);
        output.add(resp2.getResp(resp2));
        input = new Input(input.getRest(table_matcher.end()));
        return input;
    }

    public static void getText(String title,ArrayList<Object> output){
        Resp resp1 = new Resp();
        resp1.setType(RespType.TEXT.getValue());
        resp1.setValue(title);
        output.add(resp1.getResp(resp1));
    }

    public static Integer getRef_start(Matcher ref_matcher){
        int ref_start = -1;
        //Boolean refExist = false;
        if(ref_matcher.find()){
            ref_start = ref_matcher.start();
        }
        return ref_start;
    }

    public static Integer getTable_start(Matcher table_matcher){
        int table_start = -1;
        //Boolean tableExist = false;
        if (table_matcher.find()){
            table_start = table_matcher.start();
        }
        return table_start;
    }

    public static Matcher refMatcher(Input input){
        Pattern ref_pattern = Pattern.compile("\\[report-id:\\s*(\\d+)]");
       return ref_pattern.matcher(input.getInput());
    }

    public static Matcher tableMatcher(Input input){
        Pattern table_pattern = Pattern.compile("\\{table:(.*?)}");
        return table_pattern.matcher(input.getInput());
    }


    public static void main(String[] args) {
        Input input = new Input("茅台最新研报[report-id:3]{table:[h1,h2,h3],[t1,t2,t3]},显示股价太高了[report-id:2],{table:[h1,h2],[t1,t2]}。");
        ArrayList<Object> output= new ArrayList<>();

        while(input.getLength()>0){
            Matcher ref_matcher = refMatcher(input);
            Integer ref_start = getRef_start(ref_matcher);
            Matcher table_matcher = tableMatcher(input);
            Integer table_start = getTable_start(table_matcher);

            if (ref_start<table_start) { // if ref is before table
                if (ref_start > -1) { // if ref exists
                    input = getRef(ref_start,input,ref_matcher,output);
                }else { // if ref not exist
                    input = getTable(table_start,input,table_matcher,output);
                }
            }else if (ref_start>table_start){ //if table exists before ref
                if (table_start>-1) { // if table exists
                    input = getTable(table_start,input,table_matcher,output);
                }else { // if no table exists
                    input = getRef(ref_start,input,ref_matcher,output);
                }
            }else{ //if no ref and table exist
                getText(input.getInput(),output);
                break;
            }
            ref_matcher.reset(input.getInput());
            table_matcher.reset(input.getInput());
        }
        System.out.println(JSONArray.toJSONString(output));
    }
}
