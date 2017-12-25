/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pengyue.weixin.wxhelper;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.httpclient.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.pengyue.weixin.wxhelper.json.BaseRequest;
import com.pengyue.weixin.wxhelper.json.HeartBeatBean;
import com.pengyue.weixin.wxhelper.json.KeyVal;
import com.pengyue.weixin.wxhelper.json.SyncKey;
import com.pengyue.weixin.wxhelper.json.UserInfo;
import com.pengyue.weixin.wxhelper.json.WebWxInitBean;
import com.pengyue.weixin.wxhelper.json.WebWxInitJson;
import com.pengyue.weixin.wxhelper.json.WebWxStatusNotifyBean;
import com.pengyue.weixin.wxhelper.json.WebWxStatusNotifyJson;
import com.pengyue.weixin.wxhelper.json.WebWxSyncPostBean;
import com.pengyue.weixin.wxhelper.json.WebWxSyncPostJson;
import com.pengyue.weixin.wxhelper.service.LoginCallback;
import com.pengyue.weixin.wxhelper.util.DeviceUtil;

/**
 * 
 * @author Administrator
 * @desc 用于封装Web版微信的相关操作，包括内容有：登陆验证、获取好友列表、发送消息、获取消息等。
 */
public class WxBox {

	private String uuid; // 用于标识当前登陆的唯一标记
	private String Skey;// 登陆后与服务器进行交互的密钥
	private String pass_ticket;// 登录后与服务器进行交互必备参数
	private String wxuin;// 用于cookie中，与服务器端交互的数据信息
	private String wxsid;// 用于cookie中，与服务器端交互的数据信息

	private final String deviceid = DeviceUtil.generateDeviceId();// "e157113007325620";//设置ID，根据官方的数据，应该是由e+随机数字生成的字符串
	private String synkey;// 用于同步保持心跳的交互密钥

	private String token_login_url;// 用于得到经过第三步骤后，返回的真正的登陆地址。
	private final String new_refer = "wx2.qq.com"; // 目前的协议中，使用是最近的该域名
	private final String old_refer = "wx.qq.com";// 在相应交互中，也有该域名的出现，但最终都会被重定向到新域名那里去

	private BasicCookieStore cookieStore; // 用于保存PC端与服务器间交互时的cookie信息

	private SyncKey syncKey;// 主要用于浏览器端与服务器端保持心跳的交互key

	private UserInfo info;//

	// 最初的登陆页面，主要用于通过该请求链接，得到一个唯一的uuid，并由该UUID在全局使用。
	// 设为Step 1.
	// 请求方法 GET，返回值uuid
	private String jsLogin_url = "https://login.wx2.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN"
			+ "&_={time}";
	// 根据Step1所得到的uuid，去访问并获取对应的二维码图片，用于手机扫描。
	// --不得不吐槽竟然让用户用手机去扫描，我要是用手机上，还需要用网页版的干嘛？方便打字？
	// Step 2
	// 请求方法 GET
	private String qr_code_url = "https://login.weixin.qq.com/qrcode/{uuid}";

	// Step 3
	// 由于在step2中已经提供了二维码给手机端进行扫描，那就意味着必须有另一个动作去轮询服务器，以确定手机是否已经扫描到，并同意用户在网页端登陆微信
	// 请求方法 GET
	// 如果返回的响应中包括有window.redirect_uri=xxxxxx(URL地址)。
	// 即表示手机端已经同意授权,而此时响应中提供的用于跳转的URL，则是用于让用户去获取相应cookie的关键步骤
	// 但是，很遗憾的告诉你，该URL中的域名是以wx.qq.com开头的，由于目前微信协议中的新的refer已经改为wx2.qq.com了，
	// 所以，实际上你应该访问的登陆地址由 https://wx.qq.com/xxxxxxx 改为 https://wx2.qq.com/xxxxxxx
	// 才对。
	// 访问该地址后，从返回的cookie中至少会得到 wxuin和wxsid 这二个信息。
	// 这二个信息的重要性，主要用于之后与服务器端进行交互认证之用途。
	// 返回地址如下：https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=d85eb3c260c14b5ea2959a1e783b7681&lang=zh_CN&scan=1397376800
	// private String checkAuth_url =
	// "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login?uuid={uuid}&tip=1&_={time}";
	private String checkAuth_url = "https://login.wx2.qq.com/cgi-bin/mmwebwx-bin/login?r=-894255418&loginicon=true&uuid={uuid}&tip=0&_={time}";

