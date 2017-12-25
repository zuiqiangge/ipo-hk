package com.pengyue.ipo.collection.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.collection.CollectionNews;
import com.pengyue.ipo.collection.SourceInfo;
import com.pengyue.ipo.util.DateUtil;

/**
 * AM730--新闻
 * @author admin
 *
 */
public class Am730Xw extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "https://www.am730.com.hk/news/%E6%96%B0%E8%81%9E";
		String nextUrl = null;
		try {
			do {
				List<News> newsList = null;
				if(nextUrl == null){
					Document doc = getConnection(url).get();
					Elements eles = doc.select(".news-list-container .news-list");
					newsList = getListByHtml(eles);
					
					String next = eles.last().select(".newsTitle a").attr("href");
					if (next != null && next.trim().length() > 0) {
						String id = next.substring(next.lastIndexOf("-") + 1);
						nextUrl = "https://www.am730.com.hk/news/getLoadMorePost/news/1/"
								+ id + "/6";
						System.out.println(nextUrl);
					}
				}else{
					String jsonStr = getConnection(nextUrl).execute().body();
					JSONObject json = JSONObject.fromObject(jsonStr);
					JSONArray array = json.getJSONArray("main_post");
					newsList = getListByJSONArray(array);
					
					String id = array.getJSONObject(array.size()-1).getString("post_id");
					nextUrl = "https://www.am730.com.hk/news/getLoadMorePost/news/1/"
							+ id + "/6";
					System.out.println(nextUrl);
				}
				
				if(newsList != null && newsList.size() > 0){
					getNewsService().batchInsert(newsList);
				}else{
					break;
				}

			} while (nextUrl != null && nextUrl.trim().length() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从json中获得list
	 * @param eles
	 * @return
	 */
	private List<News> getListByJSONArray(JSONArray array) {
		List<News> newsList = new ArrayList<News>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject json = array.getJSONObject(i);
			String pubDate = json.getString("publish_date");
			//新闻是否过期
			if(isOverdue(pubDate, timeFormat)){
				System.out.println("--新闻已过期跳过--");
				continue;
			}
			String href = "https://www.am730.com.hk/news/%E6%96%B0%E8%81%9E/" + json.getString("permalink");
			if (!getNewsService().urlExists(href)) {
				Document descDoc = null;
				try {
					descDoc = getConnection(href).get();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("请求详情页报错：" + e.getMessage());
					continue;
				}
				String title = descDoc.select(".news-detail-title").text();
				String context = descDoc.select(".news-detail-content p")
						.text();
				String desc = null;
				if (context.length() > 80) {
					desc = context.substring(0, 80) + "...";
				} else {
					desc = context;
				}
				News news = getNewsBean(title, pubDate, SourceInfo.AM730_XW,
						"0", desc, context, href);
				newsList.add(news);
			}
		}
		return newsList;
	}
	/**
	 * 从html中获得list
	 * @param eles
	 * @return
	 */
	public List<News> getListByHtml(Elements eles){
		List<News> newsList = new ArrayList<News>();
		for (Element ele : eles) {
			String pubDate = ele.select(".news-time").text();
			pubDate = parsingTime(pubDate);
			if (pubDate == null) {
				System.out.println("--解析新闻时间错误！跳过--");
				continue;
			}
			if (isOverdue(pubDate, "yyyy-MM-dd")) {
				System.out.println("--新闻已过期跳过--");
				continue;
			}
			String href = ele.select(".newsTitle a").attr("href");

			if (!getNewsService().urlExists(href)) {
				//转换日期格式
				pubDate += " 00:00:00";
				String title = ele.select(".newsTitle").text();
				Document descDoc = null;
				try {
					descDoc = getConnection(href).get();
				} catch (Exception e) {
					System.out.println("请求详情页报错：" + e.getMessage());
					continue;
				}
				String context = descDoc.select(".news-detail-content p")
						.text();
				String desc = null;
				if (context.length() > 80) {
					desc = context.substring(0, 80) + "...";
				} else {
					desc = context;
				}
				News news = getNewsBean(title, pubDate, SourceInfo.AM730_XW,
						"0", desc, context, href);
				newsList.add(news);
			}
		}
		return newsList;
	}
	
	/**
	 * 解析日期
	 * @param pubDate
	 * @return
	 */
	public String parsingTime(String pubDate){
		pubDate = pubDate.trim();
		if(pubDate.contains("分前") || pubDate.contains("小時前")){
			return DateUtil.getcurrDate();
		}else if(pubDate.matches("\\d{4}[-](\\d{1,2}){2}")){
			return pubDate;
		}
		throw new RuntimeException("解析时间错误");
	}

}
