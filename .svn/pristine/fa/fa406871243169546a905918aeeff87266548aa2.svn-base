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
 * 香港01--首页
 * @author admin
 *
 */
public class Xg01Home extends CollectionNews {

	@Override
	public void collection() {
		Pattern p = Pattern.compile("\\d{4}([-]\\d{2}){2}\\s\\d{2}[:]\\d{2}");
		String url = "http://www.hk01.com/";
		List<News> newsList = new ArrayList<News>();
		try {
			Document doc = getConnection(url).get();
			Elements eles = doc.select("a.item");
			for (Element ele : eles) {
				String href = ele.attr("href");
				href = setUrlChineseCode(href, "UTF-8");
				//判断url是否已存在
				if (!getNewsService().urlExists(href)) {
					Document descDoc = null;
					try {
						descDoc = getConnection(href).get();
					} catch (Exception e) {
						System.out.println("请求详情页报错:"+e.getMessage());
						continue;
					}
					String pubDate = descDoc.select(".article_info .date").first().text();
					Matcher m = p.matcher(pubDate);
					if(m.find()){
						pubDate = m.group();
					}
					//判断新闻是否过期
					if(isOverdue(pubDate.split(" ")[0],"yyyy-MM-dd")){
						System.out.println("---新闻过期了跳过---");
						continue;
					}
					//转换格式
					pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate, "yyyy-MM-dd HH:mm"), timeFormat);
					String title = ele.select(".item__tit").text();
					String context = descDoc.select(".article__body__content>.article_content__module p").text();
					String desc = null;
					if (context.length() > 80) {
						desc = context.substring(0, 80) + "...";
					} else {
						desc = context;
					}
					
					News news = getNewsBean(title, pubDate, SourceInfo.XG01_HOME, "0", desc, context, href);
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