	// Step 4
	// 请求方法 POST，content-type类型为application/json; charset=utf-8
	// 需要提供之前保存的cookie信息以及如下的json信息内容：
	/*
	 * {"BaseRequest":{"Uin":"2545437902","Sid":"QfLp+Z+FePzvOFoG","Skey":"","DeviceID"
	 * :"e1615250492"}} 该方法会返回手机微信主页上的信息，其中包括小部分的联系人的方式等等。
	 * 可以通过WebWxInitBean来装载其中的数据
	 */
	 private String web_wx_init_url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r={time}&pass_ticket={pass_ticket}";
	//private String web_wx_init_url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxinit";
	// Step 5
	// 通过浏览器端的监控，发现在第四步之后，并不是直接调用获取全部的用户列表，而是紧接着调用 如下URL，从URL的命名来看，应该是在第四步成功之后，
	// 再次向服务器端提供的一次验证，而且这次验证在返回的json中的syncKey将会做为此后心跳机制中的交互码。在第4步中也会返回此码，二次得到的syncKey并不一样
	// 估计是为了迷惑别人，故意如此做吧。但是在该此请求中，却必须在post体中带上第4步中得到syncKey。
	// 另外从该资源的返回内容来看，该请求应该也包括了获取用户消息的请求。
	// 形如"https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid={sid}&r={time}&skey={skey}";
	private String web_wx_sync = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsync";

	// Step 6
	// 根据观察，发现这个是一个自己给自己发送信息的一个状态包，估计是用来保持网页端与手机端之间状态的一个方式吧。
	// 方法POST
	// "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxstatusnotify?r=1397443950116&skey=%40crypt_cfbfba84_e5913dbec2b764d086b7d1d1aab946ca";
	private String web_wx_status_notify = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxstatusnotify";

	// Step 7
	/*
	 * 该URL用于定时性的向服务器发送心跳包，在返回值中的，有相应的状态码，大致如下window.synccheck={retcode:”0″,selector
	 * :”0″} 其中，当selector不等于0即，意味着有消息需要客户端发起请求。 请求的方法类型为 GET
	 */
	private String sync_check_url = "https://webpush2.weixin.qq.com/cgi-bin/mmwebwx-bin/synccheck?"
			+ "skey={skey}"
			+ "&callback=jQuery183008612365838727565_1397377003545"
			+ "&r={r}&sid={sid}&uin={uin}&deviceid={deviceid}"
			+ "&synckey={synckey}&_={time}";

	private QrCodeFrame frame;// 用来显示二维码窗口，如果用户直接关闭，程序则直接退出。

	private int login_try_times = 50;// 用来检测用户扫描二维码时的，尝试次数，如果用户在特定的次数里，服务器端没有能返回授权通过的信息，则认为登录失败。

	private WebWxInitBean webWxInitBean;

	private LoginCallback loginCallback = new DefaultLoginCallback();

	public WxBox() {
		this.cookieStore = new BasicCookieStore();
		this.syncKey = new SyncKey();
	}

