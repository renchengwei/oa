package oa.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "tb_post")
@Scope(value = "prototype")
public class Post {

	@Id
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 发帖人
	 */
	@Column(updatable=false)
	private String issueuser;
	@Transient
	private User issueuserbean;
	
	/**
	 * 发帖时间
	 */
	@Column(updatable=false)
	private String issuetime;
	
	/**
	 * 回复次数
	 */
	private int receivenum;
	/**
	 * 帖子分类
	 */
	private String type;
	/**
	 * 置顶时间
	 */
	private String toptime;
	
	/**
	 * 是否屏蔽
	 * 0：为屏蔽
	 * 1：已屏蔽
	 */
	private String isshield;
	
	/**
	 * 是否删除
	 * 0：未删除
	 * 1：已删除
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
	public String getIssueuser() {
		return issueuser;
	}
	public void setIssueuser(String issueuser) {
		this.issueuser = issueuser;
	}
	public String getIssuetime() {
		return issuetime;
	}
	public void setIssuetime(String issuetime) {
		this.issuetime = issuetime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getToptime() {
		return toptime;
	}
	public void setToptime(String toptime) {
		this.toptime = toptime;
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
	public int getReceivenum() {
		return receivenum;
	}
	public void setReceivenum(int receivenum) {
		this.receivenum = receivenum;
	}
	public User getIssueuserbean() {
		return issueuserbean;
	}
	public void setIssueuserbean(User issueuserbean) {
		this.issueuserbean = issueuserbean;
	}
	public String getIsshield() {
		return isshield;
	}
	public void setIsshield(String isshield) {
		this.isshield = isshield;
	}
}
