package oa.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oa.dao.EmailDao;
import oa.pojo.EmailFile;
import oa.pojo.ReceiveEmail;
import oa.pojo.SMS;
import oa.pojo.SendEmail;
import oa.pojo.User;
import oa.util.BeanCopyUtil;
import oa.util.DateUtil;
import oa.util.SessionUtil;
import oa.util.StaticCodeBook;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component("emailService")
public class EmailService {

	@Resource(name="emailDao")
	private EmailDao dao;

	public EmailDao getDao() {
		return dao;
	}

	public void setDao(EmailDao dao) {
		this.dao = dao;
	}

	/**
	 * 保存附件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public EmailFile saveUploadFile(MultipartFile file) throws IOException {
		
		byte[] bytes = file.getBytes();  
		
        String path = StaticCodeBook.getEmailFilePath();
        String filename = DateUtil.getDateString4(new Date());
        File uploadedFile = new File(path + filename);  
		EmailFile emailFile = new EmailFile();
		emailFile.setFilename(file.getOriginalFilename());
		emailFile.setFilesize(file.getSize() / 1.0);
		emailFile.setFilepath(filename);
		dao.saveObject(emailFile);
		
		FileCopyUtils.copy(bytes, uploadedFile);
		return emailFile;
	}

	/**
	 * 保存邮件信息
	 * @param map	邮件基本信息
	 * @param receiveuserid	收件人id
	 * @param fileid		附件ID
	 * @return
	 */
	public SendEmail saveEmail(Map<String, String> map,String[] fileid) {
		SendEmail se = new SendEmail();
		BeanCopyUtil.setFieldValue(se, map);
		 
		//修改
		if(StringUtils.isNotEmpty(se.getId())) {
			dao.updateObject(se);
		}else {//新建
			se.setSenduser(SessionUtil.getUser().getId());
			se.setIsdel("0");
			dao.saveObject(se);
		}
		//补全附件和邮件直接的关联信息
		if(fileid != null && fileid.length > 0) {
			dao.updateFile(fileid,se.getId());
		}
		//如果是发送
		if("1".equals(se.getStatus())) {
			
			List<EmailFile> filelist = dao.getEmailFileByEmailId(se.getId());
			
			//补全发件信息
			se.setSendtime(DateUtil.getDateTime(new Date()));
			se.setSenduser(SessionUtil.getUser().getId());
			dao.updateObject(se);
			//创建收件信息
			String[] receiveids = se.getReceiveuserid().split(";");
			for(String userid : receiveids) {
				ReceiveEmail re = new ReceiveEmail();
				re.setContent(se.getContent());
				re.setSendemailid(se.getId());
				re.setSendtime(se.getSendtime());
				re.setSenduser(se.getSenduser());
				re.setTitle(se.getTitle());
				re.setReceiveuser(userid);
				re.setReceiveusernames(se.getReceiveusernames());
				re.setIsdel("0");
				re.setIsshow("0");
				dao.saveObject(re);
				//创建附件信息
				if(filelist != null && filelist.size() > 0) {
					for(EmailFile f : filelist) {
						EmailFile file = new EmailFile();
						file.setEmailid(re.getId());
						file.setFilename(f.getFilename());
						file.setFilepath(f.getFilepath());
						file.setFilesize(f.getFilesize());
						dao.saveObject(file);
					}
				}
				//短信通知
				if("1".equals(se.getIsmsg())) {
					User user = (User) dao.get(User.class, userid);
					if(StringUtils.isNotEmpty(user.getMobile())) {
						SMS sms = new SMS();
						sms.setPhonenum(user.getMobile());
						sms.setStatus("0");
						sms.setSendtime(DateUtil.getDateString2(new Date()));
						sms.setSenduser(SessionUtil.getUser().getId());
						sms.setContent("您有一封新邮件，邮件标题:" + re.getTitle() +  "，请注意查收！");
						dao.saveObject(sms);
					}
				}
			}
		}
		return se;
	}

