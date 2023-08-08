package org.example;

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
        return response;
    }



    @Override
    public String toString() {
        return "Resp{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
