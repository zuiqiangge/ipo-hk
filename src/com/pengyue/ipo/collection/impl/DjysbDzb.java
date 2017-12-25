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
 * 大纪元时报--电子版
 * @author admin
 *
 */
public class DjysbDzb extends CollectionNews {

	@Override
	public void collection() {
		String today = DateUtil.getcurrDate();
		String url = "http://hk.epochtimes.com/news/today/"+today;
		Pattern p = Pattern.compile("\\d{4}([-]\\d{2}){2}");
		List<News> newsList = new ArrayList<News>();
		try {
			Document doc = getConnection(url).get();
			Elements eles = doc.select(".news-headline>a,.right-news-list article>a");
			for (Element ele : eles) {
				String href = "http://hk.epochtimes.com" + ele.attr("href");
				String pubDate = null;
				Matcher m = p.matcher(href);
				if(m.find()){
					pubDate = m.group();
				}
				//判断新闻是否过期
				if(isOverdue(pubDate,"yyyy-MM-dd")){
					System.out.println("---新闻过期了跳过---");
					continue;
				}
				href = setUrlChineseCode(href,"UTF-8");
				//判断url是否已存在
				if(!getNewsService().urlExists(href)){
					Document descDoc = null;
					try {
						descDoc = getConnection(href).get();
					} catch (Exception e) {
						System.out.println("请求详情页报错:"+e.getMessage());
						continue;
					}
					//转换日期格式
					pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate, "yyyy-MM-dd"), timeFormat);
					String title = descDoc.select(".article-headline").text();
					String context = descDoc.select(".article-body").text();
					String desc = null;
					if (context.length() > 80) {
						desc = context.substring(0, 80) + "...";
					} else {
						desc = context;
					}
					News news = getNewsBean(title, pubDate, SourceInfo.DJYSB_DZB, "0", desc, context, href);
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
