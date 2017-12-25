/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pengyue.weixin.newpackage.test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.util.EntityUtils;
import com.pengyue.weixin.wxhelper.QrCodeFrame;

/**
 *
 * @author Administrator
 */
public class QrCode {

    static {
        System.setProperty("jsse.enableSNIExtension", "false");
    }

    public static String generateUUID() throws Exception {
        String jsLogin_url = "https://login.weixin.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN"
                            +"&_="+System.currentTimeMillis();

        String rs = httpClient(jsLogin_url);///loadResultFromHttp(jsLogin_url);
        if (rs.indexOf("200") != -1) {
            Pattern p = Pattern.compile("\"(.*?)\"");
            Matcher m = p.matcher(rs);
            while (m.find()) {
                rs = m.group();
                return rs.substring(1, rs.length() - 1);
            }
        }
        return null;

    }

    public static void loadQrCodeImage(String uuid) throws Exception {

        String Qr_url;

        Qr_url = "https://login.weixin.qq.com/qrcode/{uuid}?t=webwx";

        Qr_url = Qr_url.replace("{uuid}", uuid);

        BufferedImage image = ImageIO.read(QrCode.httpClientAsStream(Qr_url));

        if (image != null) {
            QrCodeFrame frame = new QrCodeFrame();
            frame.getQr_label().setSize(image.getWidth() + 10, image.getHeight() + 10);
            frame.getQr_label().setIcon(new ImageIcon(image));
            frame.setAlwaysOnTop(true);
            frame.pack();
            frame.setVisible(true);
        }
    }

    public static String loadUriToken(String uuid) throws Exception {
        String token_uri = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login?uuid={uuid}&tip=1"
                            +"&_="+System.currentTimeMillis();
        
        token_uri = token_uri.replace("{uuid}", uuid);
        String token = null;

        while (token == null) {
            // 创建URL对象

            String rs = loadResultFromHttp(token_uri);

            if (rs.indexOf("200") == -1) {
                Thread.sleep(5000);
            } else {
                Pattern p = Pattern.compile("\"(.*?)\"");
                Matcher m = p.matcher(rs);
                while (m.find()) {
                    rs = m.group();
                    token = rs.substring(1, rs.length() - 1);
                    System.out.println(token);
                    break;
                }
            }
        }
        return token;

    }

    public static String loadResultFromHttp(String url) throws Exception {
      
        StringBuilder sb = new StringBuilder();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        try  {
        	CloseableHttpResponse response1 = httpclient.execute(httpget);
                HttpEntity entity = response1.getEntity();
                InputStreamReader insr=new InputStreamReader( entity.getContent() );
                int respInt = insr.read();
             
                while (respInt != -1) {
                    sb.append((char) respInt);
                    respInt = insr.read();
                }
                EntityUtils.consume(entity);

               
            }catch (Exception e) {
				// TODO: handle exception
            	e.printStackTrace();
			}
        
         return sb.toString();
    }

    public static String httpClient(String url) throws Exception {

       return Request.Get(url).connectTimeout(10000).socketTimeout(10000).execute().returnContent().asString();
    }

    public static InputStream httpClientAsStream(String url) throws Exception {

        return Request.Get(url)
                .connectTimeout(10000)
                .socketTimeout(10000)
                .execute().returnContent().asStream();
    }

    public static void printCookie(String url) throws IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpclient.execute(httpget);
        
        
         HttpEntity entity = httpResponse.getEntity();  
        
        // 响应状态  
        System.out.println("status:" + httpResponse.getStatusLine());  
        System.out.println("headers:");  
        HeaderElementIterator it = new BasicHeaderElementIterator( httpResponse.headerIterator("Set-Cookie"));
       // EntityUtils.consume(entity);
        while (it.hasNext()) {
            HeaderElement elem = it.nextElement();
            System.out.println(elem.getName() + " = " + elem.getValue());
            NameValuePair[] params = elem.getParameters();
            for (int i = 0; i < params.length; i++) {
                System.out.println(" " + params[i]);
            }
        }
       
        // 判断响应实体是否为空  
        if (entity != null) {  
            String responseString = EntityUtils.toString(entity);  
            System.out.println("response length:" + responseString.length());  
        }  
       
    }
    
    public static void main(String... arg) throws Exception {
        //System.out.println(httpClient("https://login.weixin.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN"));

        String uuid = generateUUID();
        if (uuid != null) {
            loadQrCodeImage(uuid);
            String token=loadUriToken(uuid);
            if(token!=null){
                token=token.replace("wx.qq.com", "wx2.qq.com");
                printCookie(token);
            }
        }
        
        
    }
}
