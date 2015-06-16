package oa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import oa.pojo.Department;
import oa.service.DepartmentService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("DepartmentAction.do")
public class DepartmentAction {
	
	private static final Logger logger = Logger.getLogger(DepartmentAction.class);
	
	@Resource(name="departmentService")
	private DepartmentService service;
	
	public DepartmentService getService() {
		return service;
	}

	public void setService(DepartmentService service) {
		this.service = service;
	}

	/**
	 * 保存部门信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=saveDepartment")
	@ResponseBody
	public Map<String,Object> saveDepartment(HttpServletRequest request,@RequestParam Map<String, String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Department dep = service.saveDepartment(map);
			result.put("data", dep);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("saveDepartment",e);
		}
		return result;
	}
	
	/**
	 * 查询所有部门信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getAllDepartments")
	@ResponseBody
	public Map<String,Object> getAllDepartments(HttpServletRequest request,@RequestParam Map<String, String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			List<Department> deplist = service.getAllDepartments(map);
			result.put("data", deplist);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getAllDepartments",e);
		}
		return result;
	}
	
	/**
	 * 根据id查询部门
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getDepartmentById")
	@ResponseBody
	public Map<String,Object> getDepartmentById(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		
		try {
			String id = map.get("id");
			Department dep = service.getDepartmentById(id);
			result.put("data", dep);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getDepartmentById",e);
		}
		return result;
	}
	

	/**
	 * 删除部门，并会级联删除相关的子部门和相关人员信息   
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=deleteDepartment")
	@ResponseBody
	public Map<String,Object> deleteDepartment(HttpServletRequest request,@RequestParam Map<String, String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		
		try {
			String id = map.get("id");
			service.deleteDepartment(id);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("deleteDepartment",e);
		}
		return result;
	}
}
