package oa.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "v_postandreply")
@Scope(value = "prototype")
public class PostAndReply {

	@Id
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String id;
	private String postid;
	private String title;
	private String content;
	private String issueuser;
	private String issuetime;
	private Integer ordernum;
	private String type;
	private String replyid;
	private String isdel;
	/**
	 * 是否屏蔽
	 * 0：为屏蔽
	 * 1：已屏蔽
	 */
	private String isshield;
	/**
	 * 发表人
	 */
	@Transient
	private User issueuserbean;
	/**
	 * 回复对象（不一定是楼主）
	 */
	@Transient
	private PostReply replyben;
	
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
	public Integer getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
	public String getReplyid() {
		return replyid;
	}
	public void setReplyid(String replyid) {
		this.replyid = replyid;
	}
	public User getIssueuserbean() {
		return issueuserbean;
	}
	public void setIssueuserbean(User issueuserbean) {
		this.issueuserbean = issueuserbean;
	}
	public PostReply getReplyben() {
		return replyben;
	}
	public void setReplyben(PostReply replyben) {
		this.replyben = replyben;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	public String getPostid() {
		return postid;
	}
	public void setPostid(String postid) {
		this.postid = postid;
	}
	public String getIsshield() {
		return isshield;
	}
	public void setIsshield(String isshield) {
		this.isshield = isshield;
	}
}
