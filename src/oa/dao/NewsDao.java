package oa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oa.pojo.News;
import oa.pojo.User;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("newsDao")
public class NewsDao extends BaseDao {

	public int getNewsCount(Map<String, String> map) {
		String hql = "select count(id) from News where isdel='0' ";
		List<String> params = new ArrayList<String>();
		if(StringUtils.isNotEmpty(map.get("title"))) {
			hql += " and title like ?";
			params.add("%" + map.get("title") + "%");
		}
		return this.queryCountByHql(hql, params);
	}

	public List<News> getNewsByPage(Map<String, String> map) {
		String hql = "select n,u from News n,User u where n.isdel='0' and n.issueuser=u.id";
		List<String> params = new ArrayList<String>();
		if(StringUtils.isNotEmpty(map.get("title"))) {
			hql += " and n.title like ?";
			params.add("%" + map.get("title") + "%");
		}
		
		hql += " order by n.issuetime desc,n.id";
		
		int pageNo=Integer.parseInt(map.get("page").toString());
		int maxResult=Integer.parseInt(map.get("rows").toString());
		
		List<Object[]> list = this.queryForPage(hql, params, pageNo, maxResult);
		
		List<News> newsList = new ArrayList<News>();
		for(Object[] obj : list) {
			News news = (News) obj[0];
			User user = (User) obj[1];
			news.setIssueuserBean(user);
			newsList.add(news);
		}
		return newsList;
	}

}
