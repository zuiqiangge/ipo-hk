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
 * 大公网--中国新闻
 * @author admin
 *
 */
public class DgwZgxw extends CollectionNews {
	
	@Override
	public void collection() {
		String url = "http://news.takungpao.com/mainland/focus/";
		String nextUrl = null;
		try {
			Document doc = null;
			do {
				List<News> newsList = new ArrayList<News>();
				if (nextUrl == null) {
					doc = getConnection(url).get();
				}else{
					doc = getConnection(nextUrl).get();
				}
				Elements eles = doc.select(".m_txt_news>ul>li");
				for (Element ele : eles) {
					String pubDate = ele.select(".a_time").text().trim();
					//判断新闻是否过期
					if(isOverdue(pubDate,"yyyy-MM-dd")){
						System.out.println("---新闻过期了跳过---");
						break;
					}

					String href = ele.select(".a_title").attr("href");
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
						pubDate = DateUtil.fmtDt(
								DateUtil.parse(pubDate, "yyyy-MM-dd"),
								timeFormat);
						String title = descDoc.select(".tpk_con_tle").text();
						String context = descDoc.select(".tpk_text").text();
						String desc = null;
						if (context.length() > 80) {
							desc = context.substring(0, 80) + "...";
						} else {
							desc = context;
						}
						News news = getNewsBean(title, pubDate, SourceInfo.DGW_ZGXW, "0", desc, context, href);
						//添加到List
						newsList.add(news);
					}
				}
				if (newsList != null && newsList.size() > 0) {
					//保存数据库
					getNewsService().batchInsert(newsList);
				}else{
					break; //当前页没有采集到今日新闻后面也就不需要采集了直接跳出
				}
				
				int currPage = Integer.parseInt(doc.select("#displaypagenum>center>span").last().text().trim());
				int total = Integer.parseInt(doc.select("#displaypagenum>center>.a1").first().text().replace("条", ""));
				int maxPage = 0;
				if((total % 95) == 0){
					maxPage = total / 95;
				}else{
					maxPage = (total / 95) + 1;
				}
				if(currPage >= maxPage){
					break;//到达最后一页退出
				}
				
				nextUrl = doc.select("#displaypagenum>center>.a1").last().attr("href");
			} while (nextUrl != null && nextUrl.trim().length() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
