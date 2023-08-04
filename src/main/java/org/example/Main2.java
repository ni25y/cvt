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

/**
 * Copyright：©2023讯兔科技.该代码受知识产权法律保护.如有侵权，讯兔科技保留采用法律手段追究法律责任的权利。
 *
 * @Description: TODO
 * @Author: kevin
 * @Date: 2023/8/4
 **/
public class Main2 {
    public static void main(String[] args) {
        Input input = new Input("茅台最新研报[report-id:3]{table:[h1,h2,h3],[t1,t2,t3]},显示股价太高了[report-id:2],{table:[h1,h2],[t1,t2]}。");
        //input.setInput("茅台最新研报[report-id:1]{table:[h1,h2,h3],[t1,t2,t3]},显示股价太高了[report-id:2],{table:[h1,h2],[t1,t2]}。");

        //ArrayList<String> output= new ArrayList<String>();
        ArrayList<Object> output= new ArrayList<>();
        //input = "这是一段普通文本。";
        //input = "这是一个引用符号[report-id:1]。";

        Pattern ref_pattern = Pattern.compile("\\[report-id:\\s*(\\d+)]");
        Pattern table_pattern = Pattern.compile("\\{table:(.*?)}");
        //Main test1 = new Main(input);
        //JSONArray outputArray = new JSONArray();


        while(input.getLength()>0){
            //System.out.println("input = "+ input);
            Matcher ref_matcher = ref_pattern.matcher(input.getInput());
            int ref_start = -1;
            //Boolean refExist = false;
            if(ref_matcher.find()){
                ref_start = ref_matcher.start();
            }

            Matcher table_matcher = table_pattern.matcher(input.getInput());
            int table_start = -1;
            //Boolean tableExist = false;
            if (table_matcher.find()){
                //tableExist = true;
                table_start = table_matcher.start();
            }

            if (ref_start<table_start) { // if ref is before table
                if (ref_start > -1) { // if ref exists
                    String title = input.substring(0, ref_start);
                    String reportId = ref_matcher.group(1);
                    if (ref_start > 0) { //if text exists before ref, extract text first
                        Resp resp1 = new Resp();
                        resp1.setType(RespType.TEXT.getValue());
                        resp1.setValue(title);
                        output.add(resp1.getResp(resp1));
                    } // extract ref
                    // source = new String[3];
                    //source[1] = reportId;
                    //source[2] = title;
                    // source[3] = "time";
                    Resp resp2 = new Resp();
                    resp2.setType(RespType.REF.getValue());
                    RefResp source = new RefResp();
                    source.setUrl(reportId);
                    source.setTitle(title);
                    source.setSubmitTime(LocalDateTime.now());

                    resp2.setValue(resp2.getRef(source));

                    output.add(resp2.getResp(resp2));

                    input = new Input(input.getRest(ref_matcher.end()));
                    ref_matcher.reset(input.getInput());
                } else { // if ref not exist
                    if (table_start > 0) { // if text exists before table, extract text first
                        String title = input.substring(0, table_start);
                        Resp resp1 = new Resp();
                        resp1.setType(RespType.TEXT.getValue());
                        resp1.setValue(title);
                        output.add(resp1.getResp(resp1));
                    } //then extract table
                    Resp resp2 = new Resp();
                    resp2.setType(RespType.TABLE.getValue());
                    resp2.setValue(table_matcher.group(1));
                    output.add(resp2.getResp(resp2));

                    input = new Input(input.getRest(table_matcher.end()));
                    table_matcher.reset(input.getInput());

                }
            }else if (ref_start>table_start){ //if table exists before ref
                if (table_start>-1) { // if table exists
                    if (table_start > 0) { // if text exists before table, extract text first
                        String title = input.substring(0, table_start);
                        //String reportId = ref_matcher.group(1);
                        Resp resp1 = new Resp();
                        resp1.setType(RespType.TEXT.getValue());
                        resp1.setValue(title);
                        output.add(resp1.getResp(resp1));

                    } //then extract table
                    Resp resp2 = new Resp();
                    resp2.setType(RespType.TABLE.getValue());
                    resp2.setValue(table_matcher.group(1));
                    output.add(resp2.getResp(resp2));


                    input = new Input(input.getRest(table_matcher.end()));
                    table_matcher.reset(input.getInput());

                }else { // if no table exists
                    String title = input.substring(0, ref_start);
                    String reportId = ref_matcher.group(1);
                    if (ref_start > 0) { // if text exists before ref, extract text first
                        Resp resp1 = new Resp();

                        resp1.setType(RespType.TEXT.getValue());
                        resp1.setValue(title);
                        output.add(resp1.getResp(resp1));
                    } //extract ref
                    Resp resp2 = new Resp();
                    resp2.setType(RespType.REF.getValue());
                    RefResp source = new RefResp();
                    source.setUrl(reportId);
                    source.setTitle(title);
                    source.setSubmitTime(LocalDateTime.now());

                    resp2.setValue(resp2.getRef(source));
                    output.add(resp2.getResp(resp2));
                    input = new Input(input.getRest(ref_matcher.end()));
                    ref_matcher.reset(input.getInput());

                }
            }else{ //if no ref and table exist
                Resp resp1 = new Resp();
                String title = input.getInput();
                resp1.setType(RespType.TEXT.getValue());
                resp1.setValue(title);
                output.add(resp1.getResp(resp1));
                break;
            }
            System.out.println(JSONArray.toJSONString(output));



        }
        System.out.println(JSONArray.toJSONString(output));



    }
}
