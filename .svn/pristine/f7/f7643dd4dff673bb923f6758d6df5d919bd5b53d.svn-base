package com.pengyue.ipo.collection.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.collection.CollectionNews;
import com.pengyue.ipo.collection.SourceInfo;
import com.pengyue.ipo.util.DateUtil;

/**
 * 香港经济日报--即时新闻
 * @author admin
 *
 */
public class XgjjrbJsxw extends CollectionNews {

	@Override
	public void collection() {
		String today = DateUtil.fmtDt(new Date(), "yyyyMMdd");
		String url = "http://inews.hket.com/sran001/%E5%85%A8%E9%83%A8?dis=" + today;
		try {
				List<News> newsList = new ArrayList<News>();
				Document doc = getConnection(url).get();
				Elements eles = doc.select("#eti-inews-list>tbody>tr");
				for (Element ele : eles) {
					String href = ele.select("a").attr("href");
					//判断url是否已存在
					if (!getNewsService().urlExists(href)) {
						Document descDoc = null;
						try {
							descDoc = getConnection(href).get();
						} catch (Exception e) {
							System.out.println("请求详情页报错:"+e.getMessage());
							continue;
						}
						//由于url中限制了当天日期所以不用判断新闻日期是否过期
						String pubDate = today + " " + ele.select("span").text();
						//转换格式
						pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate, "yyyyMMdd HH:mm"), timeFormat);
						
						String title = ele.select("a").text();
						String context = descDoc.select("#content-main").text();
						if(context == null || context.trim().isEmpty()){
							context = descDoc.select("#eti-article-content-body").text();
						}
						
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						
						News news = getNewsBean(title, pubDate, SourceInfo.XGJJRB_JSXW, "0", desc, context, href);
						//添加到List
						newsList.add(news);
					}
				}
				if (newsList != null && newsList.size() > 0) {
					//保存数据库
					getNewsService().batchInsert(newsList);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
