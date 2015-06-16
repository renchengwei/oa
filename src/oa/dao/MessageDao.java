package oa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oa.pojo.Message;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("messageDao")
public class MessageDao extends BaseDao {

	public int getMessageCount(Map<String, String> map) {
		String hql = "select count(id) from Message where isdel='0'";
		List<String> params = new ArrayList<String>();
		
		if(StringUtils.isNotEmpty(map.get("content"))) {
			hql += " and content like ?";
			params.add("%" + map.get("content") + "%");
		}
		
		if(StringUtils.isNotEmpty(map.get("receiveusers"))) {
			hql += " and receiveusers like ?";
			params.add("%" + map.get("receiveusers") + "%");
		}
		return this.queryCountByHql(hql, params);
	}

	public List<Message> getMessageByPage(Map<String, String> map) {
		String hql = "from Message where isdel='0'";
		List<String> params = new ArrayList<String>();
		
		if(StringUtils.isNotEmpty(map.get("content"))) {
			hql += " and content like ?";
			params.add("%" + map.get("content") + "%");
		}
		
		if(StringUtils.isNotEmpty(map.get("receiveusers"))) {
			hql += " and receiveusers like ?";
			params.add("%" + map.get("receiveusers") + "%");
		}
		
		hql += "order by sendtime desc,id";
		
		int pageNo=Integer.parseInt(map.get("page").toString());
		int maxResult=Integer.parseInt(map.get("rows").toString());
		
		List<Message> list = this.queryForPage(hql, params, pageNo, maxResult);
		return list;
	}
}
