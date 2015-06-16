package oa.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import oa.pojo.Message;
import oa.service.MessageService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("MessageAction.do")
public class MessageAction {

	private static final Logger logger = Logger.getLogger(MessageAction.class);
	
	@Resource(name="messageService")
	private MessageService messageService;

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@RequestMapping(params="method=saveMessage")
	@ResponseBody
    public Map<String,Object> saveMessage(HttpServletRequest request,@RequestParam Map<String,String> map){  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Message message = messageService.saveMessage(map);
			result.put("data", message);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("saveMessage",e);
		}
		return result;
    }  
	
	@RequestMapping(params="method=getMessageByPage")
	@ResponseBody
    public Map<String,Object> getMessageByPage(HttpServletRequest request,@RequestParam Map<String,String> map){  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> data = messageService.getMessageByPage(map);
			result.put("data", data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getMessageByPage",e);
		}
		return result;
    } 
	
	@RequestMapping(params="method=getMessageById")
	@ResponseBody
    public Map<String,Object> getMessageById(HttpServletRequest request,@RequestParam Map<String,String> map){  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			Message message = messageService.getMessageById(id);
			result.put("data", message);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getMessageById",e);
		}
		return result;
    } 

	@RequestMapping(params="method=deleteMessage")
	@ResponseBody
    public Map<String,Object> deleteMessage(HttpServletRequest request,@RequestParam Map<String,String> map){  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			Message message = messageService.deleteMessage(id);
			result.put("data", message);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("deleteMessage",e);
		}
		return result;
    } 

}
