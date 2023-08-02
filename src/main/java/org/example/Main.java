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

    public void organize(){
        // Create a HashMap to store the mapping between array elements and JSON objects
        HashMap<Integer, JSONObject> hashMap = new HashMap<>();
        for (int i = 0; i < indexArray.size(); i++) {
            hashMap.put(indexArray.get(i), output.getJSONObject(i));
        }

        // Organize the four JSON objects according to the array
        JSONObject[] organizedJsonObjects = new JSONObject[indexArray.size()];
        for (int i = 0; i < indexArray.size(); i++) {
            organizedJsonObjects[i] = hashMap.get(indexArray.get(i));
            System.out.println(String.valueOf(indexArray.get(i))+organizedJsonObjects[i]);
        }

        // Print the result
        for (JSONObject jsonObject : organizedJsonObjects) {
            System.out.println(jsonObject.toJSONString());
        }
    }



    public JSONArray refMatcher(String title, String reportId) {
        //String title = "";

        //while (ref_matcher.find()) {
            // Find the title without modifying the input
            //Pattern title_pattern = Pattern.compile("^(.*?)");
            //Matcher title_matcher = title_pattern.matcher(input.substring(ref_matcher.start()));

            //if (title_matcher.find()) {
                //String title = ref_matcher.group(1);
                //input = input.substring(ref_matcher.end());


                //ref_matcher.reset(input);
            //title_matcher.group();
                //indexArray.add(0);
            //}

            // Process the title
            //if (!title.isEmpty()) {
                //indexArray.add(ref_matcher.start());
                JSONObject textOutput = new JSONObject();
                textOutput.put("type", "text");
                textOutput.put("value", title);
                System.out.println(title);
                output.add(textOutput);

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
                refOutput.put("value", reportId);
                output.add(refOutput);
              //  System.out.println("input in ref"+input);
            //ref_matcher.reset(input);
          //  input = ref_matcher.replaceFirst("");
          //  ref_matcher.reset(input);
            //}
        return output;
        }
       // return input;








    public String tableMatcher(Matcher table_matcher) {

        while (table_matcher.find()) {
            //indexArray.add(table_matcher.start());
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
            //indexArray.add(text_matcher.start());
            JSONObject textOutput = new JSONObject(); // Create new textOutput object for each match
            textOutput.put("type", "text");
            textOutput.put("value", text_matcher.group(1));
            output.add(textOutput);
            input = text_matcher.replaceAll("");

        }
        return input;
    }

    public static void main(String[] args) {

        String input = "茅台最新研报[report-id:1]{table:[h1,h2,h3],[t1,t2,t3]},显示股价太高了[report-id:2],{table:[h1,h2],[t1,t2]}。";
        //input = "这是一段普通文本。";
        //input = "这是一个引用符号[report-id:1]。";
        input = "这是一个表格{table:[h1,h2],[t1,t2]}。";
        input = "这是一段普通文本[report-id:1]，然后是另一段普通文本[report-id:2]。";
        input = "这是一个表格{table:[h1,h2],[t1,t2]}，然后是另一个表格{table:[h3,h4],[t3,t4]}。";
        input = "这是一段普通文本[report-id:1]，然后是一个表格{table:[h1,h2],[t1,t2]}，接着是另一段普通文本。";
        input = "[report-id:1]饮用";
        input = "{table:[h1,h2],[t1,t2]}[report-id:1][report-id:2]{table:[h1,h2,h3],[t1,t2,t3]}";

        Pattern ref_pattern = Pattern.compile("\\[report-id:\\s*(\\d+)]");
        Pattern table_pattern = Pattern.compile("\\{table:(.*?)}");
        //Main test1 = new Main(input);
        JSONArray output = new JSONArray();
        while(input.length()>0){
            System.out.println("input = "+ input);
            Matcher ref_matcher = ref_pattern.matcher(input);
            int ref_start = -1;
            //Boolean refExist = false;
            if(ref_matcher.find()){
                ref_start = ref_matcher.start();
            }

            Matcher table_matcher = table_pattern.matcher(input);
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

                        JSONObject textOutput = new JSONObject();
                        textOutput.put("type", "text");
                        textOutput.put("value", title);
                        System.out.println(textOutput);
                        output.add(textOutput);
                    } // extract ref
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
                    System.out.println(refOutput);
                    input = input.substring(ref_matcher.end());
                    output.add(refOutput);

                    ref_matcher.reset(input);
                } else { // if ref not exist
                    if (table_start > 0) { // if text exists before table, extract text first
                        String text = input.substring(0, table_start);
                        JSONObject textOutput = new JSONObject();
                        textOutput.put("type", "text");
                        textOutput.put("value", text);
                        System.out.println(textOutput);
                        output.add(textOutput);
                    } //then extract table
                    JSONObject tableOutput = new JSONObject(); // Create new tableOutput object for each match
                    tableOutput.put("type", "table");
                    tableOutput.put("value", table_matcher.group(1));
                    input = input.substring(table_matcher.end());
                    table_matcher.reset(input);
                    System.out.println(tableOutput + "input is" + input);
                    output.add(tableOutput);

                }
            }else if (ref_start>table_start){ //if table exists before ref
                if (table_start>-1) { // if table exists
                    if (table_start > 0) { // if text exists before table, extract text first
                        String text = input.substring(0, table_start);
                        //String reportId = ref_matcher.group(1);
                        JSONObject textOutput = new JSONObject();
                        textOutput.put("type", "text");
                        textOutput.put("value", text);
                        System.out.println(textOutput);
                        output.add(textOutput);
                    } //then extract table
                    JSONObject tableOutput = new JSONObject(); // Create new tableOutput object for each match
                    tableOutput.put("type", "table");
                    tableOutput.put("value", table_matcher.group(1));
                    input = input.substring(table_matcher.end());
                    table_matcher.reset(input);
                    System.out.println(tableOutput + "input is" + input);
                    output.add(tableOutput);
                }else { // if no table exists
                    String title = input.substring(0, ref_start);
                    String reportId = ref_matcher.group(1);
                    if (ref_start > 0) { // if text exists before ref, extract text first

                        //String reportId = ref_matcher.group(1);
                        JSONObject textOutput = new JSONObject();
                        textOutput.put("type", "text");
                        textOutput.put("value", title);
                        System.out.println(textOutput);
                        output.add(textOutput);
                    } //extract ref
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
                    System.out.println(refOutput);
                    input = input.substring(ref_matcher.end());
                    output.add(refOutput);

                    ref_matcher.reset(input);
                }
            }else{ //if no ref and table exist
                String text = input;
                JSONObject textOutput = new JSONObject();
                textOutput.put("type", "text");
                textOutput.put("value", text);

                System.out.println(textOutput);
                output.add(textOutput);
                input = input.substring(0,0);
            }
            /**
                }else if (ref_start>table_start){
                    if (tableExist){
                        if (table_start!=0){
                            JSONObject textOutput = new JSONObject(); // Create new textOutput object for each match
                            textOutput.put("type", "text");
                            textOutput.put("value", input.substring(0,table_start));
                            input = input.substring(table_matcher.start());
                            table_matcher.reset(input);
                            System.out.println(textOutput);
                            output.add(textOutput);
                        }
                        JSONObject tableOutput = new JSONObject(); // Create new tableOutput object for each match
                        tableOutput.put("type", "table");
                        tableOutput.put("value", table_matcher.group(1));
                        input = table_matcher.replaceFirst("");
                        table_matcher.reset(input);
                        System.out.println(tableOutput+"input is"+input);
                        output.add(tableOutput);
                        }else{
                        JSONObject textOutput = new JSONObject(); // Create new textOutput object for each match
                        textOutput.put("type", "text");
                        textOutput.put("value", input.substring(0,ref_matcher.start()));
                        input = input.substring(ref_matcher.end());
                        System.out.println(textOutput);
                        output.add(textOutput);
                    }



                        }
                }
            }
            /**
            if((ref_start<table_start)&&(refExist)){
                String title = input.substring(0,ref_start);
                String reportId = ref_matcher.group(1);
                JSONObject textOutput = new JSONObject();
                textOutput.put("type", "text");
                textOutput.put("value", title);
                System.out.println(textOutput);
                output.add(textOutput);

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
                System.out.println(refOutput);
                input = input.substring(ref_matcher.end());
                output.add(refOutput);

                ref_matcher.reset(input);


                //System.out.println("text="+title+"ref : "+reportId +" output="+input);
            }else if ((tableExist)&&(table_start!=0)){
                JSONObject textOutput = new JSONObject(); // Create new textOutput object for each match
                textOutput.put("type", "text");
                textOutput.put("value", input.substring(ref_start,table_start));
                input = input.substring(table_matcher.start());
                table_matcher.reset(input);
                System.out.println(textOutput);
                output.add(textOutput);
            }
            else if (tableExist){
                JSONObject tableOutput = new JSONObject(); // Create new tableOutput object for each match
                tableOutput.put("type", "table");
                tableOutput.put("value", table_matcher.group(1));
                input = table_matcher.replaceFirst("");
                table_matcher.reset(input);
                System.out.println(tableOutput+"input is"+input);
                output.add(tableOutput);

            }else{
                JSONObject textOutput = new JSONObject(); // Create new textOutput object for each match
                textOutput.put("type", "text");
                textOutput.put("value", input);
                input = input.substring(0,0);
                System.out.println(textOutput);
                output.add(textOutput);
            }
        }
             **/
        System.out.println(output);

        //Pattern ref_pattern = Pattern.compile("\\[report-id:\\s*(\\d+)]");
        //Pattern ref_pattern = Pattern.compile("\\[report-id:\\s*(\\d+)]");
        /**
        Matcher ref_matcher = ref_pattern.matcher(input);


        input = test1.refMatcher(ref_matcher);

        // Find pattern of table
        //Pattern table_pattern = Pattern.compile("\\{table:(.*?)}");
        Matcher table_matcher = table_pattern.matcher(input);
        input =   test1.tableMatcher(table_matcher);
                // Find pattern of text
        Pattern text_pattern = Pattern.compile("^(.*?)[.?！？。～]$");
        Matcher text_matcher = text_pattern.matcher(input);
        test1.textMatcher(text_matcher);

        test1.organize();
        //System.out.println(test1.output);
        //System.out.println(input);
        System.out.println(test1.indexArray.toString());**/
    }
    }
}
