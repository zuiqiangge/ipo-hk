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
public class KeyVal {
     @SerializedName("Key")
     private int key;
     
     @SerializedName("Val")
     private long val;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public long getVal() {
        return val;
    }

    public void setVal(long val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "KeyVal{" + "key=" + key + ", val=" + val + '}';
    }
     
     
     
     
}
