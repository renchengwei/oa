package oa.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oa.pojo.Dictionary;
import oa.pojo.Task;
import oa.pojo.TaskProgress;
import oa.pojo.User;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("taskDao")
public class TaskDao extends BaseDao {

	public int getTaskCount(Map<String, String> map) {
		String hql = "select count(id) from Task t where 1=1";
		List<String> params = new ArrayList<String>();
		String ret = setParams(map, params);
		hql += ret;
		int count = this.queryCountByHql(hql, params);
		return count;
	}

	public List<Task> getTasksByPage(Map<String, String> map) {
		
		String hql = "select t,u1,u2,dict.name from Task t,User u1,User u2,Dictionary dict where t.issueuser=u1.id and t.transactor=u2.id and t.level=dict.id";
		List<String> params = new ArrayList<String>();
		String ret = setParams(map, params);
		hql += ret + " order by t.status,t.issuetime desc,t.id";
		
		int pageNo=Integer.parseInt(map.get("page").toString());
		int maxResult=Integer.parseInt(map.get("rows").toString());
		
		List<Object[]> list = this.queryForPage(hql, params, pageNo, maxResult);
		
		List<Task> tasklist = new ArrayList<Task>();
		
		if(list != null && list.size() > 0) {
			for(Object[] obj : list) {
				Task task = (Task) obj[0];
				User issueuserbean = (User) obj[1];
				User transactorbean = (User) obj[2];
				String leavelname = (String) obj[3];
				task.setIssueuserbean(issueuserbean);
				task.setTransactorbean(transactorbean);
				task.setLevelname(leavelname);
				tasklist.add(task);
			}
		}
		
		return tasklist;
	}
	
	private String setParams(Map<String, String> map,List<String> params) {
		String ret = "";
		if(StringUtils.isNotEmpty(map.get("title"))) {
			ret += " and t.title like ?";
			params.add("%" + map.get("title") + "%");
		}
		if(StringUtils.isNotEmpty(map.get("issueuser"))) {
			ret += " and t.issueuser=?";
			params.add(map.get("issueuser"));
		}
		if(StringUtils.isNotEmpty(map.get("status"))) {
			ret += " and t.status=?";
			params.add(map.get("status"));
		}
		if(StringUtils.isNotEmpty(map.get("transactor"))) {
			ret += " and t.transactor=?";
			params.add(map.get("transactor"));
		}
		if(StringUtils.isNotEmpty(map.get("level"))) {
			ret += " and t.level=?";
			params.add(map.get("level"));
		}
		return ret;
	}

	public List<TaskProgress> getTaskProgressByTask(String id) {
		String hql = "select tp,u.name from TaskProgress tp,User u where tp.writeuser=u.id and tp.taskid=? order by tp.writetime,tp.id";
		List<String> params = new ArrayList<String>();
		params.add(id);
		List<Object[]> list = this.query(hql, params);
		List<TaskProgress> progresslist = new ArrayList<TaskProgress>();
		if(list != null && list.size() > 0) {
			for(Object[] obj : list) {
				TaskProgress progress = (TaskProgress) obj[0];
				String writeusername = (String) obj[1];
				progress.setWriteusername(writeusername);
				progresslist.add(progress);
			}
		}
		return progresslist;
	}

	public Task getTaskById(String id) {
		String hql = "select t,u1,u2,dict.name from Task t,User u1,User u2,Dictionary dict where t.issueuser=u1.id and t.transactor=u2.id and t.level=dict.id and t.id=?";
		List<String> params = new ArrayList<String>();
		params.add(id);
		List<Object[]> list = this.query(hql, params);
		if(list != null && list.size() > 0) {
			Object[] obj = list.get(0);
			Task task = (Task) obj[0];
			User user1 = (User) obj[1];
			User user2 = (User) obj[2];
			String levelname = (String) obj[3];
			task.setTransactorbean(user2);
			task.setIssueuserbean(user1);
			task.setLevelname(levelname);
			return task;
		}
		return null;
	}

}
