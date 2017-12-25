package com.pengyue.weixin.wxhelper.json;


import com.google.gson.annotations.SerializedName;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class WebWxStatusNotifyJson {
    
    @SerializedName("BaseRequest")
    private BaseRequest br;
    
    private String ClientMsgId;
    private int Code;
    private String FromUserName;
    private String ToUserName;
    
    public WebWxStatusNotifyJson(String uin,String uid,String skey,String deviceid){
        br=new BaseRequest(uin,uid,skey,deviceid);
        ClientMsgId="";
        Code=3;
        FromUserName=ToUserName="";
    }

    public BaseRequest getBr() {
        return br;
    }

    public void setBr(BaseRequest br) {
        this.br = br;
    }

    public String getClientMsgId() {
        return ClientMsgId;
    }

    public void setClientMsgId(String ClientMsgId) {
        this.ClientMsgId = ClientMsgId;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String FromUserName) {
        this.FromUserName = FromUserName;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String ToUserName) {
        this.ToUserName = ToUserName;
    }
    
}
