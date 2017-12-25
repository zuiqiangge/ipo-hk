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
import com.pengyue.ipo.util.DateUtil;

/**
 * 都市日报--本地新闻
 * @author admin
 *
 */
public class DsrbBdxw extends CollectionNews {

	@Override
	public void collection() {
		String url = "http://www.metrodaily.hk/news-local/";
		Pattern p = Pattern.compile("\\d{4}([/]\\d{2}){2}");
		String nextUrl = null;
		try {
			Document doc = null;
			Elements eles = null;
			int offset = 0;
			do {
				List<News> newsList = new ArrayList<News>();
				if (nextUrl == null) {
					doc = getConnection(url).get();
					eles = doc.select("#w4pl-inner-593 .post-item");
				} else {
					System.out.println(nextUrl);
					doc = getConnection(nextUrl)
							.data("action", "metro_more_news_ajax")
							.data("cat", "0")
							.data("offset", String.valueOf(offset))
							.data("ppp", "4").data("slug", "news-local")
							.data("taxonomy", "news_category")
							.data("type", "metro_news").post();
					eles = doc.select(".post-item");
				}
				for (Element ele : eles) {
					String pubDate = ele.select(".content .date").text();
					Matcher m = p.matcher(pubDate);
					if (m.find()) {
						pubDate = m.group();
					}
					//判断新闻是否过期
					if(isOverdue(pubDate,"yyyy/MM/dd")){
						System.out.println("---新闻过期了跳过---");
						continue;
					}
					String href = ele.select(".content .title").attr("href");
					//判断url是否已存在
					if (!getNewsService().urlExists(href)) {
						Document descDoc = null;
						try {
							descDoc = getConnection(href).get();
						} catch (Exception e) {
							System.out.println("请求详情页报错:"+e.getMessage());
							continue;
						}
						//转换格式
						pubDate = DateUtil.fmtDt(
								DateUtil.parse(pubDate, "yyyy/MM/dd"), timeFormat);
						String title = ele.select(".content .title").text();
						String context = descDoc.select(".content").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						News news = getNewsBean(title, pubDate, SourceInfo.DSRB_BDXW, "0", desc, context, href);
						//添加到List
						newsList.add(news);
					}
				}
				if (newsList != null && newsList.size() > 0) {
					//保存数据库
					getNewsService().batchInsert(newsList);
				}else{
					break;
				}
				nextUrl = "http://www.metrodaily.hk/wp-admin/admin-ajax.php";
				offset += 4;
			} while (true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
