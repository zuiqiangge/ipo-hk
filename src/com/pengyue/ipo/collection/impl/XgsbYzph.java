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
 * 香港商报--一周排行
 * @author admin
 *
 */
public class XgsbYzph extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "http://www.hkcd.com/week_ranking.html";
		try {
			Pattern p = Pattern.compile("\\d{4}([-]\\d{2}){2}");
			List<News> newsList = new ArrayList<News>();
			Document doc = getConnection(url).get();
			Elements eles = doc.select(".cat_newlist>ul>li");
			for (Element ele : eles) {
				String pubDate = null;
				Matcher m = p.matcher(ele.select("span").text());
				if (m.find()) {
					pubDate = m.group();
				}
				//判断新闻是否过期
				if(isOverdue(pubDate,"yyyy-MM-dd")){
					System.out.println("---新闻过期了跳过---");
					continue;
				}
				
				String href = "http://www.hkcd.com/" + ele.select("a").attr("href");
				
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
					pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate, "yyyy-MM-dd"), timeFormat);
					String title = descDoc.select(".conten_title").text();
					String context = descDoc.select(".content_main").text();
					String desc = null;
					if (context.length() > 80) {
						desc = context.substring(0, 80) + "...";
					} else {
						desc = context;
					}

					News news = getNewsBean(title, pubDate, SourceInfo.XGSB_YZPH, "0", desc, context, href);
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
