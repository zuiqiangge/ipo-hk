package com.pengyue.ipo.collection;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.pengyue.ipo.bean.CollectionClass;
import com.pengyue.ipo.collection.impl.Catcat;
import com.pengyue.ipo.collection.impl.Discuss;
import com.pengyue.ipo.collection.impl.Fail;
import com.pengyue.ipo.collection.impl.Hkgolden;
import com.pengyue.ipo.collection.impl.Uwants;
import com.pengyue.ipo.service.collectionclass.CollectionClassService;
import com.pengyue.ipo.service.news.NewsService;
import com.pengyue.ipo.service.news.ReportService;
import com.pengyue.ipo.service.system.CollectSevice;

@Component
public class Task implements ApplicationContextAware {
	
	@Autowired
	private static CollectionClassService collectionClassService;
	
	//注入
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		collectionClassService = context.getBean("collectionClassService",CollectionClassService.class);
		//为采集类的抽象父类设置service值
		CollectionNews.setNewsService(context.getBean("newsService", NewsService.class));
		CollectionNews.setReportService(context.getBean("reportService", ReportService.class));
		CollectionNews.setCollectSevice(context.getBean("collectSevice", CollectSevice.class));
	}
	
	public void startTask(){
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "9666");
		try {
			List<CollectionClass> cList =  collectionClassService.findAllEnable();
			
			//创建一个指定长线程池
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
			for (CollectionClass c : cList) {
				Class<?> clazz = Class.forName(c.getClassName());
				Constructor<?> con = clazz.getConstructor();
				CollectionNews coll = (CollectionNews)con.newInstance();
				//启动线程
				fixedThreadPool.execute(coll);
			}
			fixedThreadPool.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startForumTask(){
		System.setProperty("http.proxyHost", "127.0.0.1");
		System.setProperty("http.proxyPort", "9666");
 
		try {
			//创建一个指定长线程池
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
			//启动线程
			fixedThreadPool.execute(new Discuss());
			fixedThreadPool.execute(new Hkgolden());
			fixedThreadPool.execute(new Catcat());
			fixedThreadPool.execute(new Fail());
			fixedThreadPool.execute(new Uwants());
			fixedThreadPool.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
