package com.example.lj.ljplayerhd.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by wh on 2017/1/9.
 */

public class DefinitionBean implements Serializable{

    private String definition;
    private String url;
    private boolean isEnable;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEnable(){
        return TextUtils.isEmpty(url)?false:true;
    }
}
