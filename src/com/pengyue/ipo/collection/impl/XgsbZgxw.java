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
 * 香港商报--中国新闻
 * @author admin
 *
 */
public class XgsbZgxw extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "http://www.hkcd.com.hk/node_30564.htm";
		String nextUrl = null;
		try {
			Pattern p = Pattern.compile("\\d{4}[-]\\d{1,2}[/]\\d{1,2}");
			Pattern ptime = Pattern.compile("\\d{2}[:]\\d{2}");
			do {
				List<News> newsList = new ArrayList<News>();
				Document doc = null;
				if(nextUrl != null && nextUrl.trim().length() > 0){
					doc = getConnection(nextUrl).get();
				}else{
					doc = getConnection(url).get();
				}
				Elements eles = doc.select("table.wenzi>tbody>tr:has(a)");
				for (Element ele : eles) {
					String pubDate = null;
					String href = "http://www.hkcd.com.hk/"
							+ ele.select("a").attr("href");
					Matcher m = p.matcher(href);
					if (m.find()) {
						pubDate = m.group();
					}
					//判断新闻是否过期
					if(isOverdue(pubDate,"yyyy-MM/dd")){
						System.out.println("---新闻过期了跳过---");
						break;
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
						String time = " 00:00";
						Matcher mtime = ptime.matcher(ele.select(".wenzi").get(0).ownText());
						if(mtime.find()){
							time = " " + mtime.group();
						}
						//转换日期格式
						pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate + time, "yyyy-MM/dd HH:mm"), timeFormat);
						String title = descDoc.select(".heibiaoti").text();
						String context = descDoc.select(
								"table:has(.heibiaoti) tbody tr:eq(5)").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}

						News news = getNewsBean(title, pubDate, SourceInfo.XGSB_ZGXW, "0", desc, context, href);
						//添加到List
						newsList.add(news);
					}
				}
				if (newsList != null && newsList.size() > 0) {
					//保存数据库
					getNewsService().batchInsert(newsList);
				} else {
					break;//没有今日的新闻了直接跳出循环不再请求下一页
				}
				
				String next = doc.select("#div_currpage a:contains(下一页)").attr("href");
				if(next != null && next.trim().length() > 0){
					nextUrl = "http://www.hkcd.com.hk/" + next;
				}else{
					nextUrl = null;
				}
			} while (nextUrl != null && nextUrl.trim().length() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
