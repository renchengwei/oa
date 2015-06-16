package oa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oa.dao.TaskDao;
import oa.pojo.SMS;
import oa.pojo.Task;
import oa.pojo.TaskProgress;
import oa.pojo.User;
import oa.util.BeanCopyUtil;
import oa.util.DateUtil;
import oa.util.SessionUtil;
import oa.util.StaticCodeBook;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


@Component("taskService")
public class TaskService {

	@Resource(name="taskDao")
	private TaskDao dao;

	public TaskDao getDao() {
		return dao;
	}

	public void setDao(TaskDao dao) {
		this.dao = dao;
	}

	/**
	 * 保存任务
	 * @param map
	 * @return
	 */
	public Task saveTask(Map<String, String> map) {
		Task task = new Task();
		BeanCopyUtil.setFieldValue(task, map);
		
		if(StringUtils.isNotEmpty(task.getId())) {
			dao.updateObject(task);
		}else {
			task.setStatus("0");
			task.setIssueuser(SessionUtil.getUser().getId());
			task.setIssuetime(DateUtil.getDateTime(new Date()));
			dao.saveObject(task);
			//短信通知
			if("1".equals(task.getIsmsg())) {
				User user = (User) dao.get(User.class, task.getTransactor());
				SMS sms = new SMS();
				sms.setPhonenum(user.getMobile());
				sms.setStatus("0");
				sms.setContent("您有一份待办任务，标题：" + task.getTitle() + "，请注意查收!");
				sms.setSendtime(DateUtil.getDateString2(new Date()));
				sms.setSenduser(SessionUtil.getUser().getId());
				dao.saveObject(sms);
			}
		}
		return task;
	}

	/**
	 * 分页查询下发任务
	 * @param map
	 * @return
	 */
	public Map<String, Object> getSendTasksByPage(Map<String, String> map) {
		
		map.put("issueuser", SessionUtil.getUser().getId());
		
		Map<String , Object> result = new HashMap<String, Object>();
		List<Task> tasklist = new ArrayList<Task>();
		
		int count = dao.getTaskCount(map);
		if(count > 0) {
			tasklist = dao.getTasksByPage(map);
		}
		
		result.put(StaticCodeBook.PAGE_TOTAL, count);
		result.put(StaticCodeBook.PAGE_ROWS, tasklist);
		return result;
	}

	/**
	 * 添加工作进度
	 * @param map
	 * @return
	 */
	public TaskProgress addTaskProgress(Map<String, String> map) {
		TaskProgress progress = new TaskProgress();
		BeanCopyUtil.setFieldValue(progress, map);
		
		progress.setWriteuser(SessionUtil.getUser().getId());
		progress.setWritetime(DateUtil.getDateTime(new Date()));
		dao.saveObject(progress);
		return progress;
	}
	
	/**
	 * 根据ID查询任务信息
	 * @param id
	 * @return
	 */
	public Task getTaskById(String id,String flag) {
		Task task = dao.getTaskById(id);
		if(task != null) {
			List<TaskProgress> progresslist = dao.getTaskProgressByTask(id);
			task.setProgresslist(progresslist);
			//待办的任务改为办理中
			if("receive".equals(flag) && "0".equals(task.getStatus())) {
				task.setStatus("1");
				dao.updateObject(task);
			}
		}
		return task;
	}

	/**
	 * 下发任务
	 * @param id
	 * @return
	 */
	public Task getdistributionTask(String id) {
		Task task = (Task) dao.get(Task.class, id);
		
		Task newTask = new Task();
		if(task != null) {
			newTask.setTitle(task.getTitle());
			newTask.setContent(task.getContent());
			newTask.setEndtime(task.getEndtime());
			newTask.setLevel(task.getLevel());
			newTask.setIsmsg("1");
		}
		return newTask;
	}

	/**
	 * @param map
	 * @return
	 */
	public Map<String, Object> getReceiveTasksByPage(Map<String, String> map) {
		map.put("transactor", SessionUtil.getUser().getId());
		
		Map<String , Object> result = new HashMap<String, Object>();
		List<Task> tasklist = new ArrayList<Task>();
		
		int count = dao.getTaskCount(map);
		if(count > 0) {
			tasklist = dao.getTasksByPage(map);
		}
		
		result.put(StaticCodeBook.PAGE_TOTAL, count);
		result.put(StaticCodeBook.PAGE_ROWS, tasklist);
		return result;
	}

	/**
	 * 任务办理完成
	 * @param id
	 * @return
	 */
	public Task updateTaskOK(String id) {
		Task task = (Task) dao.get(Task.class, id);
		task.setStatus("2");
		dao.updateObject(task);
		//添加任务进度
		Map<String, String> map = new HashMap<String, String>();
		map.put("taskid", task.getId());
		map.put("content", "<p>任务办理完成</p>");
		this.addTaskProgress(map);
		return task;
	}

	/**
	 * 重新办理任务
	 * @param map
	 * @return
	 */
	public Task updateTaskAgainDo(Map<String, String> map) {
		Task task = (Task) dao.get(Task.class, map.get("taskid"));
		task.setStatus("1");
		dao.updateObject(task);
		//添加任务进度
		map.put("content", "<p>该任务需要重新办理，具体原因如下：</p>" + map.get("content"));
		addTaskProgress(map);
		return task;
	}
	
	/**
	 * 任务办理完结
	 * @param map
	 * @return
	 */
	public Task updateTaskFinish(String id) {
		Task task = (Task) dao.get(Task.class, id);
		task.setStatus("3");
		dao.updateObject(task);
		//添加任务进度
		Map<String,String> map = new HashMap<String, String>();
		map.put("taskid", id);
		map.put("content", "<p>任务办理完结</p>");
		addTaskProgress(map);
		return task;
	}

	public int getShowTaskCount() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("transactor", SessionUtil.getUser().getId());
		map.put("status", "0");
		return dao.getTaskCount(map);
	}
}
