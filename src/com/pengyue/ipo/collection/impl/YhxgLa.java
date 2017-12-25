package com.pengyue.ipo.collection.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.collection.CollectionNews;
import com.pengyue.ipo.collection.SourceInfo;

/**
 * 雅虎香港--两岸
 * @author admin
 *
 */
public class YhxgLa extends CollectionNews {
	@Test
	public void test(){
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "8636");
		collection();
	}

	@Override
	public void collection() {
		String url = "https://hk.news.yahoo.com/china/archive/1.html";
		String nextUrl = null;
		try {
			do {
				List<News> newsList = new ArrayList<News>();
				Document doc = null;
				if (nextUrl == null || nextUrl.trim().length() == 0) {
					doc = getConnection(url).get();
				} else {
					System.out.println(nextUrl);
					doc = getConnection(nextUrl).get();
				}
				Elements eles = doc.select(".yom-list-wide li");
				for (Element ele : eles) {
					String href = "https://hk.news.yahoo.com" + ele.select(".txt>h4>a").attr("href");
					//判断url是否已存在
					if (!getNewsService().urlExists(href)) {
						Document descDoc = null;
						try {
							System.out.println(href);
							descDoc = getConnection(href).get();
						} catch (Exception e) {
							System.out.println("请求详情页报错:"+e.getMessage());
							continue;
						}
						String pubDate = descDoc.select(".byline>abbr").attr("title");
						//判断新闻是否过期
						if(isOverdue(pubDate.split("T")[0],"yyyy-MM-dd")){
							System.out.println("---新闻过期了跳过---");
							break;
						}
						//转换日期格式
						pubDate = pubDate.replace("T", " ").replace("Z", "");
						String title = ele.select(".txt>h4>a").text();
						String context = descDoc.select("#mediaarticlebody>.bd>p").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						
						News news = getNewsBean(title, pubDate, SourceInfo.YHXG_LA, "0", desc, context, href);
						newsList.add(news);
					}
				}
				if (newsList != null && newsList.size() > 0) {
					//保存数据库
					getNewsService().batchInsert(newsList);
				} else {
					break;
				}
				
				//下一页地址
				String next = doc.select(".future .next:has(.icon-arrow-next-1)").attr("href");
				if(next != null && next.trim().length() > 0){
					nextUrl = "https://hk.news.yahoo.com" + next;
				}else{
					nextUrl = null;
				}
			} while (nextUrl != null && nextUrl.trim().length() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
