package oa.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name="tb_task")
@Scope(value = "prototype")
public class Task {

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
	 * 下发人
	 */
	@Column(updatable=false)
	private String issueuser;
	
	@Transient
	private User issueuserbean;
	
	/**
	 * 下发时间
	 */
	@Column(updatable=false)
	private String issuetime;
	/**
	 * 状态
	 * 
	 * 0:未查看
	 * 1:办理中
	 * 2:办理完毕
	 * 3:办结
	 */
	private String status;
	/**
	 * 紧急程度
	 */
	private String level;
	/**
	 * 截止时间
	 */
	private String endtime;
	/**
	 * 办理人
	 */
	@Column(updatable=false)
	private String transactor;
	@Transient
	private User transactorbean;
	@Transient
	private List<TaskProgress> progresslist;
	@Transient
	private String levelname;
	/**
	 * 是否短信通知
	 */
	private String ismsg;
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
		if(StringUtils.isNotEmpty(issuetime)) {
			return issuetime.substring(0, 10);
		}
		return issuetime;
	}
	public void setIssuetime(String issuetime) {
		this.issuetime = issuetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getEndtime() {
		if(StringUtils.isNotEmpty(endtime)) {
			return endtime.substring(0, 10);
		}
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getTransactor() {
		return transactor;
	}
	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}
	public String getIsmsg() {
		return ismsg;
	}
	public void setIsmsg(String ismsg) {
		this.ismsg = ismsg;
	}
	public User getIssueuserbean() {
		return issueuserbean;
	}
	public void setIssueuserbean(User issueuserbean) {
		this.issueuserbean = issueuserbean;
	}
	public User getTransactorbean() {
		return transactorbean;
	}
	public void setTransactorbean(User transactorbean) {
		this.transactorbean = transactorbean;
	}
	public List<TaskProgress> getProgresslist() {
		return progresslist;
	}
	public void setProgresslist(List<TaskProgress> progresslist) {
		this.progresslist = progresslist;
	}
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	
}
