/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pengyue.weixin.newpackage.test;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Administrator
 */
public class Header {

    static {
        System.setProperty("jsse.enableSNIExtension", "false");
    }


    public static void main(String... m) throws Exception {

      
     
        BasicCookieStore cookieStore = new BasicCookieStore();
        
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();


        HttpGet httpGet = new HttpGet("https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=a1fcd60e152441cd8fc38cd730818e7a&lang=zh_CN&scan=1397117537");
        System.out.println("request line:" + httpGet.getRequestLine());
        try {
            // 执行get请求  
            HttpResponse httpResponse = client.execute(httpGet);
            System.out.println("cookie store:" + cookieStore.getCookies());
            for (Cookie c:cookieStore.getCookies()){
                if("wxsid".equals(c.getName()))
                    System.out.println(c.getValue());
                else if  ("wxuin".equals(c.getName()))
                   System.out.println(c.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流并释放资源  
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
