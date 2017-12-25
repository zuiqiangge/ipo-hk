/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pengyue.weixin.wxhelper.util;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.pengyue.weixin.wxhelper.json.HeartBeatBean;

/**
 *
 * @author Administrator
 */
public class DeviceUtil {
    public static String generateDeviceId(){
        StringBuilder sb=new StringBuilder();
        sb.append("e");
        int count=15;//15个随机数字
        for(int i=0;i<count;i++){
           int num=(int)(Math.random()*9);
           sb.append(num);
        }
        
        return sb.toString();
        
    }
     
    public static void main(String[] args){
        String input="window.synccheck={retcode:\"1100\",selector:\"0\"}";
        Pattern p = Pattern.compile("\\{(.*?)\\}");
        Matcher m = p.matcher(input);
        String rs=null;
        while (m.find()) {
           rs= m.group();
            break;
        }
        if(rs!=null){
            HeartBeatBean b=new Gson().fromJson(rs, HeartBeatBean.class);
            System.out.println(b.getRetcode()+"-------"+b.getSelector());
        }
    }
}
