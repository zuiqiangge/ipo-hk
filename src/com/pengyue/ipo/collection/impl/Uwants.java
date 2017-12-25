package com.pengyue.ipo.collection.impl;

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

public class Uwants extends CollectionNews{

	@Override
	public void collection() {
		String url="http://www.uwants.com/forumdisplay.php?fid=179";
		try {
			Document doc=this.getConnection(url).get();
			int pageNo=1;
			int colCount=0;
			String rootUrl="http://www.uwants.com/";
			List<TbXtCollect> colList=new ArrayList<TbXtCollect>();
			List<TbXtReplay> repList=new ArrayList<TbXtReplay>();
			boolean isNextPost=false;
			do {
				System.out.println(DateFormat.nowDate24()+"开始在uwants论坛采集第"+pageNo+"页内容");
				Element form=doc.getElementsByAttributeValue("name","moderate").get(0);
				Elements tbodys=form.getElementsByTag("tbody");
				for (Element tbody : tbodys) {
					try {
						Elements  thTags=tbody.getElementsByTag("th");
						if (thTags.size()==0) {
							continue;
						}
						Elements spanTags=thTags.get(0).getElementsByTag("span");
						if (spanTags.size()==0) {
							continue;
						}
						Element aTag=spanTags.get(0).select("a[href]").get(0);
						String hrefUrl=aTag.attr("href");
						String postUrl=rootUrl+hrefUrl;
						//去掉后面页码无用参数
						postUrl=postUrl.substring(0,postUrl.indexOf("&extra"));
						String title=aTag.text();
						String clickedCounts=tbody.select(".nums>em").text();
						//查询此链接是否已存在
						if (getCollectSevice().isExist(postUrl)) {
							continue;
						}
						TbXtCollect collect=new TbXtCollect();
						collect.setCollecttime(DateFormat.nowDate24());
						collect.setEtlgxsj(DateFormat.nowDate24());
						collect.setSourceid(SourceInfo.uwants);
						collect.setUrl(postUrl);
						collect.setTitle(title);
						Document dmt=this.getConnection(postUrl).get();
						boolean isValid=false;		//该帖是否有效
						boolean is_first_floor=true;//是否一楼
						boolean isNextReply=false;	//是否有下一页回复
						do {
							Elements floors=dmt.select(".viewthread");
							if (floors.size()!=0) {
								String contextFirst=floors.get(0).select(".t_msgfont").select("span").text();
								if (!contextFirst.equals("")) {
									isValid=true;
								}
							}
							for (Element floor : floors) {
								Elements context=floor.select(".t_msgfont").select("span");
								if (!context.text().equals("")&&context!=null) {
									String author=floor.select("cite>a").text();
									String dateStr="";
									//匹配2016-11-20 06:48 AM
									Date newDate=new Date();
									SimpleDateFormat sdfResult=new SimpleDateFormat("yyyyMMddHHmmss");
									Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2} [0-9]{1,2}[:][0-9]{1,2} [a,p,A,P][m,M]"); 
								    Matcher matcher = pattern.matcher(floor.select(".postinfo").html()); 
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
										collect.setContext(context.text());
										collect.setPubnisihuser(author);
										collect.setPubnisihtime(dateStr);
										collect.setTytime(dateStr);
										collect.setClickedcounts(clickedCounts);
										is_first_floor=false;
									}else {
										//回复的内容
										TbXtReplay replay=new TbXtReplay();
										replay.setEtlgxsj(DateFormat.nowDate24());
										replay.setForumid(collect.getId());
										replay.setContext(context.text());
										replay.setReplayuser(author);
										replay.setReplaytime(dateStr);
									    repList.add(replay);
									}
								}else {
									continue;
								}
							}
							Elements nextRepHrefs=dmt.select(".next");
							if (nextRepHrefs.size()>0) {
								String nextPageHref=nextRepHrefs.get(0).attr("href");
								dmt=this.getConnection(rootUrl+nextPageHref).get();
								isNextReply=true;
							}else {
								isNextReply=false;
							}
						} while (isNextReply);
						if (isValid) {
							collect.setReplaycounts(repList.size()+"");
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
				if (pageNo>=20) {
					return;
				}
				//开始采集下一页
				Elements nextPostHrefs=doc.select(".next");
				if (nextPostHrefs.size()>0) {
					String nextPage=nextPostHrefs.get(0).attr("href");
					doc=this.getConnection(rootUrl+nextPage).get();
					isNextPost=true;
					pageNo++;
				}else {
					isNextPost=false;
				}
			} while (isNextPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
