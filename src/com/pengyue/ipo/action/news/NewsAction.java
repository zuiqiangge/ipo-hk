package com.pengyue.ipo.action.news;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.bean.Report;
import com.pengyue.ipo.bean.ReportAddress;
import com.pengyue.ipo.bean.ReportRecord;
import com.pengyue.ipo.collection.Task;
import com.pengyue.ipo.service.news.NewsService;
import com.pengyue.ipo.service.news.ReportAddressService;
import com.pengyue.ipo.service.news.ReportRecordService;
import com.pengyue.ipo.service.news.ReportService;
import com.pengyue.ipo.service.news.TbXtNewsService;

@Controller
@Scope("prototype")
public class NewsAction extends ActionSupport {
	
	@Autowired
	private ReportService reportService;
	@Autowired
	private ReportAddressService reportAddressService;
	@Autowired
	private TbXtNewsService tbXtNewsService;
	@Autowired
	private ReportRecordService reportRecordService;
	@Autowired
	private NewsService newsService;
	
	private List<Report> reportList;
	private List<ReportAddress> addrList;
	private String newsId;
	private ReportRecord record;
	private String zlm;
	
	/**
	 * 导出报告
	 * @return
	 */
	public String reportAddr(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("newsId", newsId);
		params.put("reportId", record.getReportId());
		params.put("addressCode", record.getAddressCode());
		boolean bl = false;
		String msg = null;
		if(!reportRecordService.existsInThePlate(params)){
			News news = tbXtNewsService.solrFindNewsById(newsId);
			record.setNewsId(news.getId());
			record.setTitle(news.getTitle());
			record.setNewsDate(news.getPubnisihTime_sj());
			record.setFname(news.getFname());
			record.setClickedCounts(news.getClickedCounts());
			record.setZlm(news.getZlm());
			record.setPubnisihUser(news.getPubnisihuser());
			try {
				reportRecordService.saveReportRecord(record);
				bl = true;
			} catch (Exception e) {
				e.printStackTrace();
				bl = false;
				msg = "出现错误保存失败";
			}
		}else{
			msg = "该新闻在板块和简报中已存在！";
		}
		responseJSON("{\"result\":"+bl+",\"msg\":\""+msg+"\"}");
		return null;
	}
	
	
	/***
	 * 倾向标注
	 * @return
	 */
	public void changeZLM(){
		HttpServletResponse response = ServletActionContext.getResponse();
		try{
		newsService.changeNewsZLM(newsId, zlm);
		tbXtNewsService.changeSorlZLM(newsId, zlm);
		PrintWriter out = response.getWriter();
		out.write("1");
		out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	} 
	/**
	 * 响应json
	 * @param obj
	 */
	private void responseJSON(Object obj){
        /* 获得response */
        HttpServletResponse response = ServletActionContext.getResponse();
        /* 设置格式为text/json    */
        response.setContentType("text/json");
        /*设置字符集为'UTF-8'*/
        response.setCharacterEncoding("UTF-8"); 
		try {
			JSONObject json = JSONObject.fromObject(obj);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	/**
//	 * 测试采集
//	 * @return
//	 */
//	public String test(){
//		Task task = new Task();
//		task.startTask();
//		return null;
//	}

	
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public List<Report> getReportList() {
		return reportList;
	}
	public void setReportList(List<Report> reportList) {
		this.reportList = reportList;
	}
	public List<ReportAddress> getAddrList() {
		return addrList;
	}
	public void setAddrList(List<ReportAddress> addrList) {
		this.addrList = addrList;
	}
	public ReportRecord getRecord() {
		return record;
	}
	public void setRecord(ReportRecord record) {
		this.record = record;
	}


	public String getZlm() {
		return zlm;
	}


	public void setZlm(String zlm) {
		this.zlm = zlm;
	}
	
}
