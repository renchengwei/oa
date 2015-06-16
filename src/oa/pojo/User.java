package oa.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

@Entity
@Table(name = "tb_user")
@Scope(value = "prototype")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator="hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	private String id;
	
	/**
	 * 所属部门
	 */
	private String pid;
	/**
	 * 真实名称
	 */
	private String name;
	/**
	 * 登陆名称
	 */
	private String loginname;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 职务
	 */
	private String duty;
	/**
	 * 性别
	 * 0：男	1：女
	 */
	private String sex;
	/**
	 * 出身年月
	 */
	private String birthdate;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 办公电话
	 */
	private String workphone;
	/**
	 * 入职时间
	 */
	private String hiredate;
	/**
	 * 积分
	 */
	private Integer mark;
	/**
	 * 头像
	 */
	private String photo;
	/**
	 * 是否可用
	 * 0:否
	 * 1:是
	 */
	private String available;
	/**
	 * 创建时间
	 */
	@Column(updatable=false)
	private String createtime;
	/**
	 * 创建人
	 */
	@Column(updatable=false)
	private String createuser;
	/**
	 * 是否删除
	 * 0：否	1：是
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
	 * 角色
	 */
	private String roleid;
	
	/**
	 * 是否禁言
	 * 0：否
	 * 1：是
	 */
	private String enableSendMsg;
	
	/**
	 * 所属公司
	 */
	@Transient
	private Department org;
	@Transient
	private Department dep;
	@Transient
	private String dutyname;
	
	@Transient
	private Role role;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getWorkphone() {
		return workphone;
	}
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}
	public String getHiredate() {
		return hiredate;
	}
	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
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
	public Department getDep() {
		return dep;
	}
	public void setDep(Department dep) {
		this.dep = dep;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getDutyname() {
		return dutyname;
	}
	public void setDutyname(String dutyname) {
		this.dutyname = dutyname;
	}
	public Department getOrg() {
		return org;
	}
	public void setOrg(Department org) {
		this.org = org;
	}
	public String getEnableSendMsg() {
		return enableSendMsg;
	}
	public void setEnableSendMsg(String enableSendMsg) {
		this.enableSendMsg = enableSendMsg;
	}
}
