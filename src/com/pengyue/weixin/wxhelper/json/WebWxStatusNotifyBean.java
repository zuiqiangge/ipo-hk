/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pengyue.weixin.wxhelper.json;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Administrator
 */
public class WebWxStatusNotifyBean {
    @SerializedName("BaseResponse")
    private BaseResponse br;
    private long MsgId;
    
    public WebWxStatusNotifyBean(){
        br=new BaseResponse();       
    }

    public BaseResponse getBr() {
        return br;
    }

    public void setBr(BaseResponse br) {
        this.br = br;
    }

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long MsgId) {
        this.MsgId = MsgId;
    }

    @Override
    public String toString() {
        return "WebWxStatusNotifyBean{" + "br=" + br + ", MsgId=" + MsgId + '}';
    }
    
    
}
