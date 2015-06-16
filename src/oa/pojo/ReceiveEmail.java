package oa.pojo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;


@Entity
@Table(name = "tb_receiveemail")
@Scope(value = "prototype")
public class ReceiveEmail {

	@Id
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 正文
	 */
	private String content;
	
	/**
	 * 发件ID
	 */
	private String sendemailid;
	/**
	 * 发送时间
	 */
	private String sendtime;
	
	/**
	 * 发件人
	 */
	private String senduser;
	@Transient
	private User senduserbean;
	
	/**
	 * 收件时间
	 */
	private String receivetime;
	
	/**
	 * 收件人
	 */
	private String receiveuser;
	
	
	/**
	 * 收件人姓名集合
	 */
	private String receiveusernames;
	
	/**
	 * 是否删除
	 * 0:未删除
	 * 1:已删除
	 */
	private String isdel;
	
	/**
	 * 删除时间
	 */
	private String deltime;
	
	/**
	 * 删除人
	 */
	private String deluser;
	
	/**
	 * 是否已查看
	 * 0：未查看
	 * 1：已查看
	 */
	private String isshow;
	
	@Transient
	private List<EmailFile> files;
	
	
	public List<EmailFile> getFiles() {
		return files;
	}

	public void setFiles(List<EmailFile> files) {
		this.files = files;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	public String getReceiveuser() {
		if(StringUtils.isNotEmpty(receiveuser)) {
			return receiveuser.substring(0, 10);
		}
		return receiveuser;
	}

	public void setReceiveuser(String receiveuser) {
		this.receiveuser = receiveuser;
	}

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}

	public String getDeltime() {
		return deltime;
	}

	public void setDeltime(String deltime) {
		this.deltime = deltime;
	}

	public String getDeluser() {
		return deluser;
	}

	public void setDeluser(String deluser) {
		this.deluser = deluser;
	}

	public String getSendemailid() {
		return sendemailid;
	}

	public void setSendemailid(String sendemailid) {
		this.sendemailid = sendemailid;
	}

	public String getSendtime() {
		if(StringUtils.isNotEmpty(sendtime)) {
			return sendtime.substring(0, 10);
		}
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getSenduser() {
		return senduser;
	}

	public void setSenduser(String senduser) {
		this.senduser = senduser;
	}

	public String getReceiveusernames() {
		return receiveusernames;
	}

	public void setReceiveusernames(String receiveusernames) {
		this.receiveusernames = receiveusernames;
	}

	public User getSenduserbean() {
		return senduserbean;
	}

	public void setSenduserbean(User senduserbean) {
		this.senduserbean = senduserbean;
	}

	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}
	
}
