package com.pengyue.ipo.collection.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

public class Catcat extends CollectionNews{

	@Override
	public void collection() {
		String url="http://catcatforum.com/forum.php?mod=forumdisplay&fid=2138";
		try {
			Document doc=getConnection(url).get();
			int pageNo=1;
			int colCount=0;
			List<TbXtCollect> colList=new ArrayList<TbXtCollect>();
			List<TbXtReplay> repList=new ArrayList<TbXtReplay>();
			boolean isNextPost=false;
			do {
				System.out.println(DateFormat.nowDate24()+"开始在catcat论坛采集第"+pageNo+"页内容");
				Element form=doc.getElementsByAttributeValue("name","moderate").get(0);
				Elements tbodys=form.getElementsByTag("tbody");
				for (Element tbody : tbodys) {
					try {
						Elements  xstClass=tbody.getElementsByClass("xst");
						if (xstClass.size()==0) {
							continue;
						}
						Element aTag=xstClass.get(0).select("a[href]").get(0);
						String postUrl=aTag.attr("href");
						//去掉后面页码无用参数
						postUrl=postUrl.substring(0,postUrl.indexOf("&extra"));
						String title=aTag.text();
						//查询此链接是否已存在
						if (getCollectSevice().isExist(postUrl)) {
							continue;
						}
						TbXtCollect collect=new TbXtCollect();
						collect.setCollecttime(DateFormat.nowDate24());
						collect.setEtlgxsj(DateFormat.nowDate24());
						collect.setSourceid(SourceInfo.catcat);
						collect.setUrl(postUrl);
						collect.setTitle(title);
						Document dmt=getConnection(postUrl).get();
						boolean isValid=true;		//该帖是否有效
						boolean is_first_floor=true;//是否一楼
						boolean isNextReply=false;	//是否有下一页回复
						do {
							Elements postlist=dmt.select("#postlist");
							if (postlist.size()==0) {
								isValid=false;
								break;
							}
							Elements floors=postlist.get(0).select("div>table.plhin>tbody");
							for (Element floor : floors) {
								String context=floor.select(".t_f").text();
								if (context!=null&&!context.equals("")) {
									String author=floor.select(".xw1").get(0).text();
									String dateStr="";
									//匹配2016-11-20 06:48:05
									Date newDate=new Date();
									SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									SimpleDateFormat sdfResult=new SimpleDateFormat("yyyyMMddHHmmss");
									Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2} [0-9]{1,2}[:][0-9]{1,2}[:][0-9]{1,2}"); 
								    Matcher matcher = pattern.matcher(floor.select("div.authi>em").html()); 
								    if(matcher.find()){ 
								      dateStr = matcher.group(0);
								      newDate=sdf.parse(dateStr);
								    }
								    dateStr=sdfResult.format(newDate);
									if (is_first_floor) {
										//楼主的内容
										collect.setContext(context);
										collect.setPubnisihuser(author);
										collect.setPubnisihtime(dateStr);
										collect.setTytime(dateStr);
										Elements xi1=postlist.get(0).getElementsByTag("table").get(0).getElementsByClass("xi1");
										collect.setClickedcounts(xi1.get(0).text());
										collect.setReplaycounts(xi1.get(1).text());
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
							Elements nextRepHrefs=dmt.select(".nxt");
							if (nextRepHrefs.size()>0) {
								String nextPageHref=nextRepHrefs.get(0).attr("href");
								dmt=getConnection(nextPageHref).get();
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
					col.setZlm(this.getZlm(col.getContext()));
				}
				getCollectSevice().saveData(colList,repList);
				colList.clear();
				repList.clear();
				System.out.println(DateFormat.nowDate24()+"保存了"+colCount+"条帖子内容");
				//开始采集下一页
				String nextPage=doc.select(".nxt").attr("href");
				if (nextPage!=null&&!nextPage.equals("")) {
						doc=getConnection(nextPage).get();
					isNextPost=true;
					pageNo++;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
				}else {
					isNextPost=false;
				}
			} while (isNextPost);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
