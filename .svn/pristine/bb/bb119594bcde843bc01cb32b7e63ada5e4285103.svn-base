/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pengyue.weixin.wxhelper.json;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 *
 * @author Administrator
 * @desc 用于封装好请求用的json，发送给https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r={time}&skey=该地址
 */
public class WebWxInitJson  {
    @SerializedName("BasicRequest")
    private BaseRequest  basicRequest;


    @Override
    public String toString() {
        return "WebWxInitJson{" + "basicRequest=" + basicRequest + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.basicRequest);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WebWxInitJson other = (WebWxInitJson) obj;
        if (!Objects.equals(this.basicRequest, other.basicRequest)) {
            return false;
        }
        return true;
    }

    public WebWxInitJson(BaseRequest b) {
        basicRequest=b;
    }
    public BaseRequest getBasicRequest() {
        return basicRequest;
    }

    public void setBasicRequest(BaseRequest basicRequest) {
        this.basicRequest = basicRequest;
    }
    
    
}
 