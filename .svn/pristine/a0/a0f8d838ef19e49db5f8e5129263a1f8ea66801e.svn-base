package com.pengyue.ipo.collection.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.nodes.Document;

import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.collection.CollectionNews;
import com.pengyue.ipo.collection.SourceInfo;

/**
 * 香港新浪网--新闻
 * @author admin
 *
 */
public class XgxlwXw extends CollectionNews {

	@Override
	public void collection() {
		int page = 1;
		try {
			do {
				String url = "http://sina.com.hk/p/api/news/main/category/8/0/0/" + page;
				List<News> newsList = new ArrayList<News>();
				String jsonStr = getConnection(url)
									.ignoreContentType(true)
									.execute()
									.body();
				JSONArray array = JSONObject.fromObject(jsonStr).getJSONArray("data");
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					String pubDate = obj.getString("newsdate");
					//判断新闻是否过期
					if(isOverdue(pubDate.split(" ")[0],"yyyy-MM-dd")){
						System.out.println("---新闻过期了跳过---");
						break;
					}
					String href = "http://sina.com.hk" + obj.getString("url");
					//判断url是否已存在
					if (!getNewsService().urlExists(href)) {
						Document descDoc = null;
						try {
							descDoc = getConnection(href).get();
						} catch (Exception e) {
							System.out.println("请求详情页报错:"+e.getMessage());
							continue;
						}
						String title = obj.getString("title");
						String context = descDoc.select("#news-main-body>p").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						
						News news = getNewsBean(title, pubDate, SourceInfo.XGXLW_XW, "0", desc, context, href);
						newsList.add(news);
					}
				}
				if (newsList != null && newsList.size() > 0) {
					//保存数据库
					getNewsService().batchInsert(newsList);
				} else {
					break;
				}
				page++;
			} while (true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
