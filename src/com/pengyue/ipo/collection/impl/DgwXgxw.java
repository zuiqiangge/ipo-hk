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

/**
 * 大公网--香港新闻
 * @author admin
 *
 */
public class DgwXgxw extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "http://www.takungpao.com.hk/hongkong/";
		try {
			List<News> newsList = new ArrayList<News>();
			Document doc = getConnection(url).get();
			Elements eles = doc.select(".list_tuwen .content");
			for (Element ele : eles) {
				String pubDate = ele.select(".txt .time span").text().trim();
				//判断新闻是否过期
				if(isOverdue(pubDate,"yyyy-MM-dd")){
					System.out.println("---新闻过期了跳过---");
					continue;
				}
				String href = ele.select(".txt .title a").attr("href");
				//有的连接是完整的有的却不是，判断一下
				if(href.indexOf(":") == -1){
					href = "http://www.takungpao.com.hk" + href;
				}
				//判断url是否已存在
				if (!getNewsService().urlExists(href)) {
					Document descDoc = null;
					try {
						descDoc = getConnection(href).get();
					} catch (Exception e) {
						System.out.println("请求详情页报错:"+e.getMessage());
						continue;
					}
					//转换日期格式
					pubDate = descDoc.select(".tkp_con_author span").first().text();
					String title = descDoc.select(".tkp_con_title").text();
					String context = descDoc.select(".tkp_content").text();
					String desc = null;
					if (context.length() > 80) {
						desc = context.substring(0, 80) + "...";
					} else {
						desc = context;
					}
					News news = getNewsBean(title, pubDate, SourceInfo.DGW_XGXW, "0", desc, context, href);
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
