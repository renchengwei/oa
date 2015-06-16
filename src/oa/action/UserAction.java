package oa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oa.pojo.Role;
import oa.pojo.User;
import oa.service.DepartmentService;
import oa.service.DictionaryService;
import oa.service.UserService;
import oa.util.SessionUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("UserAction.do")
public class UserAction {

	private static final Logger logger = Logger.getLogger(UserAction.class);
	
	@Resource(name="userService")
	private UserService service;
	@Resource(name="dictionaryService")
	private DictionaryService dictionaryService;
	@Resource(name="departmentService")
	private DepartmentService depService;

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}
	
	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
	public DepartmentService getDepService() {
		return depService;
	}

	public void setDepService(DepartmentService depService) {
		this.depService = depService;
	}
	
	/**
	 * 更新用户信息（用户更新）
	 * @param request
	 * @param map
	 * @param photofile
	 * @return
	 */
	@RequestMapping(params="method=updateUser")
	public ModelAndView updateUser(HttpServletRequest request,@RequestParam Map<String,String> map,
			@RequestParam(value="photofile", required=false) MultipartFile photofile) {
		ModelAndView mv = new ModelAndView("user/userinfo");
		try {
			User user = service.updateUser(map,photofile);
			mv.addObject("success", true);
			mv.addObject("data", user);
		}catch(Exception e) {
			mv.addObject("isSuccess", false);
			mv.addObject("errorMsg", "服务器异常!");
			logger.error("updateUser",e);
		}
		return mv;
	}
	

	/**
	 * 保存用户
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=saveUser")
	@ResponseBody
	public Map<String,Object> saveUser(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			//检查用户名是否已经存在
			boolean checkRet = true;
			if(StringUtils.isEmpty(map.get("id"))) {
				String loginName = map.get("loginname");
				checkRet = service.checkUser(loginName);
			}
			
			if(checkRet) {
				User user = service.saveUser(map);
				result.put("data", user);
				result.put("isSuccess", true);
			}else {
				result.put("isSuccess", false);
				result.put("errorMsg", "用户名已存在!");
			}
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("saveUser",e);
		}
		return result;
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=updatePassword")
	@ResponseBody
	public Map<String,Object> updatePassword(HttpServletRequest request,@RequestParam Map<String,String> map,
			HttpServletResponse response) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			//检测原密码是否正确
			String oldpassword = map.get("oldpassword");
			if(StringUtils.isNotEmpty(oldpassword)) {
				oldpassword = DigestUtils.md5Hex(oldpassword);
			}
			if(oldpassword.equals(SessionUtil.getUser().getPassword())) {
				service.updatePassword(map);
				result.put("isSuccess", true);
			}else {
				result.put("isSuccess", false);
				result.put("errorMsg", "原密码输入有误!");
			}
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("updatePassword",e);
		}
		return result;
	}

	/**
	 * 查询所有角色
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getRoles")
	@ResponseBody
	public Map<String,Object> getRoles(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			List<Role> rolelist = service.getRoles(map);
			result.put("data", rolelist);
			result.put("isSuccess", true);
			
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getRoles",e);
		}
		return result;
	}
	/**
	 * 分页查询所有用户
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getUsersByPage")
	@ResponseBody
	public Map<String,Object> getUsersByPage(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> data = service.getUsersByPage(map);
			result.put("data",data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getUsersByPage",e);
		}
		return result;
	}
	
	/**
	 * 根据ID查询用户
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getUserById")
	@ResponseBody
	public Map<String,Object> getUserById(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			User user = service.getUserById(id);
			result.put("data",user);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getUserById",e);
		}
		return result;
	}
	
	/**
	 * 根据ID删除用户
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=deleteUser")
	@ResponseBody
	public Map<String,Object> deleteUser(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			service.deleteUser(id);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getUserById",e);
		}
		return result;
	}
	/**
	 * 查询系统机构人员树形数据
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getTreeDepUser")
	@ResponseBody
	public Map<String,Object> getTreeDepUser(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			List<Object> list = service.getTreeDepUser(map);
			result.put("data", list);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getTreeDepUser",e);
		}
		return result;
	}
	
	/**
	 * 获取当前用户信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getCurrentUser")
	@ResponseBody
	public Map<String,Object> getCurrentUser(HttpServletRequest request,@RequestParam Map<String,String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			User user = service.getUserById(SessionUtil.getUser().getId());
			result.put("data", user);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getCurrentUser",e);
		}
		return result;
	}
	
	/**
	 * 禁言/取消禁言用户
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=updateEnableSendMsg")
	@ResponseBody
	public Map<String,Object> updateEnableSendMsg(HttpServletRequest request,@RequestParam Map<String,String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			User user = service.updateEnableSendMsg(id);
			result.put("data", user);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("updateEnableSendMsg",e);
		}
		return result;
	}
}
