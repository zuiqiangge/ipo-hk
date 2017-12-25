package com.pengyue.ipo.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pengyue.ipo.action.system.SearchTools;
import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.bean.TbXtTask;
import com.pengyue.ipo.collection.Task;
import com.pengyue.ipo.service.news.TbXtNewsService;

public class NesMangerJob {
	/**
	 * @ 定时扫描帖子任务
	 */
	@Autowired
	private TbXtNewsService TbXtNewsService;

	public void checkNeedsUpdate() {
		// System.out.println("NesMangerJob启动........");

		// 获取所有需要运行的Job
		// List<News> newsList = TbXtNewsService.selectNewsNeedUpdate();
		List<String> sourceIdList = TbXtNewsService.selectSourceList();
		if (sourceIdList != null && sourceIdList.size() > 0) {
			for (int i = 0; i < sourceIdList.size(); i++) {
				try {
					// 服务器内存小，这边处理下，防止内存溢出，500条索引一次
					int needNewscounts = TbXtNewsService
							.selectNewsNeedcountUpdateBySource(sourceIdList
									.get(i));
					int rowsPerPage = 200;
					int maxPage = (int) Math.ceil((needNewscounts + 0.0)
							/ rowsPerPage);

					if (maxPage > 0) {
						for (int k = 0; k < maxPage; k++) {
							int start = k * rowsPerPage + 1; // 起始
							int end = k * rowsPerPage + rowsPerPage; // 结束
							Map<String, Object> params = new HashMap<String, Object>();

							params.put("start", start);
							params.put("end", end);
							params.put("sourceid", sourceIdList.get(i));
							List<News> newsList = TbXtNewsService
									.selectNewsNeedUpdateBySource(params);
							SearchTools.createIndexNews(newsList);
						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		// System.out.println("job启动");
		// List<News> newsList =
		// TbXtNewsService.selectNewsNeedUpdateBySource("15");
		// SearchTools.createIndexNews(newsList);

	}

	public static void main(String[] args) {
		System.out.println((int) Math.ceil((501 + 0.0) / 500));
	/*	System.out.println("collectNews启动........");
		Task task = new Task();
		task.startTask();*/
	}

	public void collectNews() {
		 System.out.println("collectNews启动........");
		 Task task = new Task();
		 task.startTask();
	
	}

	public void collectfourms() {
		 System.out.println("collectfourms启动........");
		 Task task = new Task();
		 task.startForumTask();
	}

}
