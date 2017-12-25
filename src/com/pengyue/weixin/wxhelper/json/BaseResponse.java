/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pengyue.weixin.wxhelper.json;

/**
 *
 * @author Administrator
 */
public class BaseResponse {
    private int Ret;
    private String ErrMsg="";

    public BaseResponse() {
        this.Ret = 0;
    }

    public int getRet() {
        return Ret;
    }

    public void setRet(int Ret) {
        this.Ret = Ret;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String ErrMsg) {
        this.ErrMsg = ErrMsg;
    }

    @Override
    public String toString() {
        return "BaseResponse{" + "Ret=" + Ret + ", ErrMsg=" + ErrMsg + '}';
    }
    
    
    
}
