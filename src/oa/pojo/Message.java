package oa.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "tb_message")
@Scope(value = "prototype")
public class Message {
	@Id
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String id;
	
	/**
	 * 收件人
	 */
	private String receiveusers;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 发送时间
	 */
	private String sendtime;
	
	/**
	 * 发送人
	 */
	private String senduser;
	
	/**
	 * 状态
	 * 
	 * 0：草稿
	 * 1：发送
	 */
	private String status;
	/**
	 * 是否删除
	 */
	private String isdel;
	
	/**
	 * 删除人
	 */
	private String deluser;
	
	/**
	 * 删除时间
	 */
	private String deltime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReceiveusers() {
		return receiveusers;
	}

	public void setReceiveusers(String receiveusers) {
		this.receiveusers = receiveusers;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendtime() {
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

	public String getIsdel() {
		return isdel;
	}

	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}

	public String getDeluser() {
		return deluser;
	}

	public void setDeluser(String deluser) {
		this.deluser = deluser;
	}

	public String getDeltime() {
		return deltime;
	}

	public void setDeltime(String deltime) {
		this.deltime = deltime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
