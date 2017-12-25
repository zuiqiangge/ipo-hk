package com.pengyue.bbsTools.bbscore.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

public abstract class TestNetUtils {
	
	private static final Log LOG = LogFactory.getLog(TestNetUtils.class);
	
	public static Document getDocumentByJsoup(String url){
		return (getDocumentByJsoupUsingCookie(url, null));
	}
	
	public static Document getDocumentByJsoupUsingCookie(String url,String /*Map<String,String>*/ cookiesJSON){
		Response resp = null;
		try {
			Map<String, String> cookies = new HashMap<String, String>();
			if(cookiesJSON!=null){
				cookies.putAll(WrapperUtil.<String,String>json2Map(cookiesJSON));
			}
			return (resp = createConn(url).cookies(cookies).method(Method.GET).timeout(100000).execute()).parse();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.debug("链接网页失败！返回null",e);
			return null;
		} finally {
			setOrAddCookies(resp);
		}
	}

	public static Response postByJsoup(String url, String /*Map<String,String>*/ dataJSON) {
		
		Response resp = null;
		try {
			resp = createConn(url)
			.method(Method.POST).timeout(100000)
			.data(WrapperUtil.<String,String>json2Map(dataJSON))
			.execute();
		} catch (IOException e) {
			LOG.debug("提交网页失败！返回null",e);
			return null;
		}  finally {
			setOrAddCookies(resp);
		}
		return (resp);
	}
	
	public static Response getByJsoup(String url, String /*Map<String,String>*/ dataJSON) {
		
		Response resp = null;
		try {
			resp = createConn(url)
			.method(Method.GET).timeout(100000)
			//.data(WrapperUtil.<String,String>json2Map(dataJSON))
			.execute();
		} catch (IOException e) {
			//LOG.debug("提交网页失败！返回null",e);
			return null;
		}  finally {
			setOrAddCookies(resp);
		}
		return (resp);
	}

	public static Document postByJsoupUsingCookies(String url,
			/*Map<String, String>*/String formData,String/*Map<String,String>*/ cookies,String outputCharset) {
		
		Response resp = null;
		try {
			Connection conn = createConn(url)
			.cookies(WrapperUtil.<String,String>json2Map(cookies))
			.method(Method.POST).timeout(100000)
			.data(WrapperUtil.<String,String>json2Map(formData));
			if(outputCharset!=null && outputCharset.length()>0 ){
				conn.request().postDataCharset(outputCharset);
				conn.request().header("X-Output-Charset",outputCharset);
			}
			return ((resp = conn.execute()).parse());
		} catch (IOException e) {
			e.printStackTrace();
			LOG.debug("提交信息失败！返回null",e);
			return null;
		} finally {
			setOrAddCookies(resp);
		}
	}
	
	public static Document postByJsoupUsingCookies1(String url,
			/*Map<String, String>*/String formData,String/*Map<String,String>*/ cookies,String outputCharset) {
		
		Response resp = null;
		try {
			 
			Connection conn = createConn(url)
			.cookies(WrapperUtil.<String,String>json2Map(cookies))
			.method(Method.POST).timeout(100000)
			.ignoreContentType(true)
			.header("Host", "edu.shgb.gov.cn").header("Referer", "http://edu.shgb.gov.cn/student/learn/index.html?courseid=10010770149&courseunitid=1500002662&tcid=&treeid=3428a92f58bd188e015972c7d8201404&addexam=true")
			.data(WrapperUtil.<String,String>json2Map(formData));
			if(outputCharset!=null && outputCharset.length()>0 ){
				conn.request().postDataCharset(outputCharset);
				conn.request().header("X-Output-Charset",outputCharset);
			}
			return ((resp = conn.execute()).parse());
		} catch (IOException e) {
			e.printStackTrace();
			LOG.debug("提交信息失败！返回null",e);
			return null;
		} finally {
			setOrAddCookies(resp);
		}
	}
	private static ThreadLocal<String> lastUrl = new ThreadLocal<String>();
	private static ThreadLocal<Connection> lastConn = new ThreadLocal<Connection>();
	private static ThreadLocal<Map<String,String>> cookies = new ThreadLocal<Map<String,String>>();
	
	private static void setOrAddCookies(Map<String,String> cookies){
		Map<String, String> old = TestNetUtils.cookies.get();
		if(old==null){
			//cookies.put("curr_hostname", "www.uwants.com");
			cookies.put("Hm_lvt_3a22bbb184a78750ab8f9cdcbb78ccda", "1484965213,1485075760");
			cookies.put("pwd_limit", "c4ca4238a0b923820dcc509a6f75849b");
			cookies.put("CNZZDATA110439", "cnzz_eid%3D766756549-1484964682-http%253A%252F%252Fcas.shgb.gov.cn%252F%26ntime%3D1485088361");
			cookies.put("Hm_lpvt_3a22bbb184a78750ab8f9cdcbb78ccda", "1485084779");
			cookies.put("_gscu_146744136", "84965213q2rqyg44");
			cookies.put("_gscs_146744136", "t85088326ig9iq420|pv:3");
			cookies.put("_gscbrs_146744136", "1");
			cookies.put("kwt", "5cffcbc6-88b9-4abe-a2df-1889803ff951");
			TestNetUtils.cookies.set(cookies);
			return;
		}
		old.putAll(cookies);
	}
	
	private static void setOrAddCookies(Response resp){
		if(resp==null){
			return;
		}
		setOrAddCookies(resp.cookies());
	}
	
	private static Connection createConn(String url) {
		String lastVisitUrl = lastUrl.get();
		Connection connect = lastConn.get();
		if(connect==null){
			connect = Jsoup.connect(url);
		}else{
			connect.url(url);
		}
		connect.maxBodySize(0).ignoreHttpErrors(true);
		if(lastVisitUrl != null){
			connect.referrer(lastVisitUrl);
		}
		if(cookies.get()!=null){
			System.out.println(cookies.get()+"======="+cookies.get());
			connect.cookies(cookies.get());
		}
		lastUrl.set(url);
		System.getProperties().setProperty("proxySet", "true");
		// 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
		String ip = "127.0.0.1";
		System.getProperties().setProperty("http.proxyHost", ip);
		System.getProperties().setProperty("http.proxyPort", "9666");
		connect
		 //.proxy("127.0.0.1", 16823)
		.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2403.157 Safari/537.36 Java/1.6+ Jsoup/1.8.2");
		return (connect);
		//return JsoupExt.proxy(connect, "127.0.0.1", 8087);
	}
}
