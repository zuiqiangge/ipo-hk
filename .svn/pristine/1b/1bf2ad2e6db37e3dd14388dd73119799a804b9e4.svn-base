package com.pengyue.ipo.collection.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.collection.CollectionNews;
import com.pengyue.ipo.collection.SourceInfo;
import com.pengyue.ipo.util.DateUtil;

/**
 * 文汇报--即时香港新闻
 * @author admin
 *
 */
public class WhbJsxgxw extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "http://news.wenweipo.com/list_news.php?cat=000IN&instantCat=hk";
		try {
			List<News> newsList = new ArrayList<News>();
			Document doc = getConnection(url).get();
			Elements eles = doc.select("#content .content-art-box");
			for (Element ele : eles) {
				Document descDoc = null;
				String href = ele.select("h2 a").attr("href");
				try {
					descDoc = getConnection(href).get();
				} catch (Exception e) {
					System.out.println("请求详情页报错:"+e.getMessage());
					continue;
				}
				String pubDate = descDoc.select(".foot-left .date").text().trim();
				//判断新闻是否过期
				if(isOverdue(pubDate,"yyyy-MM-dd")){
					System.out.println("---新闻过期了跳过---");
					continue;
				}
				//判断url是否已存在
				if (!getNewsService().urlExists(href)) {
					//转换格式
					pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate,"yyyy-MM-dd"),timeFormat);
					String title = descDoc.select("#main-header .title").text();
					String context = descDoc.select("#main-content").text();
					String desc = null;
					if (context.length() > 80) {
						desc = context.substring(0, 80) + "...";
					} else {
						desc = context;
					}
					News news = getNewsBean(title, pubDate, SourceInfo.WHB_JSXGXW, "0", desc, context, href);
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
