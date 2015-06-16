package oa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oa.dao.NewsDao;
import oa.pojo.News;
import oa.pojo.User;
import oa.util.BeanCopyUtil;
import oa.util.DateUtil;
import oa.util.SessionUtil;
import oa.util.StaticCodeBook;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("newsService")
public class NewsService {

	@Resource(name="newsDao")
	private NewsDao newsDao;

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public News saveNews(Map<String, String> map) {
		News news = new News();
		if(StringUtils.isNotEmpty(map.get("id"))) {
			news = (News) newsDao.get(News.class, map.get("id"));
		}
		BeanCopyUtil.setFieldValue(news, map);
		if(StringUtils.isNotEmpty(news.getId())) {
			newsDao.updateObject(news);
		}else {
			news.setIsdel("0");
			news.setIssuetime(DateUtil.getDateTime(new Date()));
			news.setIssueuser(SessionUtil.getUser().getId());
			newsDao.saveObject(news);
		}
		return news;
	}

	/**
	 * 分页查询新闻
	 * @param map
	 * @return
	 */
	public Map<String, Object> getNewsByPage(Map<String, String> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<News> newslist = new ArrayList<News>();
		int count = newsDao.getNewsCount(map);
		if(count > 0) {
			newslist = newsDao.getNewsByPage(map);
		}
		data.put(StaticCodeBook.PAGE_TOTAL, count);
		data.put(StaticCodeBook.PAGE_ROWS, newslist);
		return data;
	}

	/**
	 * 根据ID查询新闻
	 * @param id
	 * @return
	 */
	public News getNewsById(String id) {
		News news = (News) newsDao.get(News.class, id);
		User user = (User) newsDao.get(User.class, news.getIssueuser());
		news.setIssueuserBean(user);
		return news;
	}

	/**
	 * 根据ID删除新闻
	 * @param map
	 */
	public void deleteNews(String id) {
		News news = (News) newsDao.get(News.class, id);
		if(news != null) {
			news.setIsdel("1");;
			news.setDeltime(DateUtil.getDateTime(new Date()));
			news.setDeluser(SessionUtil.getUser().getId());
			newsDao.updateObject(news);
		}
	}
}
