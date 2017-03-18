package com.example.dw.bmobdemo;

import cn.bmob.v3.BmobObject;

/**
 * Created by dw on 2017-3-10.
 */

public class Feedback extends BmobObject{
    private String name;
    private String feedback;
    public void setName(String name) {
        this.name = name;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getName() {
        return name;
    }

    public String getFeedback() {
        return feedback;
    }
}
