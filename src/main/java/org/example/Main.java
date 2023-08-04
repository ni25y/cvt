package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class Main {
    JSONArray output;
    String input;

    ArrayList<Integer> indexArray;

    public Main(String input) {
        // JSON array that contains all the information
        output = new JSONArray();
        this.input = input;
        indexArray = new ArrayList<Integer>();


    }




    public static void main(String[] args) {

        Input input = new Input("茅台最新研报[report-id:1]{table:[h1,h2,h3],[t1,t2,t3]},显示股价太高了[report-id:2],{table:[h1,h2],[t1,t2]}。");
        //input.setInput("茅台最新研报[report-id:1]{table:[h1,h2,h3],[t1,t2,t3]},显示股价太高了[report-id:2],{table:[h1,h2],[t1,t2]}。");
        input.getInput();

        Output output = new Output();
        //input = "这是一段普通文本。";
        //input = "这是一个引用符号[report-id:1]。";
        /**
        input = "这是一个表格{table:[h1,h2],[t1,t2]}。";
        input = "这是一段普通文本[report-id:1]，然后是另一段普通文本[report-id:2]。";
        input = "这是一个表格{table:[h1,h2],[t1,t2]}，然后是另一个表格{table:[h3,h4],[t3,t4]}。";
        input = "这是一段普通文本[report-id:1]，然后是一个表格{table:[h1,h2],[t1,t2]}，接着是另一段普通文本。";
        input = "[report-id:1]饮用";
        input = "{table:[h1,h2],[t1,t2]}[report-id:1][report-id:2]{table:[h1,h2,h3],[t1,t2,t3]}";
**/
        Pattern ref_pattern = Pattern.compile("\\[report-id:\\s*(\\d+)]");
        Pattern table_pattern = Pattern.compile("\\{table:(.*?)}");
        //Main test1 = new Main(input);
        JSONArray outputArray = new JSONArray();

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
            //System.out.println(table_matcher.find());
            //int table_start = table_matcher.start();
            if (ref_start<table_start) { // if ref is before table
                if (ref_start > -1) { // if ref exists
                    String title = input.substring(0, ref_start);
                    String reportId = ref_matcher.group(1);
                    if (ref_start > 0) { //if text exists before ref, extract text first
                        outputArray.add(output.getTitle("text",title));
                    } // extract ref
                    // source = new String[3];
                    //source[1] = reportId;
                    //source[2] = title;
                   // source[3] = "time";
                    outputArray.add(output.getRef(reportId,title));
                    input = new Input(input.getRest(ref_matcher.end()));
                    ref_matcher.reset(input.getInput());
                } else { // if ref not exist
                    if (table_start > 0) { // if text exists before table, extract text first
                        String text = input.substring(0, table_start);
                        outputArray.add(output.getTitle("text",text));
                    } //then extract table
                    outputArray.add(output.getTable("table",table_matcher.group(1)));

                    input = new Input(input.getRest(table_matcher.end()));
                    table_matcher.reset(input.getInput());

                }
            }else if (ref_start>table_start){ //if table exists before ref
                if (table_start>-1) { // if table exists
                    if (table_start > 0) { // if text exists before table, extract text first
                        String text = input.substring(0, table_start);
                        //String reportId = ref_matcher.group(1);

                        outputArray.add(output.getTitle("text",text));
                    } //then extract table
                    outputArray.add(output.getTable("table",table_matcher.group(1)));

                    input = new Input(input.getRest(table_matcher.end()));
                    table_matcher.reset(input.getInput());

                }else { // if no table exists
                    String title = input.substring(0, ref_start);
                    String reportId = ref_matcher.group(1);
                    if (ref_start > 0) { // if text exists before ref, extract text first

                        outputArray.add(output.getTitle("text",title));
                    } //extract ref

                    outputArray.add(output.getRef(reportId,title));
                    input = new Input(input.getRest(ref_matcher.end()));
                    ref_matcher.reset(input.getInput());

                }
            }else{ //if no ref and table exist
                String text = input.getInput();
                outputArray.add(output.getTitle("text",text));
                input = new Input(null);
            }


            System.out.println(outputArray);
        }


    }
}