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
 * 经济导报--观察时评
 * @author admin
 *
 */
public class JjdbGcsp extends CollectionNews {

	@Override
	public void collection() {
		String url = "http://new.jdonline.com.hk/index.php?m=content&c=index&a=lists&catid=12";
		Pattern p = Pattern.compile("\\d{4}([-]\\d{2}){2}");
		String nextUrl = null;
		try {
			do {
				List<News> newsList = new ArrayList<News>();
				Document doc = null;
				if (nextUrl == null || nextUrl.trim().length() == 0) {
					doc = getConnection(url).get();
				} else {
					doc = getConnection(nextUrl)
							.header("Referer", "http://new.jdonline.com.hk/index.php")
							.get();
				}
				Elements eles = doc.select(".main_left790 .main_left790c:has(.main_title18)");
				for (Element ele : eles) {
					String pubDate = null;
					Matcher m = p.matcher(ele.select(".link_red").text());
					if(m.find()){
						pubDate = m.group();
					}
					//判断新闻是否过期
					if(isOverdue(pubDate,"yyyy-MM-dd")){
						System.out.println("---新闻过期了跳过---");
						continue;
					}
					String href = ele.select(".main_title18").attr("href");
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
						pubDate = DateUtil.fmtDt(DateUtil.parseDate(pubDate), timeFormat);
						String title = ele.select(".main_title18").text();
						String context = descDoc.select(".page_text").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						News news = getNewsBean(title, pubDate, SourceInfo.JJDB_GCSP, "0", desc, context, href);
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
				nextUrl = doc.select(".list_pagenum:contains(下一頁)").attr("href");
				System.out.println(nextUrl);
				
			} while (nextUrl != null && nextUrl.trim().length() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
