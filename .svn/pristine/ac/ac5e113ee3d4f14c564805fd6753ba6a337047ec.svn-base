package com.pengyue.bbsTools.bbscore;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengyue.bbsTools.bbscore.util.BeanUtils;
import com.pengyue.bbsTools.bbscore.util.NetUtils;
import com.pengyue.bbsTools.bbscore.util.TestNetUtils;
import com.pengyue.bbsTools.bbscore.util.WrapperUtil;
import com.pengyue.bbsTools.conf.Constants;

//登录
public class TestLoginer extends Constants {
	
	public static final Log LOG = LogFactory.getLog(TestLoginer.class);
	
	private Map<String,String> getLoginFormData(){
		Document doc =  TestNetUtils.getDocumentByJsoup("http://cas.shgb.gov.cn/cas/include/v2/login.jsp#");
	 
		
		Elements form = doc.select( "form[id=loginForm]" );
		
 
		
		Map<String,String> result = new HashMap<String, String>();
		for (Element element : form.select("input")) {
			if(LOG.isDebugEnabled()){
				LOG.debug(" login form input element: "+element);
			}
			String name=element.attr("name");
			if(name==null || name.trim().length()==0){
				LOG.debug(" login form input element name is null !!");
				continue;
			}
			result.put(name, element.val());
		}
		if(LOG.isDebugEnabled()){
			LOG.debug(" getLoginFormData return  result: "+result);
		}
		return result;
	}
	
	private Map<String,String> loginCookies;
	
	private String username;
	private String pwd;
	
	public TestLoginer(String username, String pwd) {
		super();
		this.username = username;
		this.pwd = pwd;
	}

	public void login(){
		// Map<String,String> data = getLoginFormData();
		Map<String,String> data=new HashMap<String, String>();
	 
		
		 data.put("username", username);
		 data.put("password", pwd);
		 data.put("domain", "shgb");
		 data.put("service", "http%3A%2F%2Fedu.shgb.gov.cn%2Fstudent%2Fm%2Findex.html%3Fv%3D2");
		 data.put("password", pwd);
		 data.put("passwd", "");
		data.put("type", "trace");
		data.put("trace_id", "trace_id=0574bbc9-f7ff-4922-9c2a-5febfb73065b");
		data.put("courseid", "2c9e84eb552b39d3015605fe6c2117a1");
		data.put("courseunitid", "3428a92f550aef90015605fe77a12592");
		data.put("userid", "5cffcbc6-88b9-4abe-a2df-1889803ff951");
		data.put("categoryid", "");
		data.put("interval", "1000");
		 //type=start&trace_id=&courseid=10010770149&courseunitid=1500002663&userid=5cffcbc6-88b9-4abe-a2df-1889803ff951&categoryid=&interval=300000
		 
		 Map<String, String> cookies=new HashMap<String, String>(); 
			cookies.put("Hm_lvt_3a22bbb184a78750ab8f9cdcbb78ccda", "1484965213,1485075760");
			cookies.put("pwd_limit", "c4ca4238a0b923820dcc509a6f75849b");
			cookies.put("CNZZDATA110439", "cnzz_eid%3D766756549-1484964682-http%253A%252F%252Fcas.shgb.gov.cn%252F%26ntime%3D1485088361");
			cookies.put("Hm_lpvt_3a22bbb184a78750ab8f9cdcbb78ccda", "1485084779");
			cookies.put("_gscu_146744136", "84965213q2rqyg44");
			cookies.put("_gscs_146744136", "t85088326ig9iq420|pv:3");
			cookies.put("_gscbrs_146744136", "1");
			cookies.put("kwt", "5cffcbc6-88b9-4abe-a2df-1889803ff951");
		String loginUrl = "http://cas.shgb.gov.cn/cas/login";
		String posturl = "http://edu.shgb.gov.cn/student/player/trace.html";
		//Response resp1 = TestNetUtils.postByJsoup( posturl,  WrapperUtil.map2Json(data) );
		Document doc = 	TestNetUtils.postByJsoupUsingCookies1( posturl,  WrapperUtil.map2Json(data) , WrapperUtil.map2Json(data),"utf-8");
		 System.out.println(doc);
		//loginCookies = (resp.cookies());
	//	 
		
	 
		
//		if(resp==null){
//			LOG.error(" 进行登录时 resp null ！ ");
//		}
//		Document returnDoc = null;
//		try {
//			returnDoc = (resp).parse();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String msg = retunMsg(returnDoc);
//		if(msg==null){
//		 return;
//		}
//		System.out.println(msg);
//		if(msg!=null ){
// 
//		}
	}
	
	private String retunMsg(Document doc){
		Elements msg = doc.select("script");
		
		String[] tStrings =msg.toString().split("\"");
		System.out.println(tStrings[1]);
		if(tStrings==null ||tStrings.length<2 ){
			return null;
		}
		return tStrings[1];
	}
	
	public boolean isLoginSuccess(){
		return loginCookies !=null && !loginCookies.isEmpty();
	}
	

