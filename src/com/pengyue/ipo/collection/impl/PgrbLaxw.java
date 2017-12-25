package com.pengyue.ipo.collection.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.collection.CollectionNews;
import com.pengyue.ipo.collection.SourceInfo;
import com.pengyue.ipo.util.DateUtil;

/**
 * 苹果日报--两岸新闻
 * @author admin
 *
 */
public class PgrbLaxw extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "http://hk.apple.nextmedia.com/video/videolist/"+DateUtil.fmtDt(new Date(), "yyyyMMdd")+"/chinainternational/home/0";
		try {
			List<News> newsList = new ArrayList<News>();
			Document doc = getConnection(url).get();
			Elements eles = doc.select("#vdoSlider .vdo_layer .item");
			for (Element ele : eles) {
				Document descDoc = null;
				try {
					descDoc = getConnection(ele.select("a").attr("href")).get();
				} catch (Exception e) {
					System.out.println("请求详情页报错:"+e.getMessage());
					continue;
				}
				String href = descDoc.select("meta[property=og:url]").attr("content");
				//判断url是否已存在
				if (!getNewsService().urlExists(href)) {
					Document descDoc2 = null;
					try {
						descDoc2 = getConnection(href).get();
					} catch (Exception e) {
						System.out.println("请求详情页报错:"+e.getMessage());
						continue;
					}
					String title = ele.select("a span:eq(1)").text();
					String context = descDoc2.select(".ArticleContent_Inner p").text();
					String pubDate = descDoc2.select(".SelectHdate").text().trim();
					String viewNum = ele.select(".small_view").text().replace(",", "");
					if(viewNum.trim().length() == 0){
						viewNum = "0";
					}
					//判断新闻是否过期
					if(isOverdue(pubDate,"yyyy年MM月dd日")){
						System.out.println("---新闻过期了跳过---");
						continue;
					}
					//转换日期格式
					pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate, "yyyy年MM月dd日"), timeFormat);
					
					String desc = null;
					if (context.length() > 80) {
						desc = context.substring(0, 80) + "...";
					} else {
						desc = context;
					}
					News news = getNewsBean(title, pubDate, SourceInfo.PGRB_LAXW, viewNum, desc, context, href);
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
