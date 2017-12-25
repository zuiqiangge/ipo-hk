package com.pengyue.ipo.collection.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.nodes.Document;

import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.collection.CollectionNews;
import com.pengyue.ipo.collection.SourceInfo;
import com.pengyue.ipo.util.DateUtil;

/**
 * 大纪元时报--香港新闻
 * @author admin
 *
 */
public class DjysbXgxw extends CollectionNews {
	
	@Override
	public void collection() {
		int page = 1;
		try {
			do {
				List<News> newsList = new ArrayList<News>();
				String url = "http://api.hk.epochtimes.com/v1/articles?category=%E6%B8%AF%E8%81%9E&contains_chird=true&count=10&page="
						+ page;
				String jsonStr = getConnection(url).ignoreContentType(true)
												.execute()
												.body();
				JSONArray array = JSONArray.fromObject(jsonStr);
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					String pubDate = obj.getString("date");
					//判断新闻是否过期
					if(isOverdue(pubDate,"yyyy-MM-dd")){
						System.out.println("---新闻过期了跳过---");
						continue;
					}
					String href = "http://hk.epochtimes.com"
							+ obj.getString("link");
					href = setUrlChineseCode(href, "UTF-8");
					//判断url是否存在
					if (!getNewsService().urlExists(href)) {
						Document descDoc = null;
						try {
							descDoc = getConnection(href).get();
						} catch (Exception e) {
							System.out.println("请求详情页报错：" + e.getMessage());
							continue;
						}
						//转换日期格式
						pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate,"yyyy-MM-dd"), timeFormat);
						String title = obj.getString("title");
						String context = descDoc.select(".article-body").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						News news = getNewsBean(title, pubDate, SourceInfo.DJYSB_XGXW, "0", desc, context, href);
						//添加到List
						newsList.add(news);
					}
				}
				if (newsList != null && newsList.size() > 0) {
					//保存数据库
					getNewsService().batchInsert(newsList);
					page++;
				}else{
					break;
				}
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