	public void login() {
		try {
			if ((this.uuid = generateUUID()) != null) {
				loadQrCodeImage();
				loginCallback.handleResult(frame, loadUriToken());

			}
		} catch (Exception ex) {
			Logger.getLogger(WxBox.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(-1);
		}
	}

	public void configWxData() {

		cookieStore = new BasicCookieStore();// 从真实的登陆链接处，装载相应的cookie信息

		CloseableHttpClient client = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();

		HttpGet httpGet = new HttpGet(this.token_login_url);
		System.out.println("request line:" + httpGet.getRequestLine());
		try {
			// 执行get请求
			HttpResponse httpResponse = client.execute(httpGet);
			// 获取服务器返回Http的Content-Type的值
			String tempStr = httpResponse.getHeaders("Content-Type")[0]
					.getValue().toString();

			// 获取服务器返回页面的值
			HttpEntity entity = httpResponse.getEntity();
			String resContent = EntityUtils.toString(entity);
			// Stringstrmessage=null;
			System.out.println("登录成功返回=====" + resContent);
			// 解析skey,pass_ticket
			String regex = "<pass_ticket>(.*)</pass_ticket>";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(resContent);
			while (matcher.find()) {
				this.pass_ticket = matcher.group(1);
			}
			System.out.println("pass_ticket:" + pass_ticket);
			String regex1 = "<skey>(.*)</skey>";
			Pattern pattern1 = Pattern.compile(regex1);
			Matcher matcher1 = pattern1.matcher(resContent);
			while (matcher1.find()) {
				this.Skey = matcher1.group(1);
			}
			System.out.println("Skey:" + Skey);

			System.out.println("cookie store:" +cookieStore.getCookies().size()+"      "+ cookieStore.getCookies());
			for (Cookie c : cookieStore.getCookies()) {
				if ("wxsid".equals(c.getName())) {
					this.wxsid = c.getValue();
					System.out.println("wxsid======" + wxsid);
				} else if ("wxuin".equals(c.getName())) {
					this.wxuin = c.getValue();
					System.out.println("wxuin======" + wxuin);
				}
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

	public void configWebWxInitBean() throws UnsupportedEncodingException,
			IOException {
		HttpPost post = new HttpPost(this.web_wx_init_url.replace("{time}",
				String.valueOf(~(int)System.currentTimeMillis())).replace("{pass_ticket}", pass_ticket));
		BaseRequest request = new BaseRequest(this.wxuin, this.wxsid, Skey,
				this.deviceid);
		WebWxInitJson json = new WebWxInitJson(request);
		Gson gson = new Gson();
		System.out.println("requestp===="+gson.toJson(json));
//		StringEntity se = new StringEntity(gson.toJson(json));
//		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
//				"application/json; charset=utf-8"));
		//post.setEntity(se); // post方法中，加入json数据
		post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		post.setHeader("Accept","application/json, text/plain, */*");  
		post.setHeader("Accept-Language","zh-CN,zh;q=0.8");  
		post.setHeader("Accept-Encoding","gzip, deflate, br"); 
		post.setHeader("Host","wx.qq.com");  
		post.setHeader("Origin", "https://wx.qq.com");  
		post.setHeader("Referer", "https://wx.qq.com/");  
		post.setHeader("Connection", "keep-alive");  
		post.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");  
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("r", String.valueOf(~(int)System.currentTimeMillis())));
		params.add(new BasicNameValuePair("pass_ticket", pass_ticket));
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));// 设置post参数
		if (cookieStore == null) {
			System.err.print("Cookies 信息丢失!");
			System.exit(-1);
		}
		System.out.println(cookieStore.getCookies().size());

		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();// 将cookie信息添加到请求中

		HttpResponse response = httpclient.execute(post);
	//	String resContent = EntityUtils.toString(response.getEntity());
		StringBuilder rs = new StringBuilder();
		System.out.println("响应==="+response.getStatusLine().getStatusCode());
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			HttpEntity entity = response.getEntity();
			InputStreamReader insr = new InputStreamReader(entity.getContent());
			int respInt = insr.read();

			while (respInt != -1) {
				rs.append((char) respInt);
				respInt = insr.read();
			}
		}
		System.out.println("init信息=========="+rs.toString());
//		this.webWxInitBean = gson.fromJson(rs.toString(), WebWxInitBean.class);
//		this.syncKey = webWxInitBean.getSyncKey();
//		this.Skey = webWxInitBean.getSkey();
//		this.info = webWxInitBean.getUser();
		//print(info);
	}

