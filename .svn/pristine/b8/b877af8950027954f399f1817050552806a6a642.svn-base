package com.pengyue.ipo.collection.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
 * 东方日报--要闻港闻
 * @author admin
 *
 */
public class DfrbYwgw extends CollectionNews {
	@Override
	public void collection() {
		String url = "http://orientaldaily.on.cc/cnt/news/";
		try {
			List<News> newsList = new ArrayList<News>();
			Pattern p = Pattern.compile("\\d{8}");
			Document doc = getConnection(url).get();
			Elements eles = doc.select("#articleList .commonBigList li");
			for (Element ele : eles) {
				String href = "http://orientaldaily.on.cc" + ele.select("a").attr("href");
				
				String pubDate = null;
				Matcher m = p.matcher(href);
				if(m.find()){
					pubDate = m.group();
				}else{
					pubDate = DateUtil.fmtDt(new Date(), "yyyyMMdd");
				}
				//判断新闻是否过期
				if(isOverdue(pubDate, "yyyyMMdd")){
					System.out.println("---新闻过期了跳过---");
					continue;
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
					String title = ele.select("a").text();
					//转换格式
					pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate, "yyyyMMdd"), timeFormat);
					String context = descDoc.select("#pageCTN p").text();
					String desc = null;
					if (context.length() > 80) {
						desc = context.substring(0, 80) + "...";
					} else {
						desc = context;
					}
					News news = getNewsBean(title, pubDate, SourceInfo.DFRB_YWGW, "0", desc, context, href);
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
