package oa.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import oa.pojo.News;
import oa.service.NewsService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("NewsAction.do")
public class NewsAction {

	private static final Logger logger = Logger.getLogger(NewsAction.class);
	
	@Resource(name="newsService")
	private NewsService newsService;

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}
	
	/**
	 * 保存新闻
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=saveNews")
	@ResponseBody
	public Map<String,Object> saveNews(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			News news = newsService.saveNews(map);
			result.put("data", news);
			result.put("isSuccess", true);
			
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("saveNews",e);
		}
		return result;
	}
	
	/**
	 * 分页查询新闻
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getNewsByPage")
	@ResponseBody
	public Map<String,Object> getNewsByPage(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> data = newsService.getNewsByPage(map);
			result.put("data", data);
			result.put("isSuccess", true);
			
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getNewsByPage",e);
		}
		return result;
	}
	
	/**
	 * 删除新闻
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=deleteNews")
	@ResponseBody
	public Map<String,Object> deleteNews(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			newsService.deleteNews(id);
			result.put("isSuccess", true);
			
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("deleteNews",e);
		}
		return result;
	}
	
	/**
	 * 根据ID查询新闻
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getNewsById")
	@ResponseBody
	public Map<String,Object> getNewsById(HttpServletRequest request,@RequestParam Map<String,String> map) {
		
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			News news = newsService.getNewsById(id);
			result.put("data", news);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getNewsById",e);
		}
		return result;
	}
}
