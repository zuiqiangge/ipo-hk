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
 * 香港新闻网--台湾新闻
 * @author admin
 *
 */
public class XgxwwTwxw extends CollectionNews {

	@Override
	public void collection() {
		String url = "http://www.hkcna.hk/m/twxw0.html";
		String nextUrl = null;
		int page = 2;
		try {
			do {
				List<News> newsList = new ArrayList<News>();
				Document doc = null;
				if (nextUrl == null || nextUrl.trim().length() == 0) {
					doc = getConnection(url).get();
				} else {
					doc = getConnection(nextUrl).get();
				}
				Elements eles = doc.select(".w-list li");
				for (Element ele : eles) {
					String pubDate = ele.select("span").text();
					//判断新闻是否过期
					if(isOverdue(pubDate.split(" ")[0],"yyyy.MM.dd")){
						System.out.println("---新闻过期了跳过---");
						break;
					}
					String href = "http://www.hkcna.hk"
							+ ele.select("a").attr("href");
					//判断url是否已存在
					if (!getNewsService().urlExists(href)) {
						Document descDoc = null;
						try {
							descDoc = getConnection(href).get();
						} catch (Exception e) {
							System.out.println("请求详情页报错:" + e.getMessage());
							continue;
						}
						//转换日期格式
						pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate, "yyyy.MM.dd HH:mm"), timeFormat);
						String title = ele.select("a").text();
						String context = descDoc.select(".wz_nr>p").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						
						News news = getNewsBean(title, pubDate, SourceInfo.XGXWW_TWXW, "0", desc, context, href);
						newsList.add(news);
					}
				}
				if (newsList != null && newsList.size() > 0) {
					//保存数据库
					getNewsService().batchInsert(newsList);

					nextUrl = "http://www.hkcna.hk/m/twxw0_" + page + ".html";
					System.out.println(nextUrl);
					page++;
				} else {
					break;
				}
			} while (true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
