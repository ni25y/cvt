package org.example;

import com.alibaba.fastjson.JSONArray;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    static final Pattern REF_PATTERN = Pattern.compile("\\[report-id:\\s*(\\d+)]");

    static final Pattern TABLE_PATTERN = Pattern.compile("\\{table:(.*?)}");

    public static Resp getRef(Matcher ref_matcher ){
        String reportId = ref_matcher.group(1);
        Resp resp2 = new Resp();
        resp2.setType(RespType.REF.getValue());
        RefResp source = new RefResp();
        source.setUrl(reportId);
        source.setTitle("根据reportId从数据库获取的信息");
        source.setSubmitTime(LocalDateTime.now());
        resp2.setValue(source);
        return resp2;
    }

    public static Resp getTable(Matcher table_matcher){
        String value = table_matcher.group(1);
        Resp resp2 = new Resp();
        resp2.setType(RespType.TABLE.getValue());
        resp2.setValue(value);
        return resp2;
    }

    public static Resp getText(String title){
        Resp resp1 = new Resp();
        resp1.setType(RespType.TEXT.getValue());
        resp1.setValue(title);
        return resp1;
    }


    public static Integer getMatchStartIndex(Matcher matcher){
        if (matcher.find()){
            return matcher.start();
        }
        return Integer.MAX_VALUE;
    }

    public static Matcher getStartMatch(Input input){
        Matcher refMatcher = REF_PATTERN.matcher(input.getInput());
        Integer refStartIndex = getMatchStartIndex(refMatcher);
        Matcher tableMatcher = TABLE_PATTERN.matcher(input.getInput());
        Integer tableStartIndex = getMatchStartIndex(tableMatcher);
        Integer minMatchStart = Collections.min(Arrays.asList(refStartIndex,tableStartIndex));
        // 找不到特殊格式，此时输入为纯文本
        if(minMatchStart.equals(Integer.MAX_VALUE)){
            return null;
        }else if(refStartIndex.equals(minMatchStart)){
            return refMatcher;
        }else if(tableStartIndex.equals(minMatchStart)){
            return tableMatcher;
        }
        throw new IllegalArgumentException("未处理的表达式。");
    }

    public static void main(String[] args) {

        Input input = new Input("茅台最新研报[report-id:3]{table:[h1,h2,h3],[t1,t2,t3]},显示股价太高了[report-id:2],{table:[h1,h2],[t1,t2]}。");
        ArrayList<Object> output= new ArrayList<>();
        while(input.getLength()>0){
            Matcher startMatch = getStartMatch(input);
            if(startMatch==null){
                // 纯文本 直接跳出循环
                output.add(getText(input.getInput()));
                break;
            }else{
                // 非纯文本 先处理特殊样式前的文本
                String title = input.substring(0, startMatch.start());
                if(title.length()!=0){
                    output.add(getText(title));
                }
                // 处理特殊格式数据
                if(startMatch.pattern().equals(REF_PATTERN)){
                    output.add(getRef(startMatch));
                }else if(startMatch.pattern().equals(TABLE_PATTERN)){
                    output.add(getTable(startMatch));
                }
                input = new Input(input.getRest(startMatch.end()));
            }
            System.out.println(JSONArray.toJSONString(output));
        }

    }
}
