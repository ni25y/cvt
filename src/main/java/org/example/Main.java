package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

public class Main implements DataProcessor {
    private String input;
    private JSONArray output;

    public Main(String input) {
        this.input = input;
        output = new JSONArray();
    }

    public static void main(String[] args) {
        String input = "茅台最新研报[report-id:1]{table:[h1,h2,h3],[t1,t2,t3]},显示股价太高了[report-id:2],{table:[h1,h2],[t1,t2]}。";
        Main main = new Main(input);
        main.processInput();
        JSONArray output = main.getOutput();
        System.out.println(output);
    }

    @Override
    public void processInput() {
        Pattern refPattern = Pattern.compile("\\[report-id:\\s*(\\d+)]");
        Pattern tablePattern = Pattern.compile("\\{table:(.*?)}");

        while (input.length() > 0) {
            Matcher refMatcher = refPattern.matcher(input);
            int refStart = -1;
            if (refMatcher.find()) {
                refStart = refMatcher.start();
            }

            Matcher tableMatcher = tablePattern.matcher(input);
            int tableStart = -1;
            if (tableMatcher.find()) {
                tableStart = tableMatcher.start();
            }

            if (refStart < tableStart) {
                if (refStart > -1) {
                    // Process ref
                    String title = input.substring(0, refStart);
                    String reportId = refMatcher.group(1);
                    // Add ref output to the output JSONArray
                    JSONObject refOutput = new JSONObject();
                    JSONObject urlLink = new JSONObject();
                    JSONObject titleObject = new JSONObject();
                    JSONObject submitTime = new JSONObject();

                    urlLink.put("url", reportId);
                    titleObject.put("title", title);
                    submitTime.put("submission time", "a time");

                    JSONArray source = new JSONArray();
                    source.add(urlLink);
                    source.add(titleObject);
                    source.add(submitTime);

                    refOutput.put("type", "ref");
                    refOutput.put("value", source);

                    output.add(refOutput);
                    input = input.substring(refMatcher.end());
                }
            } else if (refStart > tableStart) {
                if (tableStart > -1) {
                    // Process table
                    String tableData = tableMatcher.group(1);
                    // Add table output to the output JSONArray
                    JSONObject tableOutput = new JSONObject();
                    tableOutput.put("type", "table");
                    tableOutput.put("value", tableData);

                    output.add(tableOutput);
                    input = input.substring(tableMatcher.end());
                }
            } else {
                break;
            }
        }
    }

            @Override
            public JSONArray getOutput () {
                return output;
            }

            @Override
            public void setInput (String input){
                this.input = input;
            }
        }
