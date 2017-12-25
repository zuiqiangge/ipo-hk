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

//帖子處理
public class PostHandler extends Constants {
	public static final Log LOG = LogFactory.getLog(PostHandler.class);

	// 回帖
	public boolean reply(String postUrl, Loginer loginer, String context) {
		try {
			String postId = getPostId(postUrl);
			if (postId == null) {
				LOG.error("postId不在url中");
				return false;
			}
			String replayUrl = String.format(System.getProperty("url.post.replay"),
					postId);
			LOG.debug(replayUrl);

			Map<String, String> formData = getReplyFormData(loginer
					.getLoginCookies(), replayUrl);

			formData.put(System.getProperty("select.post.replay.context.name"),
					context);

			String postReplayUrl = String.format(System
					.getProperty("url.post.do.replay"), postId);
			LOG.debug(postReplayUrl);

			Document doc =  NetUtils.postByJsoupUsingCookies(
					postReplayUrl, WrapperUtil.map2Json(formData), WrapperUtil
							.map2Json(loginer.getLoginCookies()), "big5") ;

			if (doc == null) {
				LOG.error(" 回帖请求出错了！ doc :" + doc);
				return false;
			}

			Elements msg = doc.select(System.getProperty("select.do.return.msg",
					"#mainbody .box.message p"));

			if (LOG.isDebugEnabled()) {
				LOG.debug(" msg elemtns selector : "
						+ System.getProperty("select.do.return.msg",
								"#mainbody .box.message p"));
				LOG.debug(" msg elemtns: " + msg);
			}

			if (msg.text().startsWith(
					System.getProperty("msg.start.replayed", "非常感謝，您的回覆已經發佈，現在將"))) {
				System.out.println(" 回帖成功！！ ");
				return true;
				// if(LOG.isDebugEnabled()){
				// String url =
				// "http://www.uwants.com/"+msg.get(1).select("a").attr("href");
				// LOG.debug(" view url : "+url);
				// LOG.debug(" view page  : "+NetUtils.getDocumentByJsoup(url).select(".post-content").last().text());
				// }
			}else{
				System.out.println(" 回帖失败，失败原因+++！！ "+msg.text());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public Map<String, String> getReplyFormData(Map<String, String> cookies,
			String replayUrl) {
		Document doc =   (NetUtils.getDocumentByJsoupUsingCookie(replayUrl,WrapperUtil.map2Json(cookies)
							  ));
		if (doc == null) {
			LOG.error(" 访问回帖页面出错了！ " + replayUrl);
		}

		Map<String, String> formData = new HashMap<String, String>();
		for (Element input : doc.select(System
				.getProperty("select.post.replay.form.eles"))) {
			String name = input.attr("name");
			if (name == null || name.trim().length() == 0) {
				LOG.debug(" login form input element name is null !!");
				continue;
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug(" name : " + name + " value : " + input.val());
			}
			String oldValue = formData.get(name);
			if (oldValue != null && oldValue.trim().length() > 1) {
				continue;
			}
			formData.put(name, input.val());
		}
		if (LOG.isDebugEnabled()) {
			// LOG.debug( "replay doc :"+doc);
			LOG.debug("replay form data :" + formData);
		}
		return formData;
	}

	private static Pattern postIdRegx = Pattern.compile(System
			.getProperty("regx.post.urlparam.postid"));

	public static String getPostId(String url) {
		Matcher m = postIdRegx.matcher(url);
		if (m.find()) {
			String result = m.group();
			return result.substring(result.indexOf("=") + 1);
		}
		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		Loginer loginer = new Loginer("zwwyhy", "wflinhong");
		loginer.login();
		new PostHandler().reply(
				"http://www.uwants.com/viewthread.php?tid=18959364", loginer,
				  "没人吗！..." );

	}
}
