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
@Table(name="tb_news")
@Scope(value = "prototype")
public class News {

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
	 * 发布人
	 */
	@Column(updatable=false)
	private String issueuser;
	@Transient
	private User issueuserBean;
	/**
	 * 发布时间
	 */
	@Column(updatable=false)
	private String issuetime;
	
	/**
	 * 是否删除
	 * 
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

	public User getIssueuserBean() {
		return issueuserBean;
	}

	public void setIssueuserBean(User issueuserBean) {
		this.issueuserBean = issueuserBean;
	}

	public String getIssuetime() {
		return issuetime;
	}

	public void setIssuetime(String issuetime) {
		this.issuetime = issuetime;
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
}
