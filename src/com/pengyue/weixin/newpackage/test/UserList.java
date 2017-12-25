/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pengyue.weixin.newpackage.test;

import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

/**
 *
 * @author Administrator
 */
public class UserList {
     static {
        System.setProperty("jsse.enableSNIExtension", "false");
    }

     public static void main(String... arg) throws Exception {
         String url="https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=1397140328169&skey=";
         
        String json="{\"BaseRequest\":{\"Uin\":\"1365435340\",\"Sid\":\"guDyfuIBMwmqMKtA\",\"Skey\":\"\",\"DeviceID\":\"e157113007325610\"}}";
        HttpPost post=new HttpPost(url);
       // post.setHeader(HTTP.CONTENT_TYPE,"application/json; charset=utf-8" );
       // post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        StringEntity se = new StringEntity(json); 
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8"));
        post.setEntity(se); //post方法中，加入json数据
       
        BasicCookieStore cookieStore = new BasicCookieStore();
        
        cookieStore.addCookie(new BasicClientCookie("wxsid","guDyfuIBMwmqMKtA"));
        cookieStore.addCookie(new BasicClientCookie("wxuin","1365435340"));
        
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();//将cookie信息添加到请求中
        
        HttpResponse response=httpclient.execute(post);
        
        StringBuilder rs =new StringBuilder();
         if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){  
                HttpEntity entity = response.getEntity();  
                InputStreamReader insr=new InputStreamReader( entity.getContent() );
                int respInt = insr.read();
             
                while (respInt != -1) {
                    rs.append((char) respInt);
                    respInt = insr.read();
                }
            }  
        System.out.println(rs.toString());
//       Gson gson = new GsonBuilder().create();
//       JsonObjecobj = gson.f
     }
}
