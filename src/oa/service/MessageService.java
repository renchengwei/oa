package oa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oa.dao.MessageDao;
import oa.pojo.Information;
import oa.pojo.Message;
import oa.pojo.SMS;
import oa.util.BeanCopyUtil;
import oa.util.DateUtil;
import oa.util.SessionUtil;
import oa.util.StaticCodeBook;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("messageService")
public class MessageService {

	@Resource(name="messageDao")
	private MessageDao messageDao;

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	/**
	 * 保存或者修改短信
	 * @param map
	 * @return
	 */
	public Message saveMessage(Map<String, String> map) {
		Message message = new Message();
		BeanCopyUtil.setFieldValue(message, map);
		//修改
		if(StringUtils.isNotEmpty(message.getId())) {
			messageDao.updateObject(message);
		}else {//保存
			message.setIsdel("0");
			messageDao.saveObject(message);
		}
		//如果是发送
		if("1".equals(message.getStatus())) {
			message.setSendtime(DateUtil.getDateString2(new Date()));
			message.setSenduser(SessionUtil.getUser().getId());
			messageDao.saveObject(message);
			
			String receiveusers = message.getReceiveusers();
			String[] userphonearr = receiveusers.split(";");
			int index = -1;
			for(String userphone : userphonearr) {
				if(StringUtils.isNotEmpty(userphone)) {
					SMS sms = new SMS();
					sms.setContent(message.getContent());
					sms.setSendtime(message.getSendtime());
					sms.setSenduser(message.getSenduser());
					sms.setStatus("0");
					
					if((index = userphone.indexOf("(")) != -1) {
						sms.setPhonenum(userphone.substring(0, index));
					}else {
						sms.setPhonenum(userphone);
					}
					messageDao.saveObject(sms);
				}
			}
		}
		return message;
	}

	public Map<String, Object> getMessageByPage(Map<String, String> map) {
		Map<String, Object> data = new HashMap<String, Object>();
		List<Message> messagelist = new ArrayList<Message>();
		int count = messageDao.getMessageCount(map);
		if(count > 0) {
			messagelist = messageDao.getMessageByPage(map);
		}
		data.put(StaticCodeBook.PAGE_TOTAL, count);
		data.put(StaticCodeBook.PAGE_ROWS, messagelist);
		return data;
	}

	public Message getMessageById(String id) {
		Message message = (Message) messageDao.get(Message.class, id);
		return message;
	}

	public Message deleteMessage(String id) {
		Message message = getMessageById(id);
		message.setIsdel("1");
		message.setDeltime(DateUtil.getDateString2(new Date()));
		message.setDeluser(SessionUtil.getUser().getId());
		messageDao.updateObject(message);
		return message;
	}
}
