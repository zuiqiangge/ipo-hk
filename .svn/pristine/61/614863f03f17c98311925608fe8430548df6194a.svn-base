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
 * 苹果日报--往期回顾
 * @author admin
 *
 */
public class PgrbWqhg extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "http://hk.apple.nextmedia.com/archive/index";
		try {
			List<News> newsList = new ArrayList<News>();
			Document doc = getConnection(url).get();
			Elements eles = doc.select(".Archive #tab1 ul li a:eq(0)");
			for (Element ele : eles) {
				String title = ele.text();
				String href = ele.attr("href");
				Document descDoc = null;
				try {
					descDoc = getConnection(href).get();
				} catch (Exception e) {
					System.out.println("请求详情页报错:"+e.getMessage());
					continue;
				}
				String pubDate = descDoc.select(".SelectHdate").text().trim();
				//判断新闻是否过期
				if(isOverdue(pubDate,"yyyy年MM月dd日")){
					System.out.println("---新闻过期了跳过---");
					continue;
				}
				//判断url是否已存在
				if (!getNewsService().urlExists(href)) {
					//转换日期格式
					pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate, "yyyy年MM月dd日"), timeFormat);
					String viewNum = descDoc.select(".view").text().replace(",", "");
					if(viewNum.trim().length() == 0){
						viewNum = "0";
					}
					String context = descDoc.select("#masterContent .ArticleContent_Outer").text();
					String desc = null;
					if (context.length() > 80) {
						desc = context.substring(0, 80) + "...";
					} else {
						desc = context;
					}
					News news = getNewsBean(title, pubDate, SourceInfo.PGRB_WQHG, viewNum, desc, context, href);
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
