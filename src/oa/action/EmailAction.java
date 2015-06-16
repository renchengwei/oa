package oa.action;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oa.pojo.EmailFile;
import oa.pojo.ReceiveEmail;
import oa.pojo.SendEmail;
import oa.service.EmailService;
import oa.util.StaticCodeBook;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("EmailAction.do")
public class EmailAction {
	
	private static final Logger logger = Logger.getLogger(EmailAction.class);
	
	@Resource(name="emailService")
	private EmailService service;
	
	public EmailService getService() {
		return service;
	}

	public void setService(EmailService service) {
		this.service = service;
	}


	/**
	 * 上传附件
	 * @param response
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(params="method=uploadFile")  
	@ResponseBody
    public Map<String,Object> uploadFile(HttpServletResponse response,HttpServletRequest request,
    		@RequestParam(value="file", required=true) MultipartFile file) {  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			EmailFile emailFile = service.saveUploadFile(file);
			result.put("data", emailFile);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("uploadFile",e);
		}
		return result;
    }  
	
	/**
	 * 发送邮件
	 * @param request
	 * @param map
	 * @param fileid
	 * @return
	 */
	@RequestMapping(params="method=saveEmail")
	@ResponseBody
    public Map<String,Object> saveEmail(HttpServletRequest request,@RequestParam Map<String,String> map,
    		@RequestParam(value="fileid",required=false)String[] fileid){  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			SendEmail emailFile = service.saveEmail(map,fileid);
			result.put("data", emailFile);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("saveEmail",e);
		}
		return result;
    }  
	
	/**
	 * 分页查询发件
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getSendEmailList")
	@ResponseBody
    public Map<String,Object> getSendEmailList(HttpServletRequest request,@RequestParam Map<String,String> map){  
		Map<String , Object> result = new HashMap<String, Object>();
		map.put("status", "1");
		try {
			Map<String , Object> data = service.getSendEmailList(map);
			result.put("data", data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getSendEmailList",e);
		}
		return result;
    }  
	
	/**
	 * 分页查询收件
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getReceiveEmailList")
	@ResponseBody
	public Map<String,Object> getReceiveEmailList(HttpServletRequest request,@RequestParam Map<String,String> map){  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			Map<String , Object> data = service.getReceiveEmailList(map);
			result.put("data", data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getReceiveEmailList",e);
		}
		return result;
	}  
	/**
	 * 分页查询草稿
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getDrafteEmailList")
	@ResponseBody
	public Map<String,Object> getDrafteEmailList(HttpServletRequest request,@RequestParam Map<String,String> map){  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			map.put("status", "0");
			Map<String , Object> data = service.getSendEmailList(map);
			result.put("data", data);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getDrafteEmailList",e);
		}
		return result;
	}  
	/**
	 * 删除邮件附件
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=deleteEmailFile")
	@ResponseBody
	public Map<String,Object> deleteEmailFile(HttpServletRequest request,@RequestParam Map<String,String> map){  
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			service.deleteEmailFile(id);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("deleteEmailFile",e);
		}
		return result;
	}  
	/**
	 * 删除邮件
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=deleteSendEmails")
	@ResponseBody
	public Map<String,Object> deleteSendEmails(HttpServletRequest request,@RequestParam Map<String,String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			service.deleteSendEmails(map);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("deleteSendEmails",e);
		}
		return result;
	}
	
	/**
	 * 删除收件
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=deleteReceiveEmails")
	@ResponseBody
	public Map<String,Object> deleteReceiveEmails(HttpServletRequest request,@RequestParam Map<String,String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			service.deleteReceiveEmails(map);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("deleteReceiveEmails",e);
		}
		return result;
	}
	
	/**
	 * 转发发件
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=transmitEmail")
	@ResponseBody
	public Map<String,Object> transmitEmail(HttpServletRequest request,@RequestParam Map<String,String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			SendEmail email = service.getTransmitEmail(id);
			result.put("data", email);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("transmitEmail",e);
		}
		return result;
	}
	
	/**
	 * 回复邮件
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=replyReceiveEmail")
	@ResponseBody
	public Map<String,Object> replyReceiveEmail(HttpServletRequest request,@RequestParam Map<String,String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			SendEmail email = service.getreplyReceiveEmail(id);
			result.put("data", email);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("replyReceiveEmail",e);
		}
		return result;
	}
	/**
	 * 根据ID查询发件
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getSendEmailById")
	@ResponseBody
	public Map<String,Object> getSendEmailById(HttpServletRequest request,@RequestParam Map<String,String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			SendEmail email = service.getSendEmailById(id);
			result.put("data", email);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getSendEmailById",e);
		}
		return result;
	}
	/**
	 * 根据ID查询收件
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getReceiveEmailById")
	@ResponseBody
	public Map<String,Object> getReceiveEmailById(HttpServletRequest request,@RequestParam Map<String,String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			String id = map.get("id");
			ReceiveEmail email = service.getReceiveEmailById(id);
			result.put("data", email);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getReceiveEmailById",e);
		}
		return result;
	}
	/**
	 * 根据未收邮件数量
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=getShowEmailCount")
	@ResponseBody
	public Map<String,Object> getShowEmailCount(HttpServletRequest request,@RequestParam Map<String,String> map) {
		Map<String , Object> result = new HashMap<String, Object>();
		try {
			int count = service.getShowEmailCount();
			result.put("data", count);
			result.put("isSuccess", true);
		}catch(Exception e) {
			result.put("isSuccess", false);
			result.put("errorMsg", "服务器异常!");
			logger.error("getShowEmailCount",e);
		}
		return result;
	}
	/**
	 * 下载附件
	 * @param request
	 * @param response
	 * @param map
	 */
	@RequestMapping(params="method=downloadfile")
	public void downloadfile(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String,String> map) {
		String fileid = map.get("id");
		EmailFile file = service.getEmailFileById(fileid);
		String filename = file.getFilename();
		String filepath = file.getFilepath();
		
		String path = StaticCodeBook.getEmailFilePath() + filepath;
		
		BufferedOutputStream bos = null;  
	    FileInputStream fis = null;
	    
	    try {
			String disposition = "attachment;filename=" + URLEncoder.encode(filename, "UTF-8");//注意(1)
			response.setContentType("application/x-msdownload;charset=UTF-8");//注意(2)
			response.setHeader("Content-disposition", disposition);  
			 
			fis = new FileInputStream(path);  
            bos = new BufferedOutputStream(response.getOutputStream());  
            byte[] buffer = new byte[2048];  
            while(fis.read(buffer) != -1){  
                bos.write(buffer);  
            }  
		} catch (IOException e) {
			logger.error("downloadfile", e);
		}  {
			if(fis != null){try {fis.close();} catch (IOException e) {}}  
            if(bos != null){try {bos.close();} catch (IOException e) {}}
		}
	}
	
}
