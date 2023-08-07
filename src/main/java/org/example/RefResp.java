package org.example;

import java.time.LocalDateTime;

/**
 * Copyright：©2023讯兔科技.该代码受知识产权法律保护.如有侵权，讯兔科技保留采用法律手段追究法律责任的权利。
 *
 * @Description: TODO
 * @Author: kevin
 * @Date: 2023/8/4
 **/
public class RefResp {

    String url;

    String title;

    LocalDateTime submitTime;


    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

}