	/**
	 * 查询发送邮件列表
	 * @param map
	 * @return
	 */
	public Map<String, Object> getSendEmailList(Map<String, String> map) {
		map.put("senduser", SessionUtil.getUser().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		List<SendEmail> emaillist = new ArrayList<SendEmail>();
		int count = dao.getSendEmailCount(map);
		if(count > 0) {
			emaillist = dao.getSendEmailList(map);
		}
		result.put(StaticCodeBook.PAGE_TOTAL, count);
		result.put(StaticCodeBook.PAGE_ROWS, emaillist);
		return result;
	}

	public void deleteSendEmails(Map<String, String> map) {
		String ids = map.get("ids");
		String[] idArr = ids.split("#");
		dao.deleteSendEmails(idArr);
	}

	/**
	 * 转发邮件
	 * @param id
	 * @return
	 */
	public SendEmail getTransmitEmail(String id) {
		SendEmail email = (SendEmail) dao.get(SendEmail.class, id);
		List<EmailFile> files = dao.getEmailFileByEmailId(id);
		
		List<EmailFile> newfiles = new ArrayList<EmailFile>();
		if(files != null && files.size() > 0) {
			for(EmailFile file : files) {
				EmailFile newEmailFile = new EmailFile();
				newEmailFile.setFilename(file.getFilename());
				newEmailFile.setFilesize(file.getFilesize());
				newEmailFile.setFilepath(file.getFilepath());
				dao.saveObject(newEmailFile);
				newfiles.add(newEmailFile);
			}
		}
		
		SendEmail transmitEmail = new SendEmail();
		if(email != null) {
			transmitEmail.setTitle("转发：" + email.getTitle());
			transmitEmail.setIsmsg("1");
			transmitEmail.setContent("</br></br></br><p>---------原始邮件---------</p>" + email.getContent());
			transmitEmail.setFiles(newfiles);
		}
		return transmitEmail;
	}

	/**
	 * 删除邮件附件
	 * @param id
	 */
	public void deleteEmailFile(String id) {
		dao.deleteEmailFile(id);
	}

	/**
	 * 根据ID查询发件
	 * @param id
	 * @return
	 */
	public SendEmail getSendEmailById(String id) {
		SendEmail sendEmail = (SendEmail) dao.get(SendEmail.class, id);
		if(sendEmail != null && StringUtils.isNotEmpty(sendEmail.getSenduser())) {
			User user = (User) dao.get(User.class, sendEmail.getSenduser());
			sendEmail.setSenduserbean(user);
		}
		List<EmailFile> files = dao.getEmailFileByEmailId(id);
		sendEmail.setFiles(files);
		return sendEmail;
	}

	public EmailFile getEmailFileById(String fileid) {
		return (EmailFile) dao.get(EmailFile.class, fileid);
	}

	/**
	 * 分页查询收件
	 * @param map
	 * @return
	 */
	public Map<String, Object> getReceiveEmailList(Map<String, String> map) {
		map.put("receiveuser", SessionUtil.getUser().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		List<ReceiveEmail> emaillist = new ArrayList<ReceiveEmail>();
		int count = dao.getReceiveEmailCount(map);
		if(count > 0) {
			emaillist = dao.getReceiveEmailList(map);
		}
		result.put(StaticCodeBook.PAGE_TOTAL, count);
		result.put(StaticCodeBook.PAGE_ROWS, emaillist);
		return result;
	}

	/**
	 * 根据ID查找收件
	 * @param id
	 * @return
	 */
	public ReceiveEmail getReceiveEmailById(String id) {
		ReceiveEmail email = (ReceiveEmail) dao.get(ReceiveEmail.class, id);
		if("0".equals(email.getIsshow())) {
			email.setIsshow("1");
			dao.updateObject(email);
		}
		if(email != null && StringUtils.isNotEmpty(email.getSenduser())) {
			User user = (User) dao.get(User.class, email.getSenduser());
			email.setSenduserbean(user);
		}
		List<EmailFile> files = dao.getEmailFileByEmailId(id);
		email.setFiles(files);
		return email;
	}

	/**
	 * 删除收件
	 * @param map
	 */
	public void deleteReceiveEmails(Map<String, String> map) {
		String ids = map.get("ids");
		String[] idArr = ids.split("#");
		dao.deleteReceiveEmails(idArr);
	}

	/**
	 * 回复邮件
	 * @param id
	 * @return
	 */
	public SendEmail getreplyReceiveEmail(String id) {
		ReceiveEmail email = (ReceiveEmail) dao.get(ReceiveEmail.class, id);
		List<EmailFile> files = dao.getEmailFileByEmailId(id);
		
		List<EmailFile> newfiles = new ArrayList<EmailFile>();
		if(files != null && files.size() > 0) {
			for(EmailFile file : files) {
				EmailFile newEmailFile = new EmailFile();
				newEmailFile.setFilename(file.getFilename());
				newEmailFile.setFilesize(file.getFilesize());
				newEmailFile.setFilepath(file.getFilepath());
				dao.saveObject(newEmailFile);
				newfiles.add(newEmailFile);
			}
		}
		
		SendEmail transmitEmail = new SendEmail();
		if(email != null) {
			transmitEmail.setTitle("回复：" + email.getTitle());
			transmitEmail.setIsmsg("1");
			transmitEmail.setContent("</br></br></br><p>---------原始邮件---------</p>" + email.getContent());
			transmitEmail.setReceiveuserid(email.getSenduser());
			transmitEmail.setFiles(newfiles);
		}
		return transmitEmail;
	}

	/**
	 * 查询未收邮件数量
	 * @return
	 */
	public int getShowEmailCount() {
		String id = SessionUtil.getUser().getId();
		return dao.getShowEmailCount(id);
	}
}
