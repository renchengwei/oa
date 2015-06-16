package oa.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import oa.pojo.Post;
import oa.pojo.PostReply;
import oa.pojo.User;
import oa.service.PostService;
import oa.service.UserService;
import oa.util.SessionUtil;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("PostAction.do")
public class PostAction {

	private static final Logger logger = Logger.getLogger(PostAction.class);
	
	@Resource(name="postService")
	private PostService postService;
	@Resource(name="userService")
	private UserService userService;

	public PostService getPostService() {
		return postService;
	}

	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

//	@RequestMapping(params="method=uploadFile")  
//	@ResponseBody
//    public Map<String,Object> uploadFile(HttpServletResponse response,HttpServletRequest request,
//    		@RequestParam(value="file", required=true) MultipartFile file) {  
//		Map<String , Object> result = new HashMap<String, Object>();
//		try {
//			PostFile postFile = postService.saveUploadFile(file);
//			result.put("data", postFile);
//			result.put("isSuccess", true);
//		}catch(Exception e) {
//			result.put("isSuccess", false);
//			result.put("errorMsg", "服务器异常!");
//			logger.error("uploadFile",e);
//		}
//		return result;
//    }  
	
	/**
	 * 保存帖子
	 * @param response
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(params="method=savePost")  
	@ResponseBody
    public Map<String,Object> savePost(HttpServletRequest request,@RequestParam Map<String,String> map) {  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Post post = postService.savePost(map);
			result.put("data", post);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("savePost",e);
		}
		return result;
    }  
	
	/**
	 * 保存帖子回复
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=savePostReply")  
	@ResponseBody
    public Map<String,Object> savePostReply(HttpServletRequest request,@RequestParam Map<String,String> map) {  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			PostReply postreply = postService.savePostReply(map);
			result.put("data", postreply);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("savePostReply",e);
		}
		return result;
    }  
	
	/**
	 * 分页查询帖子
	 * @param response
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(params="method=getPostByPage")  
	@ResponseBody
	public Map<String,Object> getPostByPage(HttpServletRequest request,@RequestParam Map<String,String> map) {  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> data = postService.getPostByPage(map);
			result.put("data", data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getPostByPage",e);
		}
		return result;
	}  
	
	/**
	 * 分页查询帖子回复
	 * @param response
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(params="method=getPostAndReplyByPage")  
	@ResponseBody
	public Map<String,Object> getPostAndReplyByPage(HttpServletRequest request,@RequestParam Map<String,String> map) {  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Map<String,Object> data = postService.getPostAndReplyByPage(map);
			result.put("data", data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getPostAndReplyByPage",e);
		}
		return result;
	}  
	
	/**
	 * 查询帖子和当前人员
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getPostAndUser")  
	@ResponseBody
	public Map<String,Object> getPostAndUser(HttpServletRequest request,@RequestParam Map<String,String> map) {  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Post post = postService.getPostById(map.get("id"));
			User user = userService.getUserById(SessionUtil.getUser().getId());
			result.put("post", post);
			result.put("user", user);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getPostAndUser",e);
		}
		return result;
	}  
	
	
	/**
	 * 置顶帖子
	 * @param response
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(params="method=updateToptime")  
	@ResponseBody
	public Map<String,Object> updateToptime(HttpServletRequest request,@RequestParam Map<String,String> map) {  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			postService.updateToptime(id);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("updateToptime",e);
		}
		return result;
	}  
	
	/**
	 *  屏蔽/取消屏蔽帖子或者回复
	 * @param response
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(params="method=updateIsshield")  
	@ResponseBody
	public Map<String,Object> updateIsshield(HttpServletRequest request,@RequestParam Map<String,String> map) {  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			postService.updateIsshield(id);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("updateIsshield",e);
		}
		return result;
	}  
	/**
	 *  删除帖子
	 * @param response
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(params="method=deletePost")  
	@ResponseBody
	public Map<String,Object> deletePost(HttpServletRequest request,@RequestParam Map<String,String> map) {  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			postService.deletePost(id);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("deletePost",e);
		}
		return result;
	}  
	
	
}
