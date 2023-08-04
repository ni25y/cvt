package org.example;

import com.alibaba.fastjson.JSONObject;

/**
 * Copyright：©2023讯兔科技.该代码受知识产权法律保护.如有侵权，讯兔科技保留采用法律手段追究法律责任的权利。
 *
 * @Description: TODO
 * @Author: kevin
 * @Date: 2023/8/4
 **/
public class Resp {

    private String type;

    private Object value;


    public String getType() {
        return type;
    }


    public Object getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    public Object getResp(Resp response){
        //public String getResp(Resp response){
        return response;
    }
    //public String getResp(Resp response){

    //    return JSONObject.toJSONString(response);
    //}

    public Object getRef(RefResp source){
        return source;
    }
}
