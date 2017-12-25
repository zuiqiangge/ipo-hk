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
 * 苹果日报--即时本地新闻
 * @author admin
 *
 */
public class PgrbJsbdxw extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "http://hk.apple.nextmedia.com/realtime/realtimelist/news?page=news";
		try {
			List<News> newsList = new ArrayList<News>();
			Document doc = getConnection(url).get();
			Elements eles = doc.select("#rtSlider li>div[class^=RTitem]");
			
			for (Element ele : eles) {
				String pubDate = ele.select(".date").text().trim();
				String pubTime = ele.select(".time").text().trim();
				//判断新闻是否过期
				if(isOverdue(pubDate, "yyyyMMdd")){
					System.out.println("---新闻过期了跳过---");
					continue;
				}
				
				String href = ele.select(".text a").attr("href");
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
					pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate+pubTime, "yyyyMMddHH:mm"),timeFormat);
					String title = ele.select(".text a").text();
					String viewNum = ele.select(".view02").text().replace(",", "");
					if(viewNum.trim().length() == 0){
						viewNum = "0";
					}
					String context = descDoc.select(".ArticleContent_Inner").text();
					String desc = null;
					if (context.length() > 80) {
						desc = context.substring(0, 80) + "...";
					} else {
						desc = context;
					}
					News news = getNewsBean(title, pubDate, SourceInfo.PGRB_JSBDXW, viewNum, desc, context, href);
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
