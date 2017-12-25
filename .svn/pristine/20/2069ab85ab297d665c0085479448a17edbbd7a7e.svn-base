/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pengyue.weixin.wxhelper.json;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

/**
 *
 * @author Administrator
 * 用于表达 心中机制中的返回信息
 */
public class HeartBeatBean {
    private String retcode;
    private String selector;

    //window.synccheck={retcode:"0",selector:"0"}
    public static HeartBeatBean parseString(String input){
        String rs=null;
        Pattern p = Pattern.compile("\\{(.*?)\\}");
        Matcher m = p.matcher(input);
        while (m.find()) {
             rs= m.group();
            break;
        }
        if(rs!=null){
             return new Gson().fromJson(rs, HeartBeatBean.class);
        }
        return null;        
    }

    @Override
    public String toString() {
        return "HeartBeatBean{" + "retcode=" + retcode + ", selector=" + selector + '}';
    }
    
    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }
    
}
