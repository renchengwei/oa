package oa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oa.pojo.Post;
import oa.pojo.PostAndReply;
import oa.pojo.User;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("postDao")
public class PostDao extends BaseDao{

	public int getPostCount(Map<String, String> map) {
		String hql = "select count(id) from Post where isdel='0' ";
		List<String> params = new ArrayList<String>();
		
		if(StringUtils.isNotEmpty(map.get("title"))) {
			hql += " and title like ?";
			params.add("%" + map.get("title") + "");
		}
		
		if(StringUtils.isNotEmpty(map.get("type"))) {
			hql += " and type = ?";
			params.add(map.get("type"));
		}
		
		return this.queryCountByHql(hql, params);
	}

	public List<Post> getPostByPage(Map<String, String> map) {
		String hql = "select p,u from Post p,User u where p.isdel='0' and p.issueuser=u.id ";
		List<String> params = new ArrayList<String>();
		
		if(StringUtils.isNotEmpty(map.get("title"))) {
			hql += " and title p.like ?";
			params.add("%" + map.get("title") + "");
		}
		
		if(StringUtils.isNotEmpty(map.get("type"))) {
			hql += " and p.type = ?";
			params.add(map.get("type"));
		}
		
		hql += " order by p.toptime desc,p.issuetime desc,p.id";
		
		int pageNo=Integer.parseInt(map.get("page").toString());
		int maxResult=Integer.parseInt(map.get("rows").toString());
		
		List<Object[]> list = this.queryForPage(hql, params, pageNo, maxResult);
		
		List<Post> postlist = new ArrayList<Post>();
		
		if(list != null && list.size() > 0) {
			for(Object[] obj : list) {
				Post post = (Post) obj[0];
				User user = (User) obj[1];
				post.setIssueuserbean(user);
				postlist.add(post);
			}
		}
		return postlist;
	}

	public int getPostAndReplyCount(Map<String, String> map) {
		String hql = "select count(id) from PostAndReply where postid=?";
		List<String> params = new ArrayList<String>();
		params.add(map.get("id"));
		return this.queryCountByHql(hql, params);
	}

	public List<PostAndReply> getPostAndReplyByPage(Map<String, String> map) {
		String hql = "select par,u from PostAndReply par,User u where par.issueuser=u.id and par.postid=?";
		List<String> params = new ArrayList<String>();
		params.add(map.get("id"));
		hql += " order by par.ordernum,par.issuetime";
		int pageNo=Integer.parseInt(map.get("page").toString());
		int maxResult=Integer.parseInt(map.get("rows").toString());
		List<Object[]> list = this.queryForPage(hql, params, pageNo, maxResult);
		
		List<PostAndReply> parList = new ArrayList<PostAndReply>();
		if(list != null && list.size() > 0) {
			for(Object[] obj : list) {
				PostAndReply par = (PostAndReply) obj[0];
				User user = (User) obj[1];
//				if(obj[2] != null) {
//					PostReply pr = (PostReply) obj[2];
//					par.setReplyben(pr);
//				}
				par.setIssueuserbean(user);
				parList.add(par);
			}
		}
		return parList;
	}

	public int getPostReplyOrderNum(String postid) {
		String hql = "select max(ordernum) from PostReply where postid=?";
		List<String> params = new ArrayList<String>();
		params.add(postid);
		List<Integer> list = this.query(hql, params);
		if(list != null && list.get(0) != null) {
			return list.get(0);
		}
		return 0;
	}

}
