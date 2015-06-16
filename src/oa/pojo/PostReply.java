package oa.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "tb_postreply")
@Scope(value = "prototype")
public class PostReply {

	@Id
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String id;
	/**
	 * 帖子id
	 */
	@Column(updatable=false)
	private String postid;
	/**
	 * 回复人
	 */
	@Column(updatable=false)
	private String replyuser;
	/**
	 * 回复时间
	 */
	@Column(updatable=false)
	private String replytime;
	/**
	 * 回复内容
	 */
	private String content;
	
	/**
	 * 是否屏蔽
	 * 0：为屏蔽
	 * 1：已屏蔽
	 */
	private String isshield;
	
	private String isdel;
	
	private String deluser;
	
	private String deltime;
	
	private Integer ordernum;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPostid() {
		return postid;
	}
	public void setPostid(String postid) {
		this.postid = postid;
	}
	public String getReplyuser() {
		return replyuser;
	}
	public void setReplyuser(String replyuser) {
		this.replyuser = replyuser;
	}
	public String getReplytime() {
		return replytime;
	}
	public void setReplytime(String replytime) {
		this.replytime = replytime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Integer getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
	public String getIsshield() {
		return isshield;
	}
	public void setIsshield(String isshield) {
		this.isshield = isshield;
	}
}