	public boolean isFetchSuccess(){
		return isLoginSuccess() && userInfo !=null && !BeanUtils.isEmptyPojo(userInfo);
	}
	
	private UserInfo userInfo;
	
	public void fetchInfo(){
		if(loginCookies==null){
			LOG.debug(" login cookies is null ,try login !! ");
			login();
		}
		
		LOG.debug(" 准备开始  获取 用户信息 ! ");
		if(loginCookies==null){
			LOG.error(" 没登录成功 ！无法进行用户信息获取 ");
			return;
		}
		
		
		int userProfileUrlCount =  Integer.getInteger("num.loginer.user_profile_url_count");
		
		if(LOG.isDebugEnabled()){
			LOG.debug(" userProfileUrlCount : "+userProfileUrlCount);
		}
		UserInfo bean  = new UserInfo();
		do{
			
			System.out.println(userProfileUrlCount);
			String url = System.getProperty("url.user_profile_url_"+userProfileUrlCount,"http://www.uwant.com/memcp.php?action=profile&typeid="+userProfileUrlCount);
			
			if(LOG.isDebugEnabled()){
				LOG.debug(" url config name : "+"url.user_profile_url_"+userProfileUrlCount);
				LOG.debug(" url : "+url);
			}
			
			Document doc = (TestNetUtils.getDocumentByJsoupUsingCookie(url, WrapperUtil.map2Json(loginCookies)));
			if(LOG.isDebugEnabled()){
				LOG.debug(" doc  : "+doc);
			}
			if(doc==null){
				LOG.debug(" 获取用户信息时  doc 访问失败 返回null。 忽视，进行下一个! ");
				continue;
			}
			
			String msg = retunMsg(doc);
			if(msg!=null && msg.startsWith(System.getProperty("msg.start.no.access")) ){
				LOG.warn(" 帐号被禁了!!!! 提示("+System.getProperty("msg.start.no.access")+") ");
				break;
			}
			
			Elements form = doc.select(System.getProperty("select.loginer.user_profile_form","form[name=reg]"));
			
			if(LOG.isDebugEnabled()){
				LOG.debug(" user profile form select :"+System.getProperty("select.loginer.user_profile_form","form[name=reg]"));
				LOG.debug(" user profile form :"+form);
			}
			for (Element element : form.select(System.getProperty("select.loginer.user_profile_form_ele_tagnames","input,select"))) {
				String value = "";
				String name = element.attr("name");
				boolean isAppendValue = false;
				if(element.tagName().equalsIgnoreCase("select")){
					value = element.select("option[selected]").text();
				}else if(element.attr("type").equalsIgnoreCase("radio") && element.attr("checked").equals("checked") ){
					value = element.val();
				}else if(element.attr("type").equalsIgnoreCase("checkbox") && element.attr("checked").equals("checked") ){
					value = element.val();
					isAppendValue = true;
				}else if(element.attr("type").equalsIgnoreCase("reset") || element.attr("checked").equals("submit")){
					value = "";
				}else {
					value = element.val();
				}
				if(value==null || value.length()==0){
					continue;
				}
				if(LOG.isDebugEnabled()){
					LOG.debug(" bean : "+bean);
					LOG.debug(" will set prop { name: "+name+" , value:"+value);
				}
				BeanUtils.setProp(bean, value, name, isAppendValue);
			}
			String displayInfoSelect = System.getProperty("select.loginer.user_profile_display_eles");
			if(LOG.isDebugEnabled()){
				LOG.debug(" 显示资料元素的选择器 :"+displayInfoSelect);
			}
			if(displayInfoSelect==null || displayInfoSelect.trim().length()==0){
				LOG.debug(" 显示资料元素的选择器为空，忽视这个 !");
				continue;
			}
			
			String displayInfoSplit = System.getProperty("regx.loginer.user_profile_display_eles","[:：]");
			if(LOG.isDebugEnabled()){
				LOG.debug(" displayInfoSplit:"+displayInfoSplit);
			}
			for (Element element : doc.select(displayInfoSelect)) {
				if(LOG.isDebugEnabled()){
					LOG.debug(" element:"+element);
				}
				String text = element.text();
				if(LOG.isDebugEnabled()){
					LOG.debug(" element text:"+text);
				}
				String name = text.split(displayInfoSplit)[0];
				if(LOG.isDebugEnabled()){
					LOG.debug(" parse name:"+name);
				}
				String value = text.substring(name.length()+1).trim();
				if(LOG.isDebugEnabled()){
					LOG.debug(" parse value:"+value);
				}
				BeanUtils.setProp(bean,value,name,false);
			}
		}while(--userProfileUrlCount > -1);
		
		bean.setUsername(username);
		bean.setPassword(pwd);
		this.userInfo = bean;
	}
	public Map<String, String> getLoginCookies() {
		return loginCookies;
	}

	public void setLoginCookies(Map<String, String> loginCookies) {
		this.loginCookies = loginCookies;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}	
	
 
}
