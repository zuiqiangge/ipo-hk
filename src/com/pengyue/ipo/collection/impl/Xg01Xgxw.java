package com.pengyue.ipo.collection.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.collection.CollectionNews;
import com.pengyue.ipo.collection.SourceInfo;

/**
 * 香港01--香港新闻
 * @author admin
 *
 */
public class Xg01Xgxw extends CollectionNews {

	@Override
	public void collection() {
		String url = "http://www.hk01.com/channel/%E6%96%B0%E8%81%9E";
		String nextUrl = null;
		Pattern p = Pattern.compile("\\d{4}([-]\\d{2}){2}\\s\\d{2}[:]\\d{2}");
		int page = 0;
		try {
			do {
				Document doc = null;
				List<News> newsList = new ArrayList<News>();
				//判断是否采集下一页数据
				if (nextUrl == null || nextUrl.trim().length() == 0) {
					doc = getConnection(url).get();
				} else {
					System.out.println(nextUrl);
					doc = getConnection(nextUrl)
								.header("Referer", "http://www.hk01.com/channel/%E6%96%B0%E8%81%9E")
								.header("X-Requested-With", "XMLHttpRequest")
								.get();
				}
				Elements eles = doc.select(".blog_listing__item");
				for (Element ele : eles) {
					String href = ele.select("a").attr("href");
					href = setUrlChineseCode(href, "UTF-8");
					//判断url是否已存在
					if (!getNewsService().urlExists(href)) {
						Document descDoc = null;
						try {
							descDoc = getConnection(href).get();
						} catch (Exception e) {
							System.out.println("详情页采集报错："+e.getMessage());
							continue;
						}
						String pubDate = null;
						String context = null;
						try {
							pubDate = descDoc.select(".article_info .date").first().text();
							context = descDoc.select(".article_content__module p").text();
						} catch (Exception e) {
							System.out.println("解析详情页报错：" + e.getMessage());
							continue;
						}
						Matcher m = p.matcher(pubDate);
						if(m.find()){
							pubDate = m.group() + ":00";
						}
						//判断新闻是否过期
						if(isOverdue(pubDate.split(" ")[0],"yyyy-MM-dd")){
							System.out.println("---新闻过期了跳过---");
							break;
						}
						String title = ele.select(".blog_listing__item__content__tit h3").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						
						News news = getNewsBean(title, pubDate, SourceInfo.XG01_XGXW, "0", desc, context, href);
						//添加到List
						newsList.add(news);
					}
				}
				if (newsList != null && newsList.size() > 0) {
					//保存数据库
					getNewsService().batchInsert(newsList);
					nextUrl = "https://www.hk01.com/api/get_articles/1/"+page+"/90?TEMPLATES=blog_listing";
					page++;
				}else{
					break;//采不到今天数据就不用去下一页了
				}
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	/**
//	 * 请求Json数据
//	 * @param url
//	 * @return
//	 * @throws Exception 
//	 */
//	public List<News> getListByJson(String url) throws Exception{
//		List<News> newsList = new ArrayList<News>();
//		String jsonStr = getConnection(url)
//								.header("Referer", "http://www.hk01.com/channel/%E6%96%B0%E8%81%9E")
//								.header("X-Requested-With", "XMLHttpRequest")
//								.ignoreContentType(true)
//								.execute().body();
//		JSONObject json = JSONObject.fromObject(jsonStr);
//		JSONArray array = json.getJSONArray("article_list");
//		for (int i = 0; i < array.size(); i++) {
//			JSONObject obj = array.getJSONObject(i);
//			String pubDate = obj.getString("publish_start_time");
//			//判断新闻是否过期
//			if(isOverdue(pubDate.split(" ")[0],"yyyy-MM-dd")){
//				System.out.println("---新闻过期了跳过---");
//				continue;
//			}
//			String href = obj.getString("link");
//			//判断url是否存在
//			if(!getNewsService().urlExists(href)){
//				String title = obj.getString("title");
//				Document descDoc = null;
//				try {
//					descDoc = getConnection(href).get();
//				} catch (Exception e) {
//					System.out.println("请求详情页报错：" + e.getMessage());
//					continue;
//				}
//				String context = descDoc.select(".article_content__module p").text();
//				String desc = null;
//				if (context.length() > 80) {
//					desc = context.substring(0, 80) + "...";
//				} else {
//					desc = context;
//				}
//				
//				News news = getNewsBean(title, pubDate, SourceInfo.XG01_XGXW, "0", desc, context, href);
//				//添加到List
//				newsList.add(news);
//			}
//		}
//		return newsList;
//	}
	
//	/**
//	 * 请求Html页面
//	 * @param url
//	 * @return
//	 * @throws Exception 
//	 */
//	public List<News> getListByHtml(String url) throws Exception{
//		List<News> newsList = new ArrayList<News>();
//		Document doc = null;
//		Pattern p = Pattern.compile("\\d{4}([-]\\d{2}){2}\\s\\d{2}[:]\\d{2}");
//		doc = getConnection(url).get();
//		Elements eles = doc.select(".blog_listing__item");
//		for (Element ele : eles) {
//			String href = ele.select("a").attr("href");
//			//判断url是否已存在
//			if (!getNewsService().urlExists(href)) {
//				Document descDoc = null;
//				try {
//					descDoc = getConnection(href).get();
//				} catch (Exception e) {
//					System.out.println("详情页采集报错："+e.getMessage());
//					continue;
//				}
//				String pubDate = null;
//				String context = null;
//				try {
//					pubDate = descDoc.select(".article_info .date").first().text();
//					context = descDoc.select(".article_content__module p").text();
//				} catch (Exception e) {
//					System.out.println("解析详情页报错：" + e.getMessage());
//					continue;
//				}
//				Matcher m = p.matcher(pubDate);
//				if(m.find()){
//					pubDate = m.group() + ":00";
//				}
//				//判断新闻是否过期
//				if(isOverdue(pubDate.split(" ")[0],"yyyy-MM-dd")){
//					System.out.println("---新闻过期了跳过---");
//					continue;
//				}
//				String title = ele.select(".blog_listing__item__content__tit h3").text();
//				String desc = null;
//				if (context.length() > 80) {
//					desc = context.substring(0, 80) + "...";
//				} else {
//					desc = context;
//				}
//				
//				News news = getNewsBean(title, pubDate, SourceInfo.XG01_XGXW, "0", desc, context, href);
//				//添加到List
//				newsList.add(news);
//			}
//		}
//		return newsList;
//	}

}
