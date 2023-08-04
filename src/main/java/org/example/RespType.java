package org.example;

/**
 * Copyright：©2023讯兔科技.该代码受知识产权法律保护.如有侵权，讯兔科技保留采用法律手段追究法律责任的权利。
 *
 * @Description: TODO
 * @Author: kevin
 * @Date: 2023/8/4
 **/
public enum RespType {

    TEXT("text"),REF("ref"),TABLE("table");

    RespType(String value) {

        this.value=value;
    }

    private String value;

    public String getValue(){
        return this.value;
    }

}
