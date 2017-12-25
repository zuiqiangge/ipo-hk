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
 * 香港独立媒体--政治经济
 * @author admin
 *
 */
public class XgdlmtZzjj extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "http://www.inmediahk.net/taxonomy/term/5030";
		String nextUrl = null;
		Pattern p = Pattern.compile("\\d{4}([-]\\d{2}){2}");
		try {
			do {
				List<News> newsList = new ArrayList<News>();
				Document doc = null;
				if (nextUrl == null || nextUrl.trim().length() == 0) {
					doc = getConnection(url).get();
				} else {
					doc = getConnection(nextUrl).get();
				}
				Elements eles = doc.select(".tag_list .views-row");
				for (Element ele : eles) {
					String pubDate = null;
					Matcher m = p.matcher(ele.select(".date").text());
					if(m.find()){
						pubDate = m.group();
					}
					//判断新闻是否过期
					if(isOverdue(pubDate,"yyyy-MM-dd")){
						System.out.println("---新闻过期了跳过---");
						break;
					}
					String href = "http://www.inmediahk.net"
							+ ele.select(".title a").attr("href");
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
						pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate, "yyyy-MM-dd"), timeFormat);
						String title = ele.select(".title a").text();
						String context = descDoc.select(".post-desc").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						
						News news = getNewsBean(title, pubDate, SourceInfo.XGDLMT_ZZJJ, "0", desc, context, href);
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
				String next = doc.select(".pager-next a").attr("href");
				if(next != null && next.trim().length() > 0){
					nextUrl = "http://www.inmediahk.net" + next;
					System.out.println(nextUrl);
				}else{
					break;
				}
			} while (true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