	public WebWxSyncPostBean loadMsgAndUpdateSyncKey()
			throws UnsupportedEncodingException, IOException {
		WebWxSyncPostJson post_json = new WebWxSyncPostJson(this.wxsid,
				this.wxuin);
		post_json.setRr(System.currentTimeMillis());
		post_json.setSynKey(this.syncKey);
		Gson gson = new Gson();
		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();// 将cookie信息添加到请求中
		// private String web_wx_sync =
		// "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxsync?sid={sid}&r={time}&skey={skey}";
		HttpPost post = new HttpPost(web_wx_sync);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("sid", this.wxsid));
		params.add(new BasicNameValuePair("r", String.valueOf(System
				.currentTimeMillis())));
		params.add(new BasicNameValuePair("skey", Skey));
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		StringEntity se = new StringEntity(gson.toJson(post_json));

		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json; charset=utf-8"));

		post.setEntity(se); // post方法中，加入json数据

		HttpResponse response = httpclient.execute(post);
		StringBuilder rs = new StringBuilder();
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			HttpEntity entity = response.getEntity();
			InputStreamReader insr = new InputStreamReader(entity.getContent());
			int respInt = insr.read();
			while (respInt != -1) {
				rs.append((char) respInt);
				respInt = insr.read();
			}
		}

		WebWxSyncPostBean bean = gson.fromJson(rs.toString(),
				WebWxSyncPostBean.class);
		if (bean.getSyncKey().getCount() != 0) {
			this.syncKey = bean.getSyncKey();
		}
		return bean;
	}

	/**
	 * 同时同步手机及网页端的通讯状态
	 */
	public boolean wxStatusNotify() throws UnsupportedEncodingException,
			IOException {
		Gson gson = new Gson();

		WebWxStatusNotifyJson json = new WebWxStatusNotifyJson(this.wxuin,
				this.wxsid, this.Skey, this.deviceid);

		String msgid = String.valueOf(System.currentTimeMillis());
		String timestamp = msgid;

		json.setClientMsgId(msgid);
		json.setFromUserName(info.getUserName());
		json.setToUserName(info.getUserName());

		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();// 将cookie信息添加到请求中

		HttpPost post = new HttpPost(web_wx_status_notify);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("r", timestamp));
		params.add(new BasicNameValuePair("skey", Skey));
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));// 设置post参数

		StringEntity se = new StringEntity(gson.toJson(json));
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json; charset=utf-8"));
		post.setEntity(se); // post方法中，加入json数据

		HttpResponse response = httpclient.execute(post);
		StringBuilder rs = new StringBuilder();
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			HttpEntity entity = response.getEntity();
			InputStreamReader insr = new InputStreamReader(entity.getContent());
			int respInt = insr.read();
			while (respInt != -1) {
				rs.append((char) respInt);
				respInt = insr.read();
			}
		}
		print(rs.toString());

		WebWxStatusNotifyBean bean = gson.fromJson(rs.toString(),
				WebWxStatusNotifyBean.class);

		print(bean);
		return bean.getBr().getRet() == 0;

	}

	/**
	 * 用于定时向服务器发送心跳包，同时，根据心跳包中的返回信息，确认是否需要发请另外的请求，去获取消息。
	 */
	public HeartBeatBean syncCheck() throws UnsupportedEncodingException,
			IOException {

		String url = sync_check_url.replace("{skey}",
				URLEncoder.encode(this.Skey, "utf-8")).replace("{r}",
				String.valueOf(System.currentTimeMillis())).replace("{sid}",
				URLEncoder.encode(this.wxsid, "utf-8")).replace("{uin}",
				this.wxuin).replace("{deviceid}", this.deviceid).replace(
				"{synckey}",
				URLEncoder.encode(this.contactSyncKey(syncKey), "utf-8"))
				.replace("{time}", String.valueOf(System.currentTimeMillis()));

		CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCookieStore(cookieStore).build();// 将cookie信息添加到请求中

		HttpGet get = new HttpGet(url);

		HttpResponse response = httpclient.execute(get);
		StringBuilder rs = new StringBuilder();
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			HttpEntity entity = response.getEntity();
			InputStreamReader insr = new InputStreamReader(entity.getContent());
			int respInt = insr.read();
			while (respInt != -1) {
				rs.append((char) respInt);
				respInt = insr.read();
			}
		}

		return HeartBeatBean.parseString(rs.toString());
	}

	private String loadUriToken() throws Exception {

		this.token_login_url = null;
		int index = 0;
		while (token_login_url == null && index < this.login_try_times) {
			// 创建URL对象

			String rs = loadResultFromHttp(checkAuth_url
					.replace("{uuid}", uuid).replace("{time}",
							String.valueOf(System.currentTimeMillis())));

			if (rs.indexOf("200") == -1) {
				Thread.sleep(5000);
			} else {
				Pattern p = Pattern.compile("\"(.*?)\"");
				Matcher m = p.matcher(rs);
				while (m.find()) {
					rs = m.group();
					token_login_url = rs.substring(1, rs.length() - 1);
					print("获取到的原始登陆地址：" + token_login_url);
					break;
				}
			}
			index++;
		}
//	 token_login_url = token_login_url.replace(this.old_refer,
//		 this.new_refer);

		//token_login_url = token_login_url + "&" + "fun=new&version=v2";
		print("下转换得到的正确的登陆地址：" + token_login_url);
		return token_login_url;

	}

	private String loadResultFromHttp(String url) throws Exception {
		StringBuilder sb = new StringBuilder();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
 
		try {
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			HttpEntity entity = response1.getEntity();
			InputStreamReader insr = new InputStreamReader(entity.getContent());
			int respInt = insr.read();

			while (respInt != -1) {
				sb.append((char) respInt);
				respInt = insr.read();
			}
			EntityUtils.consume(entity);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return sb.toString();
	}

	private void print(Object obj) {
		System.out.println(obj);
	}

	private void loadQrCodeImage() throws Exception {

		BufferedImage image = ImageIO.read(Request.Get(
				this.qr_code_url.replace("{uuid}", uuid)).connectTimeout(10000)
				.socketTimeout(10000).execute().returnContent().asStream());

		if (image != null) {
			frame = new QrCodeFrame();
			frame.getQr_label().setSize(image.getWidth() + 10,
					image.getHeight() + 10);
			frame.getQr_label().setIcon(new ImageIcon(image));
			frame.setAlwaysOnTop(true);
			frame.pack();
			frame.setVisible(true);
		}
	}

	private String generateUUID() throws Exception {

		String rs = Request.Get(
				jsLogin_url.replace("{time}", String.valueOf(System
						.currentTimeMillis()))).connectTimeout(10000)
				.socketTimeout(10000).execute().returnContent().asString();
		System.out.println("获取UUID===" + rs);
		if (rs.indexOf("200") != -1) {
			Pattern p = Pattern.compile("\"(.*?)\"");
			Matcher m = p.matcher(rs);
			while (m.find()) {
				rs = m.group();
				System.out.println("UUID处理后==="
						+ rs.substring(1, rs.length() - 1));
				return rs.substring(1, rs.length() - 1);
			}
		}

		return null;

	}

	/**
	 * @desc 由于在url中的SyncKey都是通过key_value|key_value...等形式进行连接的，所以，对参数进行编码
	 * @param sk
	 * @return 编码后的字符串
	 */
	private String contactSyncKey(SyncKey sk) {
		StringBuilder sb = new StringBuilder();
		List<KeyVal> list = sk.getKeyList();
		int size = list.size();
		if (size == 0) {
			return "";
		}

		for (int index = 0; index < size; index++) {
			KeyVal kv = list.get(index);
			sb.append(kv.getKey()).append("_").append(kv.getVal());
			if (index != size - 1) {
				sb.append("|");
			}
		}
		return sb.toString();
	}

	public LoginCallback getLoginCallback() {
		return loginCallback;
	}

	public void setLoginCallback(LoginCallback loginCallback) {
		this.loginCallback = loginCallback;
	}

	class DefaultLoginCallback implements LoginCallback {

		@Override
		public void handleResult(Object target, String rs) {
			System.out.println("DefaultLoginCallback=========");
			if (rs != null) {

				((JFrame) target).setVisible(false);
			} else {
				JOptionPane.showMessageDialog(frame, "二维码信息过期，请重新尝试！", "登陆失败",
						JOptionPane.ERROR);
				System.exit(-1);
			}
		}

	}

}
