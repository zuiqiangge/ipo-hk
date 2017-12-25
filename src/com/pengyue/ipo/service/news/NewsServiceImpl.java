package com.pengyue.ipo.service.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pengyue.ipo.bean.News;
import com.pengyue.ipo.dao.news.NewsDao;

/**
 * 新闻采集service
 * @author wanlongquan
 *
 */
@Service("newsService")
@Transactional
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	private NewsDao newsDao;

	/**
	 * 批量插入
	 */
	@Override
	public void batchInsert(List<News> newsList) {
		//去除List中News对象的url属性重复的数据
		for (int i = 0; i < newsList.size()-1; i++) {  
		    for (int j = newsList.size()-1; j > i; j--) {  
		        if (newsList.get(j).getUrl().equals(newsList.get(i).getUrl())) {  
		        	newsList.remove(j);
		        }
		    }
		}
		System.out.println("-----newsListSize:"+newsList.size());
		newsDao.batchInsert(newsList);
	}
	
	/**
	 * url是否已存在
	 */
	@Override
	public boolean urlExists(String url) {
		int count = newsDao.findCountByUrl(url);
		if(count > 0)
			return true;
		return false;
	}
	
	public void changeNewsZLM(String id,String zlm){
		newsDao.changeNewsZLM(id, zlm);
	}
	
}
