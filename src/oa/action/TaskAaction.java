package oa.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import oa.pojo.Task;
import oa.pojo.TaskProgress;
import oa.service.TaskService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("TaskAaction.do")
public class TaskAaction {

	private static final Logger logger = Logger.getLogger(TaskAaction.class);
	
	@Resource(name="taskService")
	private TaskService service;

	public TaskService getService() {
		return service;
	}

	public void setService(TaskService service) {
		this.service = service;
	}
	
	@RequestMapping(params="method=saveTask")
	@ResponseBody
	public Map<String, Object> saveTask(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Task task = service.saveTask(map);
			result.put("data", task);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("saveTask",e);
		}
		return result;
	}
	
	@RequestMapping(params="method=getSendTasksByPage")
	@ResponseBody
	public Map<String, Object> getSendTasksByPage(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> data = service.getSendTasksByPage(map);
			result.put("data", data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getTasksByPage",e);
		}
		return result;
	}
	@RequestMapping(params="method=getReceiveTasksByPage")
	@ResponseBody
	public Map<String, Object> getReceiveTasksByPage(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> data = service.getReceiveTasksByPage(map);
			result.put("data", data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getReceiveTasksByPage",e);
		}
		return result;
	}
	
	/**
	 * 添加工作进度
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=addTaskProgress")
	@ResponseBody
	public Map<String, Object> addTaskProgress(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			TaskProgress progress = service.addTaskProgress(map);
			result.put("data", progress);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("addTaskProgress",e);
		}
		return result;
	}
	
	/**
	 * 根据ID查询任务
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getTaskById")
	@ResponseBody
	public Map<String, Object> getTaskById(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			String flag = map.get("flag");
			Task task = service.getTaskById(id,flag);
			result.put("data", task);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getTaskById",e);
		}
		return result;
	}
	
	/**
	 * 下发任务
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=distributionTask")
	@ResponseBody
	public Map<String , Object> distributionTask(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			Task task = service.getdistributionTask(id);
			result.put("data", task);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("distributionTask",e);
		}
		
		return result;
	}
	
	/**
	 * 任务办理完成
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=updateTaskOK")
	@ResponseBody
	public Map<String , Object> updateTaskOK(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			Task task = service.updateTaskOK(id);
			result.put("data", task);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("updateTaskOK",e);
		}
		
		return result;
	}
	
	/**
	 * 重新办理任务
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=updateTaskAgainDo")
	@ResponseBody
	public Map<String , Object> updateTaskAgainDo(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Task task = service.updateTaskAgainDo(map);
			result.put("data", task);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("updateTaskAgainDo",e);
		}
		
		return result;
	}
	/**
	 * 任务办理完结
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=updateTaskFinish")
	@ResponseBody
	public Map<String , Object> updateTaskFinish(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			Task task = service.updateTaskFinish(id);
			result.put("data", task);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("updateTaskFinish",e);
		}
		
		return result;
	}
}
