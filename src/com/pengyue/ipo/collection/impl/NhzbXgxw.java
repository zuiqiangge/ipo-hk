package com.pengyue.ipo.collection.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.collection.CollectionNews;
import com.pengyue.ipo.collection.SourceInfo;

/**
 * 南华早报--香港新闻
 * @author admin
 *
 */
public class NhzbXgxw extends CollectionNews {

	@Override
	public void collection() {
		String url = "http://www.scmp.com/news/hong-kong";
		Pattern p = Pattern.compile("\\d{4}([-]\\d{2}){2}[T]\\d{2}([:]\\d{2}){2}");
		List<News> newsList = new ArrayList<News>();
		try {
			Document doc = getConnection(url).get();
			Elements eles = doc.select(".ptcg-col-1 .content-wrapper:has(time)");
			for (Element ele : eles) {
				String pubDate = ele.select("time").attr("datetime");
				Matcher m = p.matcher(pubDate);
				if(m.find()){
					pubDate = m.group();
				}
				//判断新闻是否过期
				if(isOverdue(pubDate.split("T")[0],"yyyy-MM-dd")){
					System.out.println("---新闻过期了跳过---");
					continue;
				}
				String href = "http://www.scmp.com" + ele.select(".node-title a").attr("href");
				//判断url是否已存在
				if (!getNewsService().urlExists(href)) {
					Document descDoc = null;
					try {
						descDoc = getConnection(href).get();
					} catch (Exception e) {
						System.out.println("请求详情页报错:"+e.getMessage());
						continue;
					}
					String title = ele.select(".node-title").text();
					//转换格式
					pubDate = pubDate.replace("T", " ");
					
					String context = descDoc.select(".pane-node-body .pane-content").text();
					String desc = null;
					if (context.length() > 80) {
						desc = context.substring(0, 80) + "...";
					} else {
						desc = context;
					}
					News news = getNewsBean(title, pubDate, SourceInfo.NHZB_XGXW, "0", desc, context, href);
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
