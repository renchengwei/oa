package oa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import oa.pojo.Information;
import oa.service.InformationService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("InformationAction.do")
public class InformationAction {

	private static final Logger logger = Logger.getLogger(InformationAction.class);
	
	@Resource(name="informationService")
	private InformationService service;

	public InformationService getService() {
		return service;
	}

	public void setService(InformationService service) {
		this.service = service;
	}
	
	@RequestMapping(params="method=saveInfor")
	@ResponseBody
	public Map<String,Object> saveInfor(HttpServletRequest request,@RequestParam Map<String,String> map,
			@RequestParam(value="depids") List<String> depids) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Information infor = service.saveInfor(map,depids);
			result.put("data", infor);
			result.put("isSuccess", true);
			
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("saveInfor",e);
		}
		return result;
	}
	
	@RequestMapping(params="method=getInforByPage")
	@ResponseBody
	public Map<String,Object> getInforByPage(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> data = service.getInforByPage(map);
			result.put("data", data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getInforByPage",e);
		}
		return result;
	}
	
	@RequestMapping(params="method=getShowInforByPage")
	@ResponseBody
	public Map<String,Object> getShowInforByPage(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> data = service.getShowInforByPage(map);
			result.put("data", data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getShowInforByPage",e);
		}
		return result;
	}
	
	@RequestMapping(params="method=delInfor")
	@ResponseBody
	public Map<String,Object> delInfor(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			service.delInfor(id);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("delInfor",e);
		}
		return result;
	}
	
	@RequestMapping(params="method=getInforById")
	@ResponseBody
	public Map<String,Object> getInforById(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			Information infor = service.getInforById(id);
			result.put("data", infor);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getInforById",e);
		}
		return result;
	}

}
