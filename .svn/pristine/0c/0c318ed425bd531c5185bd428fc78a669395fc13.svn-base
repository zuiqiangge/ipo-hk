package com.pengyue.ipo.collection.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pengyue.ipo.bean.TbXtCollect;
import com.pengyue.ipo.bean.TbXtReplay;
import com.pengyue.ipo.collection.CollectionNews;
import com.pengyue.ipo.collection.SourceInfo;
import com.pengyue.ipo.util.DateFormat;

public class Discuss extends CollectionNews{

	@Override
	public void collection() {
		String url="http://news.discuss.com.hk/index.php?gid=150";
		try {
			Document doc=this.getConnection(url).get();
			int pageNo=1;
			int colCount=0;
			String rootUrl="http://news.discuss.com.hk/";
			List<TbXtCollect> colList=new ArrayList<TbXtCollect>();
			List<TbXtReplay> repList=new ArrayList<TbXtReplay>();
			SimpleDateFormat sdfResult=new SimpleDateFormat("yyyyMMddHHmmss");
			System.out.println(DateFormat.nowDate24()+"开始在discuss论坛采集第"+pageNo+"页内容");
			Element table=doc.getElementsByAttributeValue("id","tab2").get(0);
			Elements lis=table.select("div>ul>li");
			for (Element li : lis) {
				try {
					Elements  aTag=li.select("a[title]");
					if (aTag.size()==0) {
						continue;
					}
					String postUrl=rootUrl+aTag.attr("href");
					String title=aTag.text();
					colCount++;
					//查询此链接是否已存在
					if (getCollectSevice().isExist(postUrl)) {
						continue;
					}
					TbXtCollect collect=new TbXtCollect();
					collect.setCollecttime(DateFormat.nowDate24());
					collect.setEtlgxsj(DateFormat.nowDate24());
					collect.setSourceid(SourceInfo.discuss);
					collect.setUrl(postUrl);
					collect.setTitle(title);
					Document dmt=this.getConnection(postUrl).get();
					boolean isValid=false;		//该帖是否有效
					boolean is_first_floor=true;//是否一楼
					boolean isNextReply=false;	//是否有下一页回复
					do {
						Elements floors=dmt.select(".viewthread");
						for (Element floor : floors) {
							String context=floor.select(".t_msgfont>span").text();
							if (context!=null&&!context.equals("")) {
								isValid=true;
								String author=floor.select(".postauthor>cite>a[href]").text();
								Elements infos=floor.select(".postinfo");
								String dateStr="";
								//匹配2016-11-20 06:48 AM
								Date newDate=new Date();
								Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2} [0-9]{1,2}[:][0-9]{1,2} [a,p,A,P][m,M]"); 
								 Matcher matcher = pattern.matcher(infos.html()); 
									if(matcher.find()){ 
									  dateStr = matcher.group(0);
									  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
									  if (dateStr.contains("P")) {
										  dateStr=dateStr.replace(" PM", "");
											newDate=sdf.parse(dateStr);
											 Calendar cld=Calendar.getInstance(Locale.CHINA);
											 cld.setTime(newDate);
											 if (cld.get(cld.HOUR_OF_DAY)!=12) {
												 cld.add(cld.HOUR, 12);
												 newDate=cld.getTime();
											}
									  }else {
										  dateStr=dateStr.replace(" AM", "");
										  newDate=sdf.parse(dateStr);
									  }
									}
									 dateStr= sdfResult.format(newDate);
								if (is_first_floor) {
									//楼主的内容
									collect.setContext(context);
									collect.setPubnisihuser(author);
									collect.setPubnisihtime(dateStr);
									collect.setTytime(dateStr);
									Elements number=infos.select("#viewthread-number>ul>li");
									String clickCount=number.get(0).text().split(": ")[1].replace(",", "");
									String replayCount=number.get(1).text().split(": ")[1];
									int k=clickCount.indexOf("k");
									if (k==-1) {
										collect.setClickedcounts(clickCount);
									}else {
										collect.setClickedcounts((int)(new Double(clickCount.substring(0,k))*1000)+"");
									}
									collect.setReplaycounts(replayCount);
									is_first_floor=false;
								}else {
									//回复的内容
									TbXtReplay replay=new TbXtReplay();
									replay.setEtlgxsj(DateFormat.nowDate24());
									replay.setForumid(collect.getId());
									replay.setContext(context);
									replay.setReplayuser(author);
									replay.setReplaytime(dateStr);
								    repList.add(replay);
								}
							}else {
								continue;
							}
						}
						String nextPageHref=dmt.select(".next").attr("href");
					 	if (nextPageHref!=null&&!nextPageHref.equals("")) {
							dmt=this.getConnection(rootUrl+nextPageHref).get();
							isNextReply=true;
						}else {
							isNextReply=false;
						}
					} while (isNextReply);
					if (isValid) {
						colList.add(collect);
						colCount++;
					}
				} catch (Exception e) {
					continue;
				}
			}
				for (TbXtCollect  col : colList) {
					col.setZlm(getZlm(col.getContext()));
				}
				getCollectSevice().saveData(colList,repList);
				System.out.println(DateFormat.nowDate24()+"保存了"+colCount+"条帖子内容");
				colList.clear();
				repList.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
