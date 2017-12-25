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

public class Hkgolden extends CollectionNews{

	@Override
	public void collection() {
		String url="http://forum8.hkgolden.com/topics.aspx?type=CA&md=90&page=1";
		try {
			Document doc=this.getConnection(url).get();
			int pageNo=1;
			int colCount=0;
			String rootUrl="http://forum8.hkgolden.com/";
			List<TbXtCollect> colList=new ArrayList<TbXtCollect>();
			List<TbXtReplay> repList=new ArrayList<TbXtReplay>();
			boolean isNextPost=false;
			int maxPageNo=doc.select("#page>option").size();
			do {
				System.out.println(DateFormat.nowDate24()+"开始在hkgolden论坛采集第"+pageNo+"页内容");
				Element table=doc.getElementsByAttributeValue("id","mainTopicTable").get(0);
				Elements trs=table.select("tbody>tr[username]");
				for (Element tr : trs) {
					try {
						Element  aTag=tr.getElementsByTag("td").get(1).select("a[href]").get(0);
						String postUrl=rootUrl+aTag.attr("href");
						String title=aTag.text();
						String replayCounts=tr.getElementsByTag("td").get(4).text();
						//查询此链接是否已存在
						if (getCollectSevice().isExist(postUrl)) {
							continue;
						}
						TbXtCollect collect=new TbXtCollect();
						collect.setCollecttime(DateFormat.nowDate24());
						collect.setEtlgxsj(DateFormat.nowDate24());
						collect.setSourceid(SourceInfo.hkgolden);
						collect.setUrl(postUrl);
						collect.setTitle(title);
						collect.setClickedcounts("");
						collect.setReplaycounts(replayCounts);
						Document dmt=this.getConnection(postUrl).get();
						boolean isValid=false;		//该帖是否有效
						boolean is_first_floor=true;//是否一楼
						boolean isNextReply=false;	//是否有下一页回复
						do {
							Elements floors=dmt.select("tr[username]");
							for (Element floor : floors) {
								String context=floor.select(".ContentGrid").text();
								if (context!=null&&!context.equals("")) {
									isValid=true;
									String author=floor.select("td>div>a[href]").text();
									String dateStr="";
									//匹配  22/11/2016 19:02  
									SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
									SimpleDateFormat sdfResult=new SimpleDateFormat("yyyyMMddHHmmss");
									Date newDate=new Date();
									Pattern pattern = Pattern.compile("[0-9]{1,2}[/][0-9]{2}[/][0-9]{4} [0-9]{1,2}[:][0-9]{1,2}"); 
									Matcher matcher = pattern.matcher(floor.select("td>table>tbody>tr>td>div>span").html()); 
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
							String nextPageHref=dmt.select("#mainTopicTable>div>div>a[href]").attr("href");
						 	if (nextPageHref!=null&&!nextPageHref.equals("")) {
								dmt=this.getConnection(rootUrl+nextPageHref).get();
								isNextReply=true;
							}else {
								isNextReply=false;
							}
						} while (isNextReply);
						if (isValid) {
							colList.add(collect);
							for (TbXtCollect  col : colList) {
								col.setZlm(this.getZlm(col.getContext()));
							}
							getCollectSevice().saveData(colList,repList);
							colCount++;
							colList.clear();
							repList.clear();
						}
					} catch (Exception e) {
						continue;
					}
				}
				System.out.println(DateFormat.nowDate24()+"保存了"+colCount+"条帖子内容");
				if (pageNo>=20) {
					return;
				}
				//开始采集下一页
				if (pageNo!=maxPageNo) {
					pageNo++; 
					doc=this.getConnection("http://forum8.hkgolden.com/topics.aspx?type=CA&md=90&page="+pageNo).get();
					isNextPost=true;
				}else {
					isNextPost=false;
				}
			} while (isNextPost);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
