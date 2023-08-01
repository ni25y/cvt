package org.example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    JSONArray output;
    String input;

    public Main(String input) {
        // JSON array that contains all the information
        output = new JSONArray();
        this.input = input;

    }

    public String refMatcher(Matcher ref_matcher) {

        // Find pattern of ref
        while (ref_matcher.find()) {
            JSONObject textOutput = new JSONObject(); // Create new textOutput object for each match
            textOutput.put("type", "text");
            textOutput.put("value", ref_matcher.group(1));
            output.add(textOutput);

            JSONArray source = new JSONArray(); // Create new JSONArray for each match
            JSONObject refOutput = new JSONObject(); // Create new refOutput object for each match
            JSONObject urlLink = new JSONObject();
            JSONObject title = new JSONObject();
            JSONObject submitTime = new JSONObject();

            urlLink.put("url", ref_matcher.group(2)); // Add the URL to the urlLink object
            title.put("title", ref_matcher.group(1)); // Empty string for the title
            submitTime.put("submission time", "a time");

            source.add(urlLink);
            source.add(title);
            source.add(submitTime);
            refOutput.put("type", "ref");
            refOutput.put("value", source);
            output.add(refOutput);
        }
        input = ref_matcher.replaceAll("");
        return input;

        }



    public String tableMatcher(Matcher table_matcher) {
        while (table_matcher.find()) {
            JSONObject tableOutput = new JSONObject(); // Create new tableOutput object for each match
            tableOutput.put("type", "table");
            tableOutput.put("value", table_matcher.group(1));
            output.add(tableOutput);
        }

        input = table_matcher.replaceAll(""); // Remove all table occurrences from the input
        return input;
    }


    public String textMatcher(Matcher text_matcher) {
        while (text_matcher.find()) {

            JSONObject textOutput = new JSONObject(); // Create new textOutput object for each match
            textOutput.put("type", "text");
            textOutput.put("value", text_matcher.group(1));
            output.add(textOutput);
            input = text_matcher.replaceAll("");

        }
        return input;
    }

    public static void main(String[] args) {
        String input = "茅台最新研报[report-id:1][report-id:2],{table:[h1,h2，h3],[t1,t2,t3]},显示股价太高了。{table:[h1,h2],[t1,t2]}";

        Main test1 = new Main(input);


        Pattern ref_pattern = Pattern.compile("^(.*?)\\[report-id:\\s*(\\d+)\\]");
        Matcher ref_matcher = ref_pattern.matcher(input);
        input = test1.refMatcher(ref_matcher);

        // Find pattern of table
        Pattern table_pattern = Pattern.compile("\\{table:(.*?)}");
        Matcher table_matcher = table_pattern.matcher(input);
        input =   test1.tableMatcher(table_matcher);
                // Find pattern of text
        Pattern text_pattern = Pattern.compile("^(.*?)[.?！？。～]$");
        Matcher text_matcher = text_pattern.matcher(input);
        test1.textMatcher(text_matcher);

        System.out.println(test1.output);
    }
}
