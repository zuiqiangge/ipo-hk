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
import com.pengyue.bbsTools.bbscore.util.WrapperUtil;
import com.pengyue.bbsTools.conf.Constants;

//登录
public class Loginer extends Constants {
	
	public static final Log LOG = LogFactory.getLog(Loginer.class);
	
	private Map<String,String> getLoginFormData(){
		Document doc =  NetUtils.getDocumentByJsoup(System.getProperty("url.goto_user_home", "http://www.uwant.com/memcp.php?action=profile&typeid=1"));
		if(LOG.isDebugEnabled()){
			LOG.debug(" url : "+System.getProperty("url.goto_user_home", "http://www.uwant.com/memcp.php?action=profile&typeid=1"));
			LOG.debug(" doc : "+doc);
		}
		if(doc==null){
			LOG.error(" 进行得到登录页面时 doc null ！ ");
		}
		
		Elements form = doc.select(System.getProperty("select.loginer.loginform","form[name=loginform]"));
		
		if(LOG.isDebugEnabled()){
			LOG.debug(" login form select : "+System.getProperty("select.loginer.loginform","form[name=loginform]"));
			LOG.debug(" login form element: "+doc);
		}
		
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
	
	public Loginer(String username, String pwd) {
		super();
		this.username = username;
		this.pwd = pwd;
	}

	public void login(){
		Map<String,String> data = getLoginFormData();
		
		if(data==null){
			LOG.error(" 請求登录頁面时 data null ！ ");
		}
		
		if(username==null || pwd==null || username.trim().length()==0 || pwd.trim().length()==0 ){
			LOG.error(" username  或者  password null  ！ ");
		}
		
		if(LOG.isDebugEnabled()){
			LOG.debug("要登陸 username: "+username);
			LOG.debug("要登陸 pwd : "+pwd);
		}
		
		data.put(System.getProperty("select.loginer.loginform.names.username", "username"), username);
		data.put(System.getProperty("select.loginer.loginform.names.password", "password"), pwd);
		
		String loginUrl = System.getProperty("url.do_login", "http://www.uwant.com/logging.php?action=login&loginsubmit=true");
		
		System.out.println("post json ::: "+WrapperUtil.map2Json(data));
		
		Response resp = NetUtils.postByJsoup(loginUrl, WrapperUtil.map2Json(data) );
		
		if(LOG.isDebugEnabled()){
			LOG.debug(" loginUrl: "+loginUrl);
			LOG.debug(" login Response : "+resp);
		}
		
		if(resp==null){
			LOG.error(" 进行登录时 resp null ！ ");
		}
		Document returnDoc = null;
		try {
			returnDoc = (resp).parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String msg = retunMsg(returnDoc);
		if(msg==null){
			Elements logininfoEle = returnDoc.select(System.getProperty("select.loginer.return.logininfo"));
			String logininfo = logininfoEle==null || logininfoEle.isEmpty() ?"" : logininfoEle.text().trim();
			LOG.warn(" 登录失败！！ 提示信息("+logininfo+")");
		}
		if(msg!=null &&  msg.startsWith(System.getProperty("msg.start.logined","歡迎您回來"))){
			if(LOG.isDebugEnabled()){
				LOG.debug(" login return cookies : "+ (resp.cookies()));
			}
			loginCookies = (resp.cookies());
			
			LOG.info(" 登录成功！！ ");
		}
	}
	
	private String retunMsg(Document doc){
		Elements msg = doc.select(System.getProperty("select.do.return.msg","#mainbody .box.message p"));
		
		if(LOG.isDebugEnabled()){
			LOG.debug(" msg elemtns selector : "+System.getProperty("select.do.return.msg","#mainbody .box.message p"));
			LOG.debug(" msg elemtns: "+msg);
			//對不起，您的 IP 地址不在被允許的範圍內，或您的賬號被禁用，無法訪問本論壇。
		}
		if(msg==null || msg.isEmpty() ){
			return null;
		}
		return msg.text().trim();
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
			
			Document doc = (NetUtils.getDocumentByJsoupUsingCookie(url, WrapperUtil.map2Json(loginCookies)));
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
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Loginer  loginer = new Loginer("zwwyhy","wflinhong");
		loginer.fetchInfo();
	//	System.out.println(org.apache.commons.beanutils.BeanUtils.describe(loginer.getUserInfo()));
		//System.out.println(loginer.isFetchSuccess());
		
		
	}
	
}
