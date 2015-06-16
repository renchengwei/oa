package oa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oa.pojo.Menu;
import oa.pojo.User;
import oa.service.EmailService;
import oa.service.InformationService;
import oa.service.NewsService;
import oa.service.TaskService;
import oa.service.UserService;
import oa.util.SessionUtil;
import oa.util.StaticCodeBook;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("LoginAction.do")
public class LoginAction {

	private static final Logger logger = Logger.getLogger(LoginAction.class);
	
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="informationService")
	private InformationService infoService;
	@Resource(name="newsService")
	private NewsService newsService;
	@Resource(name="emailService")
	private EmailService emailService;
	@Resource(name="taskService")
	private TaskService taskService;
	
	
	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public InformationService getInfoService() {
		return infoService;
	}

	public void setInfoService(InformationService infoService) {
		this.infoService = infoService;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	/**
	 * 用户登录
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=login")
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request,@RequestParam Map<String, String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String username = map.get("username");
			String password = map.get("password");
			
			if(StringUtils.isNotEmpty(password)) {
				password = DigestUtils.md5Hex(password);
			}
			
			User user = userService.getUserByNamePassword(username,password);
			
			if(user == null) {//用户名或者密码错误
				result.put("isSuccess", false);
				result.put("errorMsg", "用户名或密码错误，请重新输入!");
			}else if("1".equals(user.getAvailable())){//用户账号被禁用
				result.put("isSuccess", false);
				result.put("errorMsg", "您的账号已被禁用，请联系系统管理员!");
			}else {
				userService.getUserRoleMenu(user);
				SessionUtil.setUser(request, user);
				result.put("isSuccess", true);
				request.getSession().setAttribute("istip", true);
			}
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("login",e);
		}
		return result;
	}
	
	@RequestMapping(params="method=setisTip")
	@ResponseBody
	public Map<String,Object> setisTip(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			request.getSession().setAttribute("istip", false);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("setisTip",e);
		}
		return result;
	}


	/**
	 * 查询登录用户拥有的菜单
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getLoginUserMenus")
	@ResponseBody
	public Map<String,Object> getLoginUserMenus(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			List<Menu> list= SessionUtil.getUser().getRole().getMenulist();
			result.put("data", list);
			result.put("isSuccess", true);
			
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getLoginUserMenus",e);
		}
		return result;
	}
	
	/**
	 * 查询用户首页内容
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getLoginUserMainData")
	@ResponseBody
	public Map<String,Object> getLoginUserMainData(HttpServletRequest request,@RequestParam Map<String,String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			List<Menu> menulist= SessionUtil.getUser().getRole().getMenulist();
			
			Map<String, Object> informap = infoService.getShowInforByPage(map);
			Map<String, Object> newsmap = newsService.getNewsByPage(map);
			//未收邮件
			int emailcount = emailService.getShowEmailCount();
			//未办理任务
			int taskcount = taskService.getShowTaskCount();
			
			result.put("emailcount", emailcount);
			result.put("taskcount", taskcount);
			result.put("menus", menulist);
			result.put("infors", informap.get(StaticCodeBook.PAGE_ROWS));
			result.put("news", newsmap.get(StaticCodeBook.PAGE_ROWS));
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getLoginUserMainData",e);
		}
		return result;
	}

	

	/**
	 * 注销
	 * @param request
	 * @param response
	 * @param map
	 */
	@RequestMapping(params = "method=logout")
	public void logout(HttpServletRequest request,
			HttpServletResponse response, @RequestParam Map<String, String> map){
		try {
			SessionUtil.removeUser(request);
			response.sendRedirect(StaticCodeBook.LOGIN_PAGE);
		}catch(Exception e) {
			logger.error("logout", e);
		}
	}

}
