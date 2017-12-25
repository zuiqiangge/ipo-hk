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
 * 文汇报--社评
 * @author admin
 *
 */
public class WhbSp extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "http://paper.wenweipo.com/other/index-005WW-0.html";
		String nextUrl = null;
		try {
			Pattern p = Pattern.compile("\\d{4}([-]\\d{2}){2}");
			Document doc = null;
			do {
				List<News> newsList = new ArrayList<News>();
				if(nextUrl != null && nextUrl.trim().length() > 0){
					doc = getConnection(nextUrl).get();
				}else{
					doc = getConnection(url).get();
				}
				Elements eles = doc.select(".534e4e");
				for (Element ele : eles) {
					String pubDate = null;
					Matcher m = p.matcher(ele.nextElementSibling().text());
					if (m.find())
						pubDate = m.group();

					//判断新闻是否过期
					if(isOverdue(pubDate,"yyyy-MM-dd")){
						System.out.println("---新闻过期了跳过---");
						break;
					}
					String href = ele.attr("href");
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
						pubDate = DateUtil.fmtDt(
								DateUtil.parse(pubDate, "yyyy-MM-dd"),
								timeFormat);
						String title = descDoc.select("#main-header .title")
								.text();
						String context = descDoc.select("#main-content").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						News news = getNewsBean(title, pubDate, SourceInfo.WHB_SP, "0", desc, context, href);
						//添加到List
						newsList.add(news);
					}
				}
				if (newsList != null && newsList.size() > 0) {
					//保存数据库
					getNewsService().batchInsert(newsList);
				}else{
					break;//没有今日的新闻了直接跳出循环不再请求下一页
				}
				nextUrl = doc.select(".toutiao a:contains(下一頁)").attr("href");
			} while (nextUrl != null && nextUrl.trim().length() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
