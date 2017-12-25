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
 * 星岛日报--本地新闻
 * @author admin
 *
 */
public class XdrbBdxw extends CollectionNews {
	
	@Override
	public void collection() {
		int page = 1;
		boolean hasnext = true;
		try {
			do {
				System.out.println("-----第"+page+"页");
				String url = "http://std.stheadline.com/daily/section-list.php?page="+page+"&cat=12";
				Document doc = getConnection(url).get();
				Elements eles = doc.select(".list-content .module-wrap");
				List<News> newsList = new ArrayList<News>();
				for (Element ele : eles) {
					String pubDate = ele.select(".time").get(0).ownText().trim();
					//判断新闻是否过期
					if(isOverdue(pubDate,"yyyy-MM-dd")){
						System.out.println("---新闻过期了跳过---");
						continue;
					}
					
					String href = "http://std.stheadline.com/daily/"
							+ ele.select("a").attr("href");
					//根据url判断数据库中是否已存在
					if(!getNewsService().urlExists(href)){
						Document descDoc = null;
						try {
							descDoc = getConnection(href).get();
						} catch (Exception e) {
							System.out.println("请求详情页报错:"+e.getMessage());
							continue;
						}
						String title = ele.select(".title").text();
						String context = descDoc.select(".post-content .paragraph>p").text();
						//转换格式
						pubDate = DateUtil.fmtDt(DateUtil.parse(pubDate, "yyyy-MM-dd"),timeFormat);
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						News news = getNewsBean(title, pubDate, SourceInfo.XDRB_BDXW, "0", desc, context, href);
						newsList.add(news);
					}
				}
				
				if(newsList != null && newsList.size()>0){
					//保存数据库
					getNewsService().batchInsert(newsList);
				}
				
				//下一页
				Element nextEle = doc.select(".pagination li").last();
				if(nextEle != null && nextEle.select("a").attr("href").contains("section-list")){
					hasnext = true;
					page++;
				}else{
					hasnext = false;
				}
			} while (hasnext);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
