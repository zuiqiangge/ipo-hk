package com.pengyue.bbsTools.bbscore;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengyue.bbsTools.bbscore.util.NetUtils;
import com.pengyue.bbsTools.bbscore.util.WrapperUtil;
import com.pengyue.bbsTools.conf.Constants;
public class Testgb extends Constants {
	 

	public static void main(String[] args) throws UnsupportedEncodingException {

	 
	      final long timeInterval = 20000;  
	        Runnable runnable = new Runnable() {  
	            public void run() {  
	                while (true) {  
	                    // ------- code for task to run  
	              
	                    // ------- ends here  
	                    try {  
	                       	TestLoginer loginer = new TestLoginer("syj621", "ded9184d312ae0b2fadb5b4ee3ad37ea");
		            		loginer.login();
	                        Thread.sleep(timeInterval);  
	                    } catch (InterruptedException e) {  
	                        e.printStackTrace();  
	                    }  
	                }  
	            }  
	        };  
	        Thread thread = new Thread(runnable);  
	        thread.start();  

	}
}